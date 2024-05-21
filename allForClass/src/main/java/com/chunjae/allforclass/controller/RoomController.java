package com.chunjae.allforclass.controller;

import com.chunjae.allforclass.dto.*;
import com.chunjae.allforclass.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
public class RoomController {

    private RoomService rservice;

    @Autowired
    public RoomController(RoomService rservice) {
        this.rservice = rservice;
    }

    @GetMapping("/room/{lid}")
    public String room(@PathVariable int lid, Model model) {
        LecDTO ldto = rservice.detailLec(lid);
        VideoDTO vdto = rservice.detailvideo(lid);
//        PurDTO pdto = rservice.enterroom(pid);
        RefDTO refdto = rservice.detailref(lid);

        model.addAttribute("ldto", ldto);
        model.addAttribute("vdto", vdto);
//        model.addAttribute("pdto", pdto);
        model.addAttribute("refdto", refdto);

        model.addAttribute("body", "room/room.jsp");
        model.addAttribute("title", "온라인 강의 시청하기 - online class");
        return "main";
    }

    @PostMapping("/insertref")
    public String insertref(RefDTO refdto) {
        String base = "/uploadImg";
        String realpath = "D:\\YSH\\upload";
        System.out.println("realpath....." + realpath);
        rservice.insertref(realpath, refdto);

        return "redirect:/room/1";
    }

    @GetMapping("/replylist/{lid}")
    public @ResponseBody List<ReplyDTO> replyList(@PathVariable int lid, Model model) {
        List<ReplyDTO> rlist = rservice.replylist();
        model.addAttribute("rlist", rlist);
        return rlist;
    }

    @PostMapping("/replyinsert")
    public @ResponseBody HashMap<String, Object> replyInsert(@RequestBody HashMap<String, Object> hm) {
        int result = rservice.replyinsert(hm);
        return hm;
    }


//        VideoDTO insertvdto = roomservice.insertvideo(vdto);

//        roomservice.delReply(rid);

}
