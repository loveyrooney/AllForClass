package com.chunjae.allforclass.exception;

import org.apache.ibatis.javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class CustomExceptionHandler {
    private Logger logger = LoggerFactory.getLogger("CustomExceptionHandler.class");
    @ExceptionHandler(BusinessException.class)
    public String handleBusinessException(BusinessException e, Model model){
        logger.info("Payment Exception : {}",e.getMessage());
        model.addAttribute("status",e.getErrorCode().getHttpStatus());
        model.addAttribute("msg",e.getErrorCode().getMessage());
        model.addAttribute("body","error/exception.jsp");
        model.addAttribute("title","모두의 국영수 - ERROR");
        return "main";
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
    @ExceptionHandler({ NotFoundException.class, NoHandlerFoundException.class })
    public String handleClientException(Exception e, Model model){
        logger.info("ClientException : {}",e.getMessage());
        model.addAttribute("status","404");
        model.addAttribute("msg","페이지를 찾을 수 없습니다.");
        model.addAttribute("body","error/exception.jsp");
        model.addAttribute("title","모두의 국영수 - ERROR");
        return "main";
    }
}
