package com.chunjae.allforclass.dao;

import com.chunjae.allforclass.dto.LecDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MypageMapper {
    int insertLec(LecDTO dto);
}
