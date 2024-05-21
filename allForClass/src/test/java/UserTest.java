import com.chunjae.allforclass.dao.UserMapper;
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

    @Autowired
    private UserMapper userMapper;

    @Test
    public void t2(){
        Assertions.assertNull(userService.checkUser("a","123"));
    }
    @Test
    public void t1(){
        //Assertions.assertEquals(null,userMapper.findUid("a"));
        Assertions.assertEquals(0,userService.findUid("a"));
        //Assertions.assertEquals(1, userService.findUid("aaa@aaa.com"));
    }

    @Test
    public void t3(){
        Assertions.assertEquals("student",userService.checkRole(1));
    }

}
