import com.chunjae.allforclass.dao.UserMapper;
import com.chunjae.allforclass.dto.UserDTO;
import com.chunjae.allforclass.service.UserService;
import com.chunjae.allforclass.service.UserServiceImple;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
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
        Assertions.assertNotNull(userService.checkUser("d","d", true));
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

    @Test
    public void t4(){
        Assertions.assertEquals("김남일",userService.findUser(1).get("uname"));
    }


    @Test
    public void t5(){
        int result = userService.emailCheck("qwe");

        Assertions.assertEquals(1, result);
    }

    @Test
    public void t6(){
        UserDTO dto = new UserDTO();
        dto.setUid(1);
        dto.setEmail("aaa");
        dto.setUname("희나");
        int result = userService.updateUser(dto);

        Assertions.assertEquals(1, result);
    }

    @Test
    public void updateUser(){
        UserDTO dto = new UserDTO();
        dto.setEmail("hwang@test.com");
        dto.setUname("황희찬");
        dto.setPwd("h123");
        dto.setUid(7);
        Assertions.assertEquals(1,userService.updateUser(dto));
    }

    @Test
    public void chcekUser(){
        Assertions.assertEquals(true,userService.checkUser("hwang@test.com","h123", false));
    }
}
