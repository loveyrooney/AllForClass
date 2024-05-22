package com.chunjae.allforclass.controller;

import com.chunjae.allforclass.dto.LecDTO;
import com.chunjae.allforclass.dto.PurDTO;
import com.chunjae.allforclass.dto.UserDTO;
import com.chunjae.allforclass.service.PurchaseService;
import com.chunjae.allforclass.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

@Controller
public class PurchaseController {
    private final PurchaseService pservice;
    private final UserService uservice;
    public PurchaseController(PurchaseService pservice, UserService uservice){
        this.pservice=pservice;
        this.uservice=uservice;
    }

    private final Logger logger = LoggerFactory.getLogger("PurchaseController.class");
    private static HashMap<String,String> payprops = new HashMap<>();
    private void initProps() {
        ClassPathResource resource = new ClassPathResource("pay.properties");
        try{
            Path path = Paths.get(resource.getURI());
            List<String> content = Files.readAllLines(path);
            for(String s:content){
                payprops.put(s.split("=")[0],s.split("=")[1]);
            }
        } catch (IOException e){
            logger.error("payprops : {}",e.getMessage());
        }
    }

    @GetMapping("/detail_lec/{lid}")
    public String detail_lec(@PathVariable int lid, HttpServletRequest request, Model model){
        HttpSession session = request.getSession(false);
        if(session!=null){
            int sessionId = (int)session.getAttribute("sessionId");
            String role = uservice.checkRole(sessionId);
            model.addAttribute("role",role);
            if(role.equals("student")){
                boolean isReserved = pservice.isReserved(sessionId,lid);
                if(!isReserved){
                    initProps();
                    HashMap<String,Object> user = uservice.findUser(sessionId);
                    model.addAttribute("user",user);
                    model.addAttribute("storeId", payprops.get("STORE_ID"));
                    model.addAttribute("channelKey", payprops.get("CHANNEL_KEY"));
                }
                model.addAttribute("isReserved",isReserved);
            }
        }
        LecDTO dto = pservice.detailLec(lid);
        model.addAttribute("dto",dto);
        model.addAttribute("body","purchase/detail_lec.jsp");
        model.addAttribute("title","모두의 국영수 - "+dto.getLname());
        return "main";
    }

    @PostMapping("/payment/complete")
    public @ResponseBody HashMap<String, Object> paycomplete(@RequestBody HashMap<String,Object> hm) throws IOException, InterruptedException {
        logger.info("payment paymentId : {}",hm.get("paymentId"));
        logger.info("payment uid : {}", hm.get("uid"));
        logger.info("payment lid : {}", hm.get("lid"));
        int priceFromDB = 1000;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.portone.io/payments/"+hm.get("paymentId")))
                .header("Authorization", "PortOne "+payprops.get("V2_SECRET"))
                .method("GET", HttpRequest.BodyPublishers.ofString("{}"))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        HashMap<String,Object> result = new HashMap<>();
        if(response.body().contains("\"paid\":"+priceFromDB)
                && response.body().contains("\"id\":\""+hm.get("paymentId")+"\"")){
            result.put("correct",true);
            result.put("msg","정상 결제되었습니다.");
        } else {
            result.put("correct",false);
            result.put("msg","결제가 실패하였습니다.");
        }
        return result;
    }

    @GetMapping("/payment/cancel/{uid}")
    public @ResponseBody HashMap<String, Object> payRefund(@PathVariable int uid) throws IOException, InterruptedException{
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.portone.io/payments/"+uid+"/cancel"))
                .header("Authorization", "PortOne "+payprops.get("V2_SECRET"))
                .method("POST", HttpRequest.BodyPublishers.ofString("{\"reason\":\"reason\"}"))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        HashMap<String,Object> result = new HashMap<>();
        if(response.statusCode()==200){
            result.put("correct",true);
            result.put("msg","정상 환불되었습니다.");
        } else {
            result.put("correct",false);
            result.put("msg","환불이 실패하였습니다.");
        }
        return result;
    }


}
