package com.chunjae.allforclass.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {
    @GetMapping("/main")
    public String hi(HttpServletRequest request, Model model){
        model.addAttribute("body","hi.jsp");
        model.addAttribute("title","모두의 국영수 - main");
        return "main";
    }
}
