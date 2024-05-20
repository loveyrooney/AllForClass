package com.chunjae.allforclass.service;

import com.chunjae.allforclass.dao.PurchaseMapper;
import com.chunjae.allforclass.dto.LecDTO;
import org.springframework.stereotype.Service;

@Service
public class PurchaseServiceImpl implements PurchaseService{
    private final PurchaseMapper pmapper;
    public PurchaseServiceImpl(PurchaseMapper pmapper){
        this.pmapper=pmapper;
    }

    @Override
    public LecDTO detailLec(int lid) {
        return pmapper.detailLec(lid);
    }
}
