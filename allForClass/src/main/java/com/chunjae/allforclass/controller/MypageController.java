package com.chunjae.allforclass.controller;

import com.chunjae.allforclass.dto.CalDTO;
import com.chunjae.allforclass.dto.LecDTO;
import com.chunjae.allforclass.dto.UserDTO;
import com.chunjae.allforclass.service.MypageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

        // 관리자 계정이면 관리자페이지로 이동
        if(dto.getRole() == 3){
            return "redirect:/admin";
        } else {
            model.addAttribute("dto", dto);
            model.addAttribute("body","mypage/mypage.jsp");
            model.addAttribute("title","모두의 국영수 - 마이페이지");
            return "main";
        }
    }

    /**수강생 신청 강의 목록 json*/
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
            event.put("url", "/detail_lec/"+list.get(i).getLid());
            event.put("title", list.get(i).getTimesession()+"\n"
                               +"["+list.get(i).getSubject()+"] "
                             /*+list.get(i).getTname()+"\n"*/
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); // 포맷 지정

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

    /**강의 시간 중복 체크*/
    @PostMapping("/checklectime/{uid}")
    public @ResponseBody Integer checklectime(@PathVariable int uid, @RequestBody HashMap<String, String> lectime){
        // form에서 시간 값 받아오기
        String startdate = lectime.get("startdate");
        String timesession = lectime.get("timesession");

        int result = myservice.checkLecTime(uid, startdate, timesession);

        return result;
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

            } else {
                // 기본 이미지 파일로 게시
                dto.setImgpath("default.png");
            }
        } catch (IOException e) {
            System.out.println(e);
        }

        //DB에 입력
        int result = myservice.insertLec(dto);

        HttpSession session = request.getSession();
        int tid = (int)session.getAttribute("sessionId");

        return "redirect:/mypage/"+tid;
    }

    /**지난 강의 목록 json*/
    @GetMapping("/pastmylec/{uid}")
    public @ResponseBody List<LecDTO> pastmylec(@PathVariable int uid){

        // 현재 날짜와 시간 확인  -> 다른 곳에서도 쓰이니깐 나중에 따로 메서드 만들 것
        SimpleDateFormat format_day = new SimpleDateFormat("yyyy-MM-dd"); // 날짜포맷
        SimpleDateFormat format_time = new SimpleDateFormat("HH:mm"); // 시간포맷

        Calendar cal = Calendar.getInstance();
        String curr_day = format_day.format(cal.getTime());  // 2024-05-23
        String curr_time = format_time.format(cal.getTime());  // 10:49

        String curr_session = "";

        if("00:00".compareTo(curr_time) < 0 && "12:00".compareTo(curr_time) >= 0){
            curr_session="1";
        } else if("12:00".compareTo(curr_time) < 0 && "15:00".compareTo(curr_time) >= 0){
            curr_session="2";
        } else if("15:00".compareTo(curr_time) < 0 && "18:00".compareTo(curr_time) >= 0){
            curr_session="3";
        } else if("18:00".compareTo(curr_time) < 0 && "21:00".compareTo(curr_time) >= 0){
            curr_session="4";
        } else {
            curr_session="5";

            cal.add(Calendar.DATE, 1); // time4 이후면 날짜에 하루 더한다
            curr_day = format_day.format(cal.getTime());
        }

        // 리스트 가져올때 지난강의/예정된 강의/승인예정인지 구분위해 변수 추가 - 클래스이름으로
        String lectype = "pastmylec";

        //리스트 가져오기
        HashMap<String, Object> hm = new HashMap<>();
        List<LecDTO> list = myservice.findMyLecList(lectype, curr_day, curr_session, uid);

        return list;
    }

    /**예정된 강의 목록 json*/
    @GetMapping("/confirmedmylec/{uid}")
    public @ResponseBody List<LecDTO> confirmedmylec(@PathVariable int uid){

        // 현재 날짜와 시간 확인
        SimpleDateFormat format_day = new SimpleDateFormat("yyyy-MM-dd"); // 날짜포맷
        SimpleDateFormat format_time = new SimpleDateFormat("HH:mm"); // 시간포맷

        Calendar cal = Calendar.getInstance();
        String curr_day = format_day.format(cal.getTime());  // 2024-05-23
        String curr_time = format_time.format(cal.getTime());  // 10:49

        String curr_session = "";

        if("00:00".compareTo(curr_time) < 0 && "12:00".compareTo(curr_time) >= 0){
            curr_session="1";
        } else if("12:00".compareTo(curr_time) < 0 && "15:00".compareTo(curr_time) >= 0){
            curr_session="2";
        } else if("15:00".compareTo(curr_time) < 0 && "18:00".compareTo(curr_time) >= 0){
            curr_session="3";
        } else if("18:00".compareTo(curr_time) < 0 && "21:00".compareTo(curr_time) >= 0){
            curr_session="4";
        } else {
            curr_session="5";
        }

        // 리스트 가져올때 지난강의/예정된 강의/승인예정인지 구분위해 변수 추가 - 클래스이름으로
        String lectype = "confirmedmylec";

        //리스트 가져오기
        List<LecDTO> list = myservice.findMyLecList(lectype, curr_day, curr_session, uid);
        return list;
    }

    /**승인 대기 강의 목록 json*/
    @GetMapping("/waitmylec/{uid}")
    public @ResponseBody List<LecDTO> waitmylec(@PathVariable int uid){

        String curr_day = "";
        String curr_session = "";

        // 리스트 가져올때 지난강의/예정된 강의/승인예정인지 구분위해 변수 추가 - 클래스이름으로
        String lectype = "waitmylec";

        //리스트 가져오기
        List<LecDTO> list = myservice.findMyLecList(lectype, curr_day, curr_session, uid);
        return list;
    }

}
