import com.chunjae.allforclass.dto.CalDTO;
import com.chunjae.allforclass.dto.LecDTO;
import com.chunjae.allforclass.dto.UserDTO;
import com.chunjae.allforclass.service.MypageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
public class MypageTest {

    @Autowired
    private MypageService myservice;

    @Test
    public void inserttest(){
        LecDTO dto = new LecDTO();
        dto.setImgpath("aaa");
        dto.setDescription("설명");
        dto.setLname("강의명");
        dto.setEntry(20);
        dto.setPrice(22000);
        dto.setStartdate("2024-06-30");
        dto.setSubject("과목");
        dto.setTimesession("time1*09:00-12:00");

        int result = myservice.insertLec(dto);

        assertEquals(1, result);
    }

    @Test
    public void findlectest(){
        List<CalDTO> list = myservice.findPurList(113);

        assertEquals("수학test", list.get(3).getLname());
    }

    @Test
    public void userTest(){
        UserDTO dto = myservice.detailMe(113);
        assertEquals(113, dto.getUid());
    }

    @Test
    public void lecCountTest() {
        int result = myservice.lecTotalCount("pastmylec", "2024-05-24", "1", 116);
        assertEquals(64, result);
    }

}
