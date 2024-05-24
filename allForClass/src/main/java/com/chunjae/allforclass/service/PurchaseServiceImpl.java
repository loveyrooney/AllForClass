package com.chunjae.allforclass.service;

import com.chunjae.allforclass.dao.LectureMapper;
import com.chunjae.allforclass.dao.PurchaseMapper;
import com.chunjae.allforclass.dto.LecDTO;
import com.chunjae.allforclass.dto.PurDTO;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
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
    public int isReserved(int sessionId, int lid) {
        int isReserved = 0;
        HashMap<String,Object> hm = new HashMap<>();
        hm.put("sessionId",sessionId);
        hm.put("lid",lid);
        Integer result = pmapper.isReserved(hm);
        if(result !=null)
            isReserved = result;
        return isReserved;
    }

    @Override
    public int checkPrice(int lid) {
        int price = 0;
        Integer result = lmapper.checkPrice(lid);
        if(result!=null)
            price = result;
        return price;
    }

    @Override
    public boolean insertPur(HashMap<String, Object> hm){
        int result = pmapper.insertPur(hm);
        //if(result==0){}
        return true;
    }

    @Override
    public String findPayid(int pid) {
        return pmapper.findPayid(pid);
    }

    @Override
    public boolean deletePur(int pid) {
        boolean correct = false;
        int result = pmapper.deletePur(pid);
        if(result!=0)
            correct = true;
        return correct;
    }

    @Override
    public int countPur(int lid) {
        return pmapper.countPur(lid);
    }

    @Override
    public int checkSchedule(HashMap<String, Object> hm) {
        return pmapper.checkSchedule(hm);
    }
}
