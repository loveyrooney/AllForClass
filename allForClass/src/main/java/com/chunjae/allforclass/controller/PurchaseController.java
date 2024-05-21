package com.chunjae.allforclass.controller;

import com.chunjae.allforclass.dto.LecDTO;
import com.chunjae.allforclass.service.PurchaseService;
import com.chunjae.allforclass.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class PurchaseController {
    private final PurchaseService pservice;
    private final UserService uservice;
    public PurchaseController(PurchaseService pservice, UserService uservice){
        this.pservice=pservice;
        this.uservice=uservice;
    }
    @GetMapping("/detail_lec/{lid}")
    public String detail_lec(@PathVariable int lid, HttpServletRequest request, Model model){
        HttpSession session = request.getSession(false);
        if(session!=null){
            int sessionId = (int)session.getAttribute("sessionId");
            String role = uservice.checkRole(sessionId);
            boolean isReserved = pservice.isReserved(sessionId,lid);
            model.addAttribute("role",role);
            model.addAttribute("isReserved",isReserved);
        }
        LecDTO dto = pservice.detailLec(lid);
        model.addAttribute("dto",dto);
        model.addAttribute("body","purchase/detail_lec.jsp");
        model.addAttribute("title","모두의 국영수 - "+dto.getLname());
        return "main";
    }


}
