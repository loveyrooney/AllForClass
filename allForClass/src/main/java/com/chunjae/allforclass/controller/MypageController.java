package com.chunjae.allforclass.controller;

import com.chunjae.allforclass.dto.CalDTO;
import com.chunjae.allforclass.dto.LecDTO;
import com.chunjae.allforclass.dto.UserDTO;
import com.chunjae.allforclass.service.MypageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class MypageController {

    private Logger logger = LoggerFactory.getLogger(MypageController.class);
    private final MypageService myservice;
    public MypageController(MypageService myservice){
        this.myservice=myservice;
    }


    /**마이페이지 이동*/
    @GetMapping("/mypage/{uid}")
    public String mypage(@PathVariable int uid, Model model){

        //회원 정보 가져오기
        UserDTO dto = myservice.detailMe(uid);

        model.addAttribute("dto", dto);
        model.addAttribute("body","mypage/mypage.jsp");
        model.addAttribute("title","모두의 국영수 - 마이페이지");
        return "main";
    }

    /**수강 강의 목록 json 파일 전달*/
    @GetMapping("/cal_list/{uid}")
    public @ResponseBody List<Map<String, Object>> cal_list(@PathVariable int uid){

        // 수강 강의 리스트
        List<CalDTO> list = myservice.findPurList(uid);

        // 지난강의는 배경색 다르게 하기위하여 현재 날짜 가져옴
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); // 포멧 지정
        Calendar cal = Calendar.getInstance();
        String datestr = format.format(cal.getTime());

        //hashMap으로 캘린더 이벤트 형식 만들기
        List<Map<String, Object>> eventList = new ArrayList<>();

        for(int i = 0; i < list.size(); i++) {
            Map<String, Object> event = new HashMap<>();
            event.put("title", list.get(i).getTimesession()+"\n"
                               +"["+list.get(i).getSubject()+"] "
                               +list.get(i).getTname()+"\n"
                               +list.get(i).getLname());
            event.put("start", list.get(i).getStartdate());
            event.put("textColor", "black");

            if (list.get(i).getStartdate().compareTo(datestr) < 0) {
                event.put("color", "#CCD1D1");
            } else if(list.get(i).getSubject().equals("국어")){
                event.put("color", "#14C4C5");
            } else if(list.get(i).getSubject().equals("영어")){
                event.put("color", "#FCD266");
            } else {
                event.put("color", "#F57896");
            }
            eventList.add(event);
        }

        return eventList;
    }

    /**강의 등록 폼*/
    @GetMapping("/insertlec")
    public String insertlec(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        int tid = (int)session.getAttribute("sessionId");

        //개강일 지정 가능 날짜 - 등록일 열흘 이후로 지정가능
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); // 포멧 지정

        Calendar cal = Calendar.getInstance(); // 날짜 계산을 위해 Calendar 추상클래스 선언 및 getInstance() 메서드 사용
        cal.setTime(date);

        cal.add(Calendar.DATE, 10); // 날짜에 10일 더한다
        String datestr = format.format(cal.getTime());

        model.addAttribute("datestr", datestr);
        model.addAttribute("tid", tid);

        model.addAttribute("body","mypage/insertlec.jsp");
        model.addAttribute("title","모두의 국영수 - 강의등록신청");

        return "main";
    }

    /**강의 등록*/
    @PostMapping("/insertlec_result")
    public String insertlec_result(@RequestParam MultipartFile imgfile
                                 , LecDTO dto
                                 , HttpServletRequest request
    ){

        //이미지 파일 업로드
        String base="/uploadImg";

        try {
            // 실제 경로 받음
            String realpath= request.getSession().getServletContext().getRealPath(base);

            if (!imgfile.isEmpty()) {

                //랜덤값 받아오기
                UUID uuid = UUID.randomUUID();

                //uuid로 파일명 앞에 랜덤값 붙여줌
                String imgname = uuid+"_"+imgfile.getOriginalFilename();

                //한글, 띄어쓰기 등등 오류 발생 가능성 있어 인코딩 필요
                imgname= URLEncoder.encode(imgname, StandardCharsets.UTF_8)
                        .replace("+", "%20");
                String filename = uuid + "_" +imgname;

                File file = new File(realpath, filename);
                imgfile.transferTo(file);  //파일 업로드

                // dto imgpath에 경로 입력
                dto.setImgpath(filename);

            }
        } catch (IOException e) {
            System.out.println(e);
        }

        //DB에 입력
        int result = myservice.insertLec(dto);

        return "redirect:/mypage";
    }


}
