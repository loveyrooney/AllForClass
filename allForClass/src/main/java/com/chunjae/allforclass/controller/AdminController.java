package com.chunjae.allforclass.controller;

import com.chunjae.allforclass.dto.LecDTO;
import com.chunjae.allforclass.dto.UserDTO;
import com.chunjae.allforclass.service.AdminService;
import com.chunjae.allforclass.service.PurchaseService;
import com.chunjae.allforclass.service.RoomService;
import com.chunjae.allforclass.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public String admin(HttpServletRequest request, Model model){

        HttpSession session = request.getSession(false);
        if (session != null) {
            int sessionId = (int) session.getAttribute("sessionId");
            String role = uservice.checkRole(sessionId);
        }

        // 승인 전
//        List<LecDTO> confirmList = aservice.cList(confirm);
        List<LecDTO> leclist = aservice.lecList();
        // role 1:user, 2:teacher
        List<UserDTO> uList = aservice.uList();

        model.addAttribute("leclist", leclist);
        model.addAttribute("uList", uList);
        model.addAttribute("body", "admin/admin.jsp");
        model.addAttribute("title","관리자 페이지");
        return "main";
    }


//+waitlec(boolean confirm):List<LecDTO>
//+adminmain():String
//+updatelec(HashMap<String,Object> hm):HashMap<String, Object>
//+confirm(int lid):String




    /**강의 수정/승인 폼*/
    @GetMapping("/updatelec/{lid}")
    public String updatelec(@PathVariable int lid, Model model){

        //강의 정보 가져오기
        LecDTO dto = pservice.detailLec(lid);

        model.addAttribute("dto", dto);
        model.addAttribute("body","admin/updatelec.jsp");
        model.addAttribute("title","모두의 국영수 - 강의등록승인");

        return "main";
    }
}
