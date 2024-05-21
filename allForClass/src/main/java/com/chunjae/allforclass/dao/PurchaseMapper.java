package com.chunjae.allforclass.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Mapper
@Repository
public interface PurchaseMapper {

    Integer isReserved(HashMap<String,Object> hm);
}
