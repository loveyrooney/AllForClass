package com.chunjae.allforclass.dao;

import com.chunjae.allforclass.dto.CalDTO;
import com.chunjae.allforclass.dto.LecDTO;
import com.chunjae.allforclass.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface MypageMapper {
    int insertLec(LecDTO dto);
    UserDTO detailMe(int uid);
    List<CalDTO> findPurList(int uid);
    List<LecDTO> findPastMyLecList(HashMap<String, Object> hm);
    List<LecDTO> findConfirmedMyLecList(HashMap<String, Object> hm);
    List<LecDTO> findWaitMyLecList(int uid);
}
