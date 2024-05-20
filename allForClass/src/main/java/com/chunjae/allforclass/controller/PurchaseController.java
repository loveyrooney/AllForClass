package com.chunjae.allforclass.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PurchaseController {
    @GetMapping("/detail_lec/{lid}")
    public String detail_lec(){
        return "";
    }
}
