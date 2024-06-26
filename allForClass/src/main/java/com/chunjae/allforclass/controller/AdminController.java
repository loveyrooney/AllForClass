package com.chunjae.allforclass.controller;

import com.chunjae.allforclass.dto.LecDTO;
import com.chunjae.allforclass.dto.UserDTO;
import com.chunjae.allforclass.service.AdminService;
import com.chunjae.allforclass.service.PurchaseService;
import com.chunjae.allforclass.service.RoomService;
import com.chunjae.allforclass.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.List;

@Controller
public class AdminController {

    private AdminService aservice;
    private UserService uservice;
    private PurchaseService pservice;

    @Autowired
    public AdminController(AdminService aservice, UserService uservice, PurchaseService pservice) {
        this.aservice = aservice;
        this.uservice = uservice;
        this.pservice = pservice;
    }

    @GetMapping("/admin")
    public String admin(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            int sessionId = (int) session.getAttribute("sessionId");
            String role = uservice.checkRole(sessionId);
        }

        // 승인 전
        List<LecDTO> cList = aservice.cList();
        List<LecDTO> leclist = aservice.lecList();
        // role 1:user, 2:teacher
        List<UserDTO> uList = aservice.uList();

        model.addAttribute("cList", cList);
        model.addAttribute("leclist", leclist);
        model.addAttribute("uList", uList);
        model.addAttribute("body", "admin/admin.jsp");
        model.addAttribute("title", "관리자 페이지");
        return "main";
    }

    /**
     * 강의 수정/승인 폼
     */
    @GetMapping("/updatelec/{lid}")
    public String updatelec(@PathVariable int lid, Model model) {

        //강의 정보 가져오기
        LecDTO dto = pservice.detailLec(lid);

        model.addAttribute("dto", dto);
        model.addAttribute("body", "admin/updatelec.jsp");
        model.addAttribute("title", "모두의 국영수 - 강의등록확인");

        return "main";
    }

    /**
     * 강의 정보 수정
     */
    @PostMapping("/updatelec_result")
    public String updatelec_result(LecDTO dto, Model model) {

        int result = aservice.updateLecResult(dto);
        model.addAttribute("result", result);
        model.addAttribute("lid", dto.getLid());

        return "admin/updatealert";
    }

    /**
     * 강의 승인
     */
    @GetMapping("/confirm/{lid}")
    public @ResponseBody Integer confirm(@PathVariable int lid, @RequestParam int tid, Model model) {

        int result = aservice.confirm(lid);

        HashMap<String, Object> hm = new HashMap<>();
        String payid = "teacher";
        hm.put("uid", tid);
        hm.put("lid", lid);
        hm.put("paymentId", payid);
        pservice.insertPur(hm);
        //pservice.sendConfirmEmail(lid);

        return result;
    }


    /**
     * 강의 삭제
     */
    @GetMapping("/deletelec/{lid}")
    public @ResponseBody Integer deletelec(@PathVariable int lid, HttpServletRequest request) {

        LecDTO dto = pservice.detailLec(lid);

        String base = "/uploadImg";
        String imgpath = dto.getImgpath();
        String realpath = request.getSession().getServletContext().getRealPath(base);

        File file = new File(realpath, imgpath);

        int result = 0;

        if (imgpath.equals("default.png")) {
            result = aservice.deleteLec(lid);
        } else {

            if (file.exists()) {
                if (file.delete()) {
                    result = aservice.deleteLec(lid);
                    if (result != 1) {
                        // DB 삭제 실패 처리
                        throw new RuntimeException("Failed to delete image from DB");
                    }
                } else {
                    // 파일 삭제 실패 처리
                    throw new RuntimeException("Failed to delete image file");
                }
            } else {
                // 파일이 존재하지 않을 경우
                throw new RuntimeException("image file does not exist");
            }
        }
        return result;
    }
}
