package com.chunjae.allforclass.dao;

import com.chunjae.allforclass.dto.PurDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Mapper
@Repository
public interface PurchaseMapper {

    Integer isReserved(HashMap<String,Object> hm);

    int insertPur(HashMap<String, Object> hm);

    String findPayid(int pid);

    int deletePur(int pid);

    int countPur(int lid);
}
