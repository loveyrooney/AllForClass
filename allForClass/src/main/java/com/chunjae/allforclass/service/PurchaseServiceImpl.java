package com.chunjae.allforclass.service;

import com.chunjae.allforclass.dao.LectureMapper;
import com.chunjae.allforclass.dao.PurchaseMapper;
import com.chunjae.allforclass.dto.LecDTO;
import org.springframework.stereotype.Service;

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
}
