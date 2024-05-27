package com.chunjae.allforclass.service;

import com.chunjae.allforclass.dto.LecDTO;
import com.chunjae.allforclass.dto.MailDTO;
import com.chunjae.allforclass.exception.BusinessException;

import java.util.HashMap;
import java.util.List;

public interface PurchaseService {

    LecDTO detailLec(int lid);
    int isReserved(int sessionId, int lid);

    int checkPrice(int lid);

    boolean insertPur(HashMap<String, Object> hm);

    String findPayid(int pid);

    boolean deletePur(int pid);

    int countPur(int lid);

    int checkSchedule(HashMap<String, Object> hm);

    List<MailDTO> sendMailList();

    void sendHtmlEmail() throws BusinessException;

    //void schedule();
}
