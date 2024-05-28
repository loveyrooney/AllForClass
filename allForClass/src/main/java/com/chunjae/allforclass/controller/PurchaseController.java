package com.chunjae.allforclass.controller;

import com.chunjae.allforclass.dto.LecDTO;
import com.chunjae.allforclass.dto.MailDTO;
import com.chunjae.allforclass.exception.BusinessException;
import com.chunjae.allforclass.exception.ErrorCode;
import com.chunjae.allforclass.service.PurchaseService;
import com.chunjae.allforclass.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Controller
public class PurchaseController {
    private final PurchaseService pservice;
    private final UserService uservice;
    public PurchaseController(PurchaseService pservice, UserService uservice){
        this.pservice=pservice;
        this.uservice=uservice;
        initProps();
    }

    private static final Logger logger = LoggerFactory.getLogger("PurchaseController.class");

    // 결제 관련 정보 보관
    private static final HashMap<String,String> payprops = new HashMap<>();

    // 결제 관련 정보 세팅 메서드
    private static void initProps() {
        // 결제 관련 정보를 properties 파일에서 가져오기
        ClassPathResource resource = new ClassPathResource("pay.properties");
        try{
            Path path = Paths.get(resource.getURI());
            List<String> content = Files.readAllLines(path);
            for(String s:content){
                // = 기준 0번째는 key, 1번째는 value
                payprops.put(s.split("=")[0],s.split("=")[1]);
            }
        } catch (IOException e){
            logger.error("payprops : {}",e.getMessage());
        }
    }

    @GetMapping("/detail_lec/{lid}")
    public String detail_lec(@PathVariable int lid, HttpServletRequest request, Model model){
        // 모든 사용자에게 공통으로 보여질 정보 - 강의 상세정보, 수강신청 오픈기간 여부
        LecDTO dto = pservice.detailLec(lid);
        model.addAttribute("dto",dto);
        boolean available = LocalDate.now().isBefore(LocalDate.parse(dto.getStartdate()));
        if(available)
            model.addAttribute("available",1);
        else
            model.addAttribute("available",0);
        // 해당 강의의 결제 건이 몇 개 있는지 체크
        int reserve = pservice.countPur(lid);
        model.addAttribute("reserve",dto.getEntry()-reserve);
        // 로그인 사용자에게 보여질 정보
        HttpSession session = request.getSession(false);
        if(session!=null && session.getAttribute("sessionId")!=null){ // 세션이 있는경우 사용자의 role check
            int sessionId = (int)session.getAttribute("sessionId");
            String role = uservice.checkRole(sessionId);
            model.addAttribute("role",role);
                if(role.equals("student")){  // 사용자의 role 이 수강생인 경우
                    int pid = pservice.isReserved(sessionId,lid);
                    if(pid==0){ // 해당 강의를 수강신청 한 적 없는 경우
                        HashMap<String,Object> user = uservice.findUser(sessionId);
                        model.addAttribute("user",user);
                        model.addAttribute("storeId", payprops.get("STORE_ID"));
                        model.addAttribute("channelKey", payprops.get("CHANNEL_KEY"));
                    } else { // 해당 강의를 수강신청한 경우
                        model.addAttribute("pid",pid);
                        model.addAttribute("enterroom",1);
                    }
                } else {
                    model.addAttribute("enterroom",1);
                }
        }
        model.addAttribute("body","purchase/detail_lec.jsp");
        model.addAttribute("title","모두의 국영수 - "+dto.getLname());
        return "main";
    }

    @PostMapping("/checkschedule/{lid}")
    public @ResponseBody HashMap<String,Object> checkschedule(@PathVariable int lid, @RequestBody HashMap<String,Object> hm){
        // 해당 스케줄에 사용자가 이미 결제한 강의가 있는지 체크, 없으면 0, 있으면 1
        int checkSchedule = pservice.checkSchedule(hm);
        // 해당 강의의 결제 건이 몇 개 있는지 체크
        int reserve = pservice.countPur(lid);
        HashMap<String,Object> data = new HashMap<>();
        // 결제자가 정원미만이고, 스케줄이 비어있는 경우 수강신청 가능
         if((int)hm.get("entry")-reserve>0){
            if (checkSchedule == 0) {
                data.put("available", 1);
            } else {
                data.put("available",0);
                data.put("msg","해당 스케줄에 이미 수강신청한 강의가 있습니다.");
            }
        }else {
            data.put("available",0);
            data.put("msg","수강 정원이 꽉 찼습니다.");
        }
        return data;
    }

    @PostMapping("/payment/complete")
    public @ResponseBody HashMap<String, Object> paycomplete(@RequestBody HashMap<String,Object> hm) throws BusinessException {
        logger.info("payment paymentId : {}",hm.get("paymentId"));
        int lid = (int)hm.get("lid");
        String payid = (String) hm.get("paymentId");
        // 결과 리턴할 해시 맵
        HashMap<String,Object> result = new HashMap<>();
        try{
            // 결제 단건 조회 api 비동기 방식 요청
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, new java.security.SecureRandom());
            HttpClient client = HttpClient.newBuilder()
                    .sslContext(sslContext)
                    .build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.portone.io/payments/"+payid))
                    .header("Authorization", "PortOne "+payprops.get("V2_SECRET"))
                    .method("GET", HttpRequest.BodyPublishers.ofString("{}"))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // 결제 정보의 paymentId 와 price 가 우리 DB 정보와 일치하는지 체크
            int priceFromDB = pservice.checkPrice(lid);
            if(response.body().contains("\"paid\":"+priceFromDB)
                    && response.body().contains("\"id\":\""+payid+"\"")){
                boolean correct = pservice.insertPur(hm);
                result.put("correct",correct);
                result.put("msg","정상 결제되었습니다.");
            } else {
                result.put("correct", false);
                result.put("msg","결제가 실패하였습니다.");
            }
            logger.info("payment complete : {}",response);
        }catch (IOException|InterruptedException|NoSuchAlgorithmException|KeyManagementException e){
            logger.error("payment complete exception : {}",e.getMessage());
            throw new BusinessException(ErrorCode.FAIL_TO_PAYMENT);
        }
        return result;
    }

    @GetMapping("/payment/cancel/{pid}")
    public @ResponseBody HashMap<String, Object> payRefund(@PathVariable int pid) throws BusinessException {
        // 우리 DB에 있는 사용자 pid 를 통해 결제 시 발급받은 paymentId 조회 (결제 paymentId 외부 노출 안하기 위해서)
        String payid = pservice.findPayid(pid);
        // 결과 리턴할 해시 맵
        HashMap<String,Object> result = new HashMap<>();
        try{
            // 결제 취소 api 비동기 방식 요청
            //System.setProperty("javax.net.debug", "ssl,handshake");
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, new java.security.SecureRandom());
            HttpClient client = HttpClient.newBuilder()
                    .sslContext(sslContext)
                    .build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.portone.io/payments/"+payid+"/cancel"))
                    .header("Authorization", "PortOne "+payprops.get("V2_SECRET"))
                    .header("Content-Type", "application/json")
                    .method("POST", HttpRequest.BodyPublishers.ofString("{\"reason\":\"reason\"}"))
                    .build();
            System.out.println("으아가가가 : "+request.uri());
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            logger.info("payment cancel : {}",response.body());
            logger.info("payment cancel status : {}", response.statusCode());
            // 결제 취소가 성공한 경우 우리 DB 에서 결제 정보 삭제
            if(response.statusCode()==200){
                boolean correct = pservice.deletePur(pid);
                result.put("correct",correct);
                result.put("msg","정상 환불되었습니다.");
            } else {
                result.put("correct",false);
                result.put("msg","환불이 실패하였습니다.");
            }
        }catch (IOException|InterruptedException|NoSuchAlgorithmException|KeyManagementException e){
            logger.error("payment complete exception : {}",e.getMessage());
            throw new BusinessException(ErrorCode.FAIL_TO_PAYMENT);
        }
        return result;
    }

}
