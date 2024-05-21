package com.chunjae.allforclass.service;

import com.chunjae.allforclass.dao.LectureMapper;
import com.chunjae.allforclass.dao.MypageMapper;
import com.chunjae.allforclass.dao.PurchaseMapper;
import com.chunjae.allforclass.dto.LecDTO;
import org.springframework.stereotype.Service;

@Service
public class MypageServiceImple implements MypageService{

    private final MypageMapper mymapper;

    public MypageServiceImple(MypageMapper mypageMapper) {
        this.mymapper=mypageMapper;
    }

    @Override
    public int insertLec(LecDTO dto) {
        int result = mymapper.insertLec(dto);


        return result;
    }
}
