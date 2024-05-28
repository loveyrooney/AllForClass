package com.chunjae.allforclass.exception;

import org.apache.ibatis.javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;

@ControllerAdvice
public class CustomExceptionHandler {
    private Logger logger = LoggerFactory.getLogger("CustomExceptionHandler.class");
    @ExceptionHandler(BusinessException.class)
    public @ResponseBody HashMap<String,Object> handleBusinessException(BusinessException e){
        logger.info("Payment Exception : {}",e.getMessage());
        HashMap<String,Object> hm = new HashMap<>();
        hm.put("status",e.getErrorCode().getHttpStatus());
        hm.put("msg",e.getErrorCode().getMessage());
        return hm;
    }
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model){
        logger.info("Exception : {}",e.getMessage());
        model.addAttribute("status","500");
        model.addAttribute("msg","서버 에러가 발생하였습니다.");
        model.addAttribute("body","error/exception.jsp");
        model.addAttribute("title","모두의 국영수 - ERROR");
        return "main";
    }
}
