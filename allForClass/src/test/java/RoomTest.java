import com.chunjae.allforclass.dto.LecDTO;
import com.chunjae.allforclass.dto.RefDTO;
import com.chunjae.allforclass.dto.ReplyDTO;
import com.chunjae.allforclass.service.RoomService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
public class RoomTest {

    @Autowired
    private RoomService rservice;

    @Test
    public void detaillec() {
//        assertEquals("고등수학", rservice.detailLec(1).getLname());
        String time = rservice.detailLec(2).getTimesession();
        System.out.println(time);
    }

//    @Test
//    public void detailref(){
//        assertEquals("refpath", rservice.detailref(1).getRefpath());
//    }

    @Test
    public void replyTest() {
        assertEquals("댓글1", rservice.replylist(1).get(0).getContent());
    }

    @Transactional
    @Test
    public void replyTest1() {

        System.out.println("ReplyList ...... " + rservice.replylist(1).get(0).getUrole());
    }

    @Transactional
    @Test
    public void replyTest2() {
        ReplyDTO dto = new ReplyDTO();
        dto.setUid(3);
        dto.setLid(1);
        dto.setContent("댓글2-1");
        dto.setContent("");
        rservice.replyinsert(dto);
    }

    @Test
    public void refTest1() {
        List<RefDTO> reflist = rservice.detailref(1);

        for (int i = 0; i < reflist.size(); i++) {
            String refname = rservice.detailref(1).get(i).getRefpath();
            refname = URLDecoder.decode(refname, StandardCharsets.UTF_8).substring(37);
            System.out.println("........" + refname);
        }

    }

    @Test
    public void taskScheduler() throws InterruptedException, ParseException {

        String startDate = rservice.detailLec(2).getStartdate();
        String startTime = rservice.detailLec(2).getTimesession().substring(6,8);
        String endTime = rservice.detailLec(2).getTimesession().substring(12,14);
        String openTime = startDate +" "+ startTime;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
        Date activationDate = sdf.parse(openTime);

        long duration = Long.parseLong(endTime) - Long.parseLong(startTime);
        System.out.println(openTime);
        System.out.println(duration);


//        // activationDate와 duration을 사용하여 hello 메소드를 호출합니다.
//        rservice.hello(activationDate, duration);
//
//        // 활성화 날짜까지 기다리는 것을 시뮬레이션합니다.
//        long waitTime = activationDate.getTime() - System.currentTimeMillis();
//        if (waitTime > 0) {
//            Thread.sleep(waitTime + 1000); // 활성화 시간 직후까지 기다립니다.
//        }
//
//        // 활성화 날짜 이후 check 변수가 true인지 확인합니다.
//        assert rservice.check;
//        System.out.println("After activation, check: " + rservice.check);
//
//        // duration이 지난 후 check 변수가 false로 변경되었는지 확인합니다.
//        Thread.sleep(duration + 100); // duration 시간과 작은 버퍼 시간을 기다립니다.
//
//        // duration이 지난 후 check 변수가 false로 변경되었는지 확인합니다.
//        assert !rservice.check;
//        System.out.println("After duration, check: " + rservice.check);
    }


}
