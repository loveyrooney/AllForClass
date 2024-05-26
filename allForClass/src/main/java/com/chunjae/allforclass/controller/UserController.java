package com.chunjae.allforclass.controller;

import com.chunjae.allforclass.dto.UserDTO;
import com.chunjae.allforclass.service.MypageService;
import com.chunjae.allforclass.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    private final UserService userService;

    private final MypageService myservice;

    @Autowired
    public UserController(UserService userService, MypageService myservice) {
        this.userService = userService;
        this.myservice = myservice;
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

    @PostMapping("/emailCheck")
    public @ResponseBody int emailCheck(@RequestBody HashMap<String,Object> hm) {
        String email = (String) hm.get("email");
        System.out.println("hello......"+email);
        int cnt = userService.emailCheck(email);

        return cnt;
    }

    @GetMapping("updateuser/{uid}")
    public String updateuser(@PathVariable int uid, Model model) {

        UserDTO dto = myservice.detailMe(uid);
        model.addAttribute("dto", dto);
        model.addAttribute("body", "user/updateuser.jsp");
        model.addAttribute("title", "모두의 국영수 - 회원정보수정");
        return "main";
    }

    @PostMapping("/updateuser_result")
    public String updateuser_result(UserDTO dto){
        int result = userService.updateUser(dto);

        return "redirect:/mypage/"+dto.getUid();
    }

    @GetMapping("/deleteuser/{uid}")
    public @ResponseBody Integer deleteuser(@PathVariable int uid){
        int result = userService.deleteUser(uid);

        return result;
    }
}