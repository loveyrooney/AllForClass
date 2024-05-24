package com.chunjae.allforclass.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

// 테스트중 ...
@Controller
public class VideoController {

    @GetMapping(value = "/getVideo/{filename}",
            produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<byte[]> getImage(@PathVariable String filename
            , HttpServletRequest request) {
        String path = "/uploadFile";
        String realpath = "D:\\moduUpload";
//        String realpath = request.getSession().getServletContext().getRealPath(path);
        String fname = URLEncoder.encode(filename, StandardCharsets.UTF_8)
                .replace("+", "%20");
        InputStream in = null;
        ResponseEntity<byte[]> entity = null;
        try {
            in = new FileInputStream(realpath + "/" + fname);
            System.out.println("in....." + in);
            HttpHeaders headers = new HttpHeaders();
            entity = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(in)
                    , headers, HttpStatus.OK);
        } catch (IOException e) {
            System.out.println("path: "+path);
            System.out.println("realpath: "+realpath);
            System.out.println(e + ".....file not found!!!!!!!!!");
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

}

