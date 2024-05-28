package com.chunjae.allforclass.service;

import com.chunjae.allforclass.dao.LectureMapper;
import com.chunjae.allforclass.dao.PurchaseMapper;
import com.chunjae.allforclass.dto.LecDTO;
import com.chunjae.allforclass.dto.MailDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Service
@PropertySource("classpath:mail.properties")
public class PurchaseServiceImpl implements PurchaseService{
    private final PurchaseMapper pmapper;
    private final LectureMapper lmapper;
    private final JavaMailSender javaMailSender;
    @Value("${AdminMail.id}")
    private String adminMail;
    @Value("${domain}")
    private String domain;
    public PurchaseServiceImpl(PurchaseMapper pmapper, LectureMapper lmapper, JavaMailSender javaMailSender){
        this.pmapper=pmapper;
        this.lmapper=lmapper;
        this.javaMailSender=javaMailSender;
    }
    private final Logger logger = LoggerFactory.getLogger("PurchaseServiceImpl.class");

    @Override
    public LecDTO detailLec(int lid) {
        return lmapper.detailLec(lid);
    }

    @Override
    public int isReserved(int sessionId, int lid) {
        int isReserved = 0;
        HashMap<String,Object> hm = new HashMap<>();
        hm.put("sessionId",sessionId);
        hm.put("lid",lid);
        Integer result = pmapper.isReserved(hm);
        if(result !=null)
            isReserved = result;
        return isReserved;
    }

    @Transactional
    @Override
    public HashMap<String, Object> ApplyClass(HashMap<String,Object> hm, String responseBody) throws SQLException {
        HashMap<String,Object> result = new HashMap<>();
        int priceFromDB = checkPrice((int)hm.get("lid"));
        if(responseBody.contains("\"paid\":"+priceFromDB)
                && responseBody.contains("\"id\":\""+hm.get("paymentId")+"\"")){
            boolean correct = insertPur(hm);
            result.put("correct",correct);
            result.put("msg","[정상결제] 수강 신청에 성공하였습니다.");
        } else {
            result.put("correct", false);
            result.put("msg","[결제정보불일치] 수강 신청에 실패하였습니다. 관리자에게 문의 바랍니다.");
        }
        return result;
    }

    @Override
    public int checkPrice(int lid) {
        int price = 0;
        Integer result = lmapper.checkPrice(lid);
        if(result!=null)
            price = result;
        return price;
    }

    @Override
    public boolean insertPur(HashMap<String, Object> hm){
        int result = pmapper.insertPur(hm);
        //if(result==0){}
        return true;
    }

    @Override
    public String findPayid(int pid) {
        return pmapper.findPayid(pid);
    }

    @Override
    public boolean deletePur(int pid) throws SQLException {
        boolean correct = false;
        int result = pmapper.deletePur(pid);
        if(result!=0)
            correct = true;
        return correct;
    }

    @Override
    public int countPur(int lid) {
        return pmapper.countPur(lid);
    }

    @Override
    public int checkSchedule(HashMap<String, Object> hm) {
        return pmapper.checkSchedule(hm);
    }

    @Override
    public List<MailDTO> sendMailList() {
        return pmapper.sendMailList();
    }

//    @Scheduled(fixedRate = 1000)
//    public void schedule(){
//        System.out.println(System.currentTimeMillis());
//    }

    @Scheduled(cron = "0 0 10 * * ?")
    @Override
    public void sendHtmlEmail() {
        List<MailDTO> sendlist = sendMailList();
        if(sendlist.size()>0){
            try{
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                for(MailDTO dto: sendlist){
                    String timesession = dto.getTimesession().substring(7,11);
                    helper.setTo(dto.getUemail());
                    helper.setSubject("모두의 국영수 - 개강 안내 메일입니다.");
                    helper.setText(mailText(dto,timesession),true);
                    helper.setFrom(adminMail);
                    javaMailSender.send(mimeMessage);
                }
            } catch (Exception e){
                logger.info("fail sendMail : {}",e.getMessage());
            }
        }
    }

    @Override
    public void sendConfirmEmail(int lid) {
        MailDTO sendinfo = pmapper.sendConfirmInfo(lid);
        System.out.println(adminMail);
        if(sendinfo != null){
            try{
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                    String timesession = sendinfo.getTimesession().substring(7,11);
                    helper.setTo(sendinfo.getUemail());
                    helper.setSubject("모두의 국영수 - 강의 승인 안내 메일입니다.");
                    helper.setText(conFirmMailText(sendinfo,timesession),true);
                    helper.setFrom(adminMail);
                    javaMailSender.send(mimeMessage);
            } catch (Exception e){
                logger.info("fail sendMail : {}",e.getMessage());
            }
        }
    }

    private String mailText(MailDTO dto, String timesession) {
        StringBuilder text = new StringBuilder();
        text.append("<html><head>");
        text.append("<meta charset=\"UTF-8\">");
        text.append("</head><body>");
        text.append("<h2>"+dto.getUname()+" 님 안녕하세요😍</h2>");
        text.append("<h2>"+dto.getLname()+"("+dto.getTname()+" 선생님) 강의가\n");
        text.append(dto.getStartdate()+" "+timesession+" 시에 개강합니다.</h2>");
        text.append("<p>수업 영상은 개강일 당일 자정까지만 공개되오니 참고하시기 바랍니다.</p>");
        text.append("👉<a href=\""+domain+"/login\">수업 들으러 가기</a>");
        text.append("<p>감사합니다.</p>");
        text.append("<p>Copyright @2024 AllForClass Team. All rights reserved.</p>");
        return text.toString();
    }

    private String conFirmMailText(MailDTO dto, String timesession) {
        StringBuilder text = new StringBuilder();
        text.append("<html><head>");
        text.append("<meta charset=\"UTF-8\">");
        text.append("</head><body>");
        text.append("<h2>"+dto.getTname()+" 선생님 안녕하세요😍</h2>");
        text.append("<h2>신청하신 "+dto.getLname()+" 강의가 최종 승인되었습니다.</h2>");
        text.append("<p>"+dto.getStartdate()+" "+timesession+" 시에 개강 예정이며, 수업 영상과 자료는 지금부터 등록 및 수정이 가능합니다.\n");
        text.append("강의 영상의 경우 수강생에게는 개강일 당일 자정까지만 공개되오니 참고하시기 바랍니다.</p>");
        text.append("👉<a href=\""+domain+"/login\">강의실 가기</a>");
        text.append("<p>감사합니다.</p>");
        text.append("<p>Copyright @2024 AllForClass Team. All rights reserved.</p>");
        return text.toString();
    }

}
