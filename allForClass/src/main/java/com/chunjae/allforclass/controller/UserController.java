package com.chunjae.allforclass.controller;

import com.chunjae.allforclass.dto.UserDTO;
import com.chunjae.allforclass.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("body", "user/login.jsp");
        model.addAttribute("title", "모두의 국영수 - login");
        return "main";
    }

    @GetMapping("/loginalert")
    public String loginalert(Model model) {
        model.addAttribute("body", "user/loginalert.jsp");
        model.addAttribute("title", "모두의 국영수 - login");
        return "main";
    }

    @PostMapping("/login_result")
    public String login_result(@RequestParam String email, @RequestParam String pwd, HttpSession session) {

        if (email != null && pwd != null) {
            boolean login = userService.checkUser(email, pwd);
            if (login == true) {  //로그인 성공
                int uid = userService.findUid(email);
                session.setAttribute("sessionId", uid);
                return "redirect:main";
            } else { //실패
                return "redirect:loginalert";
            } //if
        }else{
            return "redirect:loginalert";
        }
    }

    @GetMapping("/join")
    public String join(Model model){
        model.addAttribute("body", "user/join.jsp");
        model.addAttribute("title", "모두의 국영수 - join");
        return "main";
    }

    @PostMapping("/join_result")
    public String join_result(UserDTO dto){

        userService.join(dto);
        return "redirect:login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("sessionId") != null)
            session.invalidate();
        return "redirect:/main";
    }

}