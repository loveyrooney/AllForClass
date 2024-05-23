import com.chunjae.allforclass.dao.PurchaseMapper;
import com.chunjae.allforclass.service.PurchaseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

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
    public void deletePur(){
        Assertions.assertEquals(true,pservice.deletePur(2));
    }
}
