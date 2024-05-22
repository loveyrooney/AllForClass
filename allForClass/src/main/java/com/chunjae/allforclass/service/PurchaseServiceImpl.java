package com.chunjae.allforclass.service;

import com.chunjae.allforclass.dao.LectureMapper;
import com.chunjae.allforclass.dao.PurchaseMapper;
import com.chunjae.allforclass.dto.LecDTO;
import com.chunjae.allforclass.dto.PurDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class PurchaseServiceImpl implements PurchaseService{
    private final PurchaseMapper pmapper;
    private final LectureMapper lmapper;
    public PurchaseServiceImpl(PurchaseMapper pmapper, LectureMapper lmapper){

        this.pmapper=pmapper;
        this.lmapper=lmapper;
    }

    @Override
    public LecDTO detailLec(int lid) {
        return lmapper.detailLec(lid);
    }

    @Override
    public boolean isReserved(int sessionId, int lid) {
        boolean isRererved = false;
        HashMap<String,Object> hm = new HashMap<>();
        hm.put("sessionId",sessionId);
        hm.put("lid",lid);
        Integer result = pmapper.isReserved(hm);
        if(result!=0)
            isRererved = true;
        return isRererved;
    }

}
