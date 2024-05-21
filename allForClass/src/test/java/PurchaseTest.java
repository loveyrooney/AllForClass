import com.chunjae.allforclass.service.PurchaseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
public class PurchaseTest {
    @Autowired
    private PurchaseService pservice;

    @Test
    public void detailLec(){
        Assertions.assertEquals("박지성", pservice.detailLec(1).getTname());
    }
}
