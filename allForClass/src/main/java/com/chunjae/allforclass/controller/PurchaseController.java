package com.chunjae.allforclass.controller;

import com.chunjae.allforclass.dto.LecDTO;
import com.chunjae.allforclass.service.PurchaseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PurchaseController {
    private final PurchaseService pservice;
    public PurchaseController(PurchaseService pservice){
        this.pservice=pservice;
    }
    @GetMapping("/detail_lec/{lid}")
    public String detail_lec(@PathVariable int lid, Model model){
        LecDTO dto = pservice.detailLec(lid);
        model.addAttribute("dto",dto);
        model.addAttribute("body","purchase/detail_lec.jsp");
        model.addAttribute("title","모두의 국영수 - "+dto.getLname());
        return "main";
    }
}
