import com.chunjae.allforclass.dao.PurchaseMapper;
import com.chunjae.allforclass.service.PurchaseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
public class PurchaseTest {
    @Autowired
    private PurchaseService pservice;

    @Autowired
    private PurchaseMapper pmapper;

    @Test
    public void detailLec(){
        Assertions.assertEquals("박지성", pservice.detailLec(1).getTname());
    }

    @Test
    public void isReserved(){
//        HashMap<String,Object> hm = new HashMap<>();
//        hm.put("sessionId",1);
//        hm.put("lid",1);
//        Assertions.assertEquals(null,pmapper.isReserved(hm));
        Assertions.assertEquals(false,pservice.isReserved(1,1));
    }

}
