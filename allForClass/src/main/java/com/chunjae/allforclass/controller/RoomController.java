package com.chunjae.allforclass.controller;

import com.chunjae.allforclass.dto.*;
import com.chunjae.allforclass.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RoomController {

    private RoomService roomservice;

    @Autowired
    public RoomController(RoomService roomservice) {
        this.roomservice = roomservice;
    }

    @GetMapping("/room")
    public String room(Model model) {



        PurDTO pdto = new PurDTO();
        int pid = pdto.getPid();
        String role = roomservice.enterroom(pid);

        LecDTO ldto = new LecDTO();
        int lid = ldto.getLid();
        VideoDTO detailvdto = roomservice.detailvideo(lid);

        RefDTO detailrdto = roomservice.detailref(lid);
        List<ReplyDTO> rlist = roomservice.replylist();

        VideoDTO vdto = new VideoDTO();
        VideoDTO insertvdto = roomservice.insertvideo(vdto);

        RefDTO refdto = new RefDTO();
        RefDTO insertrdto = roomservice.insertref(refdto);

        ReplyDTO rdto = new ReplyDTO();
        int rid = rdto.getRid();
        roomservice.delReply(rid);


        model.addAttribute("body", "room.jsp");
        model.addAttribute("title", "온라인 강의 시청하기 - online class");
        return "main";
    }


}
