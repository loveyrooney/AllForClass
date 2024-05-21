package com.chunjae.allforclass.controller;

import com.chunjae.allforclass.dto.*;
import com.chunjae.allforclass.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
public class RoomController {

    private final static Logger logger = LoggerFactory.getLogger(RoomController.class);
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
        List<RefDTO> reflist = rservice.detailref(lid);

        model.addAttribute("ldto", ldto);
        model.addAttribute("vdto", vdto);
//        model.addAttribute("pdto", pdto);
        model.addAttribute("reflist", reflist);

        model.addAttribute("body", "room/room.jsp");
        model.addAttribute("title", "온라인 강의 시청하기 - online class");
        return "main";
    }

    @PostMapping("/insertref")
    public String insertref(HttpServletRequest request, RefDTO refdto) {
        logger.info(".....insert ref method......");
        logger.info("..... getlid {}", refdto.getLid());
        logger.info("..... getrfile....  {}", refdto.getFiles()[0].getOriginalFilename());

        String base = "/uploadImg";
//        String realpath = "D:\\YSH\\upload";
        String realpath = request.getSession().getServletContext().getRealPath(base);
        System.out.println("realpath....." + realpath);
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


//        VideoDTO insertvdto = roomservice.insertvideo(vdto);

//        roomservice.delReply(rid);

}
