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
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
public class RoomTest {

    @Autowired
    private RoomService rservice;

    @Test
    public void detaillec(){
        assertEquals("고등수학", rservice.detailLec(1).getLname());
    }

//    @Test
//    public void detailref(){
//        assertEquals("refpath", rservice.detailref(1).getRefpath());
//    }

    @Test
    public void replyTest(){
        assertEquals("댓글1", rservice.replylist(1).get(0).getContent());
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
    public void refTest1(){
        List<RefDTO> reflist = rservice.detailref(1);

        for(int i=0; i<reflist.size(); i++) {
            String refname = rservice.detailref(1).get(i).getRefpath();
            refname = URLDecoder.decode(refname, StandardCharsets.UTF_8).substring(37);
            System.out.println("........"+refname);
        }

    }
}
