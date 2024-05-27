package com.chunjae.allforclass.service;

import com.chunjae.allforclass.dao.LectureMapper;
import com.chunjae.allforclass.dao.PurchaseMapper;
import com.chunjae.allforclass.dto.LecDTO;
import com.chunjae.allforclass.dto.MailDTO;
import com.chunjae.allforclass.exception.BusinessException;
import com.chunjae.allforclass.exception.ErrorCode;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService{
    private final PurchaseMapper pmapper;
    private final LectureMapper lmapper;
    private final JavaMailSender javaMailSender;
    public PurchaseServiceImpl(PurchaseMapper pmapper, LectureMapper lmapper, JavaMailSender javaMailSender){

        this.pmapper=pmapper;
        this.lmapper=lmapper;
        this.javaMailSender=javaMailSender;
    }


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
    public boolean deletePur(int pid) {
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

    //@Scheduled(cron = "0 0 10 * * ?")
    @Override
    public void sendHtmlEmail() throws BusinessException {
        List<MailDTO> sendlist = sendMailList();
        SimpleMailMessage message = new SimpleMailMessage();
        //MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try{
            //MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            for(MailDTO dto: sendlist){
                String timesession = dto.getTimesession().substring(7,12);
                message.setTo(dto.getUemail());
                message.setText(dto.getUname()+"님 안녕하세요😍\n");
                message.setText(dto.getLname()+"("+dto.getTname()+" 선생님) 강의가 내일 "+timesession+" 시에 개강합니다.\n");
                message.setText("수업 영상과 강의자료는 개강일 당일 자정까지만 공개되오니 참고하시기 바랍니다.\n");
                message.setText("감사합니다.");
            }
            message.setSubject("모두의 국영수 - 개강 안내 메일입니다.");
            javaMailSender.send(message);
            // HTML 파일을 읽어와서 본문으로 설정
            //String htmlBody = readHtmlFile();
            //helper.setText(htmlBody, true); // true를 사용하여 HTML 형식으로 메시지를 설정
        } catch (Exception e){
            System.out.println(e.getMessage());
            throw new BusinessException(ErrorCode.FAIL_TO_MAILSEND);
        }
    }

//    private String readHtmlFile() throws IOException {
//        ClassPathResource resource = new ClassPathResource("classinfo.html");
//        return IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
//    }
}
