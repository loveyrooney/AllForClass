import com.chunjae.allforclass.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
public class UserTest {
    @Autowired
    private UserService userService;
    @Test
    public void t1(){
        Assertions.assertEquals(1, userService.findUid("aaa@aaa.com"));
    }

}
