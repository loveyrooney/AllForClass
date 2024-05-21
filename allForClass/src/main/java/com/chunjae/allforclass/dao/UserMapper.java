package com.chunjae.allforclass.dao;

import com.chunjae.allforclass.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

@Mapper
public interface UserMapper {

    String checkUser(HashMap<String, Object> hm);

    int findUid(String email);

    int join(UserDTO dto);
}
