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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
//        PurDTO pdto = rservice.enterroom(pid);

//        model.addAttribute("pdto", pdto);
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

    //파일 업로드, DB저장
    @PostMapping("/insertref")
    public String insertref(HttpServletRequest request, RefDTO refdto) {

//        logger.info(".....insert ref method......");
        logger.info("..... getlid {}", refdto.getLid());
//        logger.info("..... getfile....  {}", refdto.getFiles()[0].getOriginalFilename());

        String path = "/uploadFile";
        String realpath = "D:\\moduUpload";
//        String realpath = request.getSession().getServletContext().getRealPath(path);
        rservice.insertref(realpath, refdto);

        return "redirect:/room/" + refdto.getLid();
    }

    @GetMapping("/replylist/{lid}")
    public @ResponseBody List<ReplyDTO> replyList(@PathVariable int lid, Model model) {
        List<ReplyDTO> rlist = rservice.replylist(lid);
        model.addAttribute("rlist", rlist);
        return rlist;
    }

    @PostMapping("/replyinsert")
    public @ResponseBody ReplyDTO replyInsert(@RequestBody ReplyDTO rdto) {
        int result = rservice.replyinsert(rdto);
        return rdto;
    }

    @PostMapping("/replydelete/{rid}")
    public @ResponseBody int replyDelete(@PathVariable int rid) {
        int result = rservice.replydelte(rid);
        return result;
    }

}
