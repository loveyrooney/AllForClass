import com.chunjae.allforclass.dto.LecDTO;
import com.chunjae.allforclass.service.AdminService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
public class AdminTest {

    @Autowired
    private AdminService aservice;


    @Test
    public void lecListTest() {
        assertEquals(1, aservice.lecList().get(0).getLid());
    }

    @Test
    public void updateTest(){
        LecDTO dto = new LecDTO();
        dto.setLid(8);
        dto.setDescription("수정수정설명설명");
        dto.setLname("수정수정 강의명");
        dto.setEntry(10);
        dto.setPrice(1000);
        dto.setSubject("수학");
        dto.setStartdate("2024-05-29");
        dto.setTimesession("time3*15:00-18:00");

        assertEquals(1, aservice.updateLecResult(dto));
    }
}
