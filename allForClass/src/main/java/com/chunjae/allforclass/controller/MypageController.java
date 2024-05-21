package com.chunjae.allforclass.controller;

import com.chunjae.allforclass.dto.LecDTO;
import com.chunjae.allforclass.service.MypageService;
import com.chunjae.allforclass.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Controller
public class MypageController {

    private final MypageService myservice;

    public MypageController(MypageService myservice){
        this.myservice=myservice;
    }


    /**마이페이지 이동*/
    @GetMapping("/mypage")
    public String mypage(Model model){
        model.addAttribute("body","mypage/mypage.jsp");
        model.addAttribute("title","모두의 국영수 - 마이페이지");
        return "main";
    }

    /**강의 등록 폼*/
    @GetMapping("/insertlec")
    public String insertlec(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        int tid = (int)session.getAttribute("sessionId");

        /*개강일 지정 가능 날짜 - 등록일 열흘 이후로 지정가능*/
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

        /*이미지 파일 업로드*/
        String base="/uploadImg";

        try {
            // 실제 경로 받음
            String realpath= request.getSession().getServletContext().getRealPath(base);

            if (!imgfile.isEmpty()) {

                /*랜덤값 받아오기 */
                UUID uuid = UUID.randomUUID();

                /*uuid로 파일명 앞에 랜덤값 붙여줌*/
                String imgname = uuid+"_"+imgfile.getOriginalFilename();

                /*한글, 띄어쓰기 등등 오류 발생 가능성 있어 인코딩 필요*/
                imgname= URLEncoder.encode(imgname, StandardCharsets.UTF_8)
                        .replace("+", "%20");
                String filename = uuid + "_" +imgname;

                File file = new File(realpath, filename);
                imgfile.transferTo(file);  /*파일 업로드*/

                // dto imgpath에 경로 입력
                dto.setImgpath(filename);

            }
        } catch (IOException e) {
            System.out.println(e);
        }

        /*DB에 입력*/
        int result = myservice.insertLec(dto);

        return "redirect:/mypage";
    }


}
