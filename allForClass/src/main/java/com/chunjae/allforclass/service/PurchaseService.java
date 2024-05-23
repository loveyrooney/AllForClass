package com.chunjae.allforclass.service;

import com.chunjae.allforclass.dto.LecDTO;

import java.util.HashMap;

public interface PurchaseService {

    LecDTO detailLec(int lid);
    int isReserved(int sessionId, int lid);

    int checkPrice(int lid);

    boolean insertPur(HashMap<String, Object> hm);

    String findPayid(int pid);

    boolean deletePur(int pid);
}
