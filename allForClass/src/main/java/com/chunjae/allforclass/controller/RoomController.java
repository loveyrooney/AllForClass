package com.chunjae.allforclass.controller;

import com.chunjae.allforclass.dto.*;
import com.chunjae.allforclass.service.RoomService;
import com.chunjae.allforclass.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class RoomController {

    private final static Logger logger = LoggerFactory.getLogger(RoomController.class);
    private RoomService rservice;
    private UserService uservice;

    @Autowired
    public RoomController(RoomService rservice, UserService uservice) {
        this.rservice = rservice;
        this.uservice = uservice;
    }

    @GetMapping("/room/{lid}")
    public String room(@PathVariable int lid, HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            int sessionId = (int) session.getAttribute("sessionId");
            String role = uservice.checkRole(sessionId);

            model.addAttribute("role", role);
            model.addAttribute("sessionId", sessionId);
        }

        LecDTO ldto = rservice.detailLec(lid);
        VideoDTO vdto = rservice.detailvideo(lid);
        List<RefDTO> reflist = rservice.detailref(lid);

        model.addAttribute("ldto", ldto);
        model.addAttribute("vdto", vdto);

        // 첨부 파일 이름 디코드
        List<String> decodeRef = new ArrayList<>();
        for (int i = 0; i < reflist.size(); i++) {
            String refname = rservice.detailref(lid).get(i).getRefpath();
            refname = URLDecoder.decode(refname, StandardCharsets.UTF_8).substring(37);
            System.out.println("........" + refname);
            decodeRef.add(refname);
        }
        model.addAttribute("reflist", reflist);
        model.addAttribute("decodeRef", decodeRef);

        model.addAttribute("body", "room/room.jsp");
        model.addAttribute("title", "온라인 강의 시청하기 - " + ldto.getLname());
        return "main";
    }

    // 비디오 업로드
    @PostMapping("/insertvid")
    public @ResponseBody void insertvid(@RequestParam("vidfile") MultipartFile vidfile, VideoDTO vdto, HttpServletRequest request) {
        String path = "/uploadVideo";

        try {
//            String realpath = "D:\\moduUpload";
            String realpath = request.getSession().getServletContext().getRealPath(path);

            if (!vidfile.isEmpty()) {

                String vidname = vidfile.getOriginalFilename();
                vidname = URLEncoder.encode(vidname, StandardCharsets.UTF_8).replace("+", "%20");
                String filename = vidname;

                File file = new File(realpath, filename);
                vidfile.transferTo(file);

                vdto.setVideopath(filename);
                logger.info("videopath .... {}", vdto.getVideopath());
            }
        } catch (IOException e) {
            System.out.println(e);
        }

        int result = rservice.insertVid(vdto);
    }

    // 비디오 불러오기
    @GetMapping(value = "/getVideo/{videopath}", produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<byte[]> getImage(@PathVariable String videopath, HttpServletRequest request) {
        String path = "/uploadVideo";
//        String realpath = "D:\\moduUpload";
        String realpath = request.getSession().getServletContext().getRealPath(path);
        String fname = URLEncoder.encode(videopath, StandardCharsets.UTF_8).replace("+", "%20");
        InputStream in = null;
        ResponseEntity<byte[]> entity = null;
        try {
            in = new FileInputStream(realpath + "/" + fname);
            System.out.println("in....." + in);
            HttpHeaders headers = new HttpHeaders();
            entity = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(in)
                    , headers, HttpStatus.OK);
        } catch (IOException e) {
            System.out.println("path: " + path);
            System.out.println("realpath: " + realpath);
            System.out.println(e + ".....file not found!!!!!!!!!");
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    // 비디오 삭제
    @PostMapping("/deletevideo/{vid}")
    public @ResponseBody void deletevideo(@PathVariable int vid, @RequestBody Map<String, String> hm, HttpServletRequest request) {
        String path = "/uploadVideo";
        String videopath = hm.get("videopath");
//        String realpath = "D:\\moduUpload";
        String realpath = request.getSession().getServletContext().getRealPath(path);

        File file = new File(realpath, videopath);

        if (file.exists()) {
            if (file.delete()) {
                int result = rservice.deleteVid(vid);
                if (result <= 0) {
                    // DB 삭제 실패 처리
                    throw new RuntimeException("Failed to delete video from DB");
                }
            } else {
                // 파일 삭제 실패 처리
                throw new RuntimeException("Failed to delete video file");
            }
        } else {
            // 파일이 존재하지 않을 경우
            throw new RuntimeException("Video file does not exist");
        }
    }

    // 자료 업로드
    @PostMapping("/insertref")
    public @ResponseBody void insertref(HttpServletRequest request, RefDTO refdto) {
        String path = "/uploadFile";
//        String realpath = "D:\\moduUpload";
        String realpath = request.getSession().getServletContext().getRealPath(path);
        rservice.insertref(realpath, refdto);
    }

    // 자료 다운로드
    @GetMapping(value = "/download/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> downloadref(@PathVariable String filename, HttpServletRequest request, HttpServletResponse response) {

        String path = "/uploadFile";
//        String realpath = "D:\\moduUpload";
        String realpath = request.getSession().getServletContext().getRealPath(path);
        logger.info("realpath.....{}" + realpath);

        String fname = URLEncoder.encode(filename, StandardCharsets.UTF_8).replace("+", "%20");

        File file = new File(realpath + "/" + fname);
        if (!file.exists()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        HttpHeaders headers = new HttpHeaders();

        Resource resource;
        try {
            resource = new InputStreamResource(new FileInputStream(file));
            response.setHeader("Content-Type", "application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=" + fname);
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            headers.add("contentType", "utf-8");
            headers.setContentLength(file.length());
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        } catch (IOException e) {
            System.out.println("error....." + e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }


    // 댓글 리스트
    @GetMapping("/replylist/{lid}")
    public @ResponseBody List<ReplyDTO> replyList(@PathVariable int lid, Model model) {
        List<ReplyDTO> rlist = rservice.replylist(lid);
        model.addAttribute("rlist", rlist);
        return rlist;
    }

    // 댓글 추가
    @PostMapping("/replyinsert")
    public @ResponseBody ReplyDTO replyInsert(@RequestBody ReplyDTO rdto) {
        int result = rservice.replyinsert(rdto);
        return rdto;
    }

    // 댓글 제거
    @PostMapping("/replydelete/{rid}")
    public @ResponseBody int replyDelete(@PathVariable int rid) {
        int result = rservice.replydelte(rid);
        return result;
    }

}
