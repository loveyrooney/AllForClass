package com.chunjae.allforclass.controller;

import com.chunjae.allforclass.dto.LecDTO;
import com.chunjae.allforclass.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


@Controller
public class MainController {

    private final MainService mainService;
    @Autowired
    public MainController(MainService mainService){
        this.mainService=mainService;
    }

    @GetMapping({"/main", "/searchlec"})
    public String main(boolean confirm
                     , @RequestParam(required = false, defaultValue = "")String searchtxt
                     , Model model){

        List<LecDTO> list = mainService.findLecList(confirm, searchtxt);

        model.addAttribute("list", list);
        model.addAttribute("searchtxt", searchtxt);


        model.addAttribute("body","mainlist.jsp");
        model.addAttribute("title","모두의 국영수 - main");
        return "main";
    }

    @GetMapping( value="/getImage/{lname}", produces = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getImage(@PathVariable String lname
            , HttpServletRequest request) {
        String path="/uploadImg";
        String realpath= request.getSession().getServletContext().getRealPath(path);
        String fname = URLEncoder.encode(lname, StandardCharsets.UTF_8)
                .replace("+", "%20");
        InputStream in = null;
        ResponseEntity<byte[]> entity=null;
        try {
            in = new FileInputStream(realpath + "/" + fname);
            System.out.println("in....."+in);
            HttpHeaders headers=new HttpHeaders();

            entity=new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(in)
                    ,headers,  HttpStatus.OK);
        }catch(IOException e)
        {
            System.out.println(e+".....file not found 안녕!!!!!!!!!");
            entity=new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

}
