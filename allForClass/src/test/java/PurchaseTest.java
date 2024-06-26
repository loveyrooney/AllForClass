import com.chunjae.allforclass.dao.PurchaseMapper;
import com.chunjae.allforclass.dto.MailDTO;
import com.chunjae.allforclass.exception.BusinessException;
import com.chunjae.allforclass.service.PurchaseService;
import com.chunjae.allforclass.service.PurchaseServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
public class PurchaseTest {
    @Autowired
    private PurchaseService pservice;

    @Autowired
    private PurchaseMapper pmapper;

    @Autowired
    private JavaMailSender javaMailSender;
    @Test
    public void detailLec(){
        Assertions.assertNull(pservice.detailLec(5));
    }

    @Test
    public void isReserved(){
//        HashMap<String,Object> hm = new HashMap<>();
//        hm.put("sessionId",1);
//        hm.put("lid",1);
//        Assertions.assertEquals(null,pmapper.isReserved(hm));
        Assertions.assertEquals(2,pservice.isReserved(1,1));
    }

    @Test
    public void checkPrice(){
        Assertions.assertEquals(5000,pservice.checkPrice(1));
    }

    @Transactional
    @Test
    public void insertPur(){
        HashMap<String,Object> hm = new HashMap<>();
        hm.put("uid",1);
        hm.put("lid",1);
        hm.put("paymentId","eigh234irg");
        Assertions.assertEquals(1,pmapper.insertPur(hm));
    }

    @Test
    public void findPayid(){
        Assertions.assertEquals("1",pservice.findPayid(2));
    }

    @Transactional
    @Test
    public void deletePur() throws SQLException {
        Assertions.assertEquals(true,pservice.deletePur(2));
    }

    @Test
    public void countPur(){
        Assertions.assertEquals(1,pservice.countPur(2));
    }
    @Test
    public void checkSchedule(){
        HashMap<String,Object> hm = new HashMap<>();
        hm.put("uid",3);
        hm.put("startdate","2024-05-30");
        hm.put("timesession","Time1*09:00-12:00");
        Assertions.assertEquals(0,pservice.checkSchedule(hm));
    }

    @Test
    public void date(){
        Assertions.assertEquals(true,LocalDate.now().isAfter(LocalDate.parse("2024-05-23")));
    }

    @Test
    public void sendHtmlEmail(){
        //Assertions.assertNotNull(javaMailSender);
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo("받을 사람 실제 메일");
//        message.setSubject("Test Subject 2");
//        message.setText("Test Body");
//        message.setFrom("보내는 smtp 계정 메일");
//        try {
//            javaMailSender.send(message);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        try {
            pservice.sendHtmlEmail();
        }catch (BusinessException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void sendmaillist(){
        Assertions.assertEquals(2,pservice.sendMailList().size());
        List<MailDTO> list = pservice.sendMailList();
        for(MailDTO dto: list){
            System.out.println(dto.getUemail());
            System.out.println(dto.getUname());
            System.out.println(dto.getTimesession());
        }
    }

    @Test
    public void sendConfirmMail(){
        //Assertions.assertEquals("박지성",pmapper.sendConfirmInfo(3).getTname());
        pservice.sendConfirmEmail(3);
    }


//    @Test
//    public void schedule() throws InterruptedException{
//        long delay = 2000L;
//        Thread.sleep(delay + 5000);
//        pservice.schedule();
//    }
}
