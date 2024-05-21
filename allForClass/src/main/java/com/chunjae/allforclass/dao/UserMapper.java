package com.chunjae.allforclass.dao;

import com.chunjae.allforclass.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.Optional;

@Mapper
public interface UserMapper {

    String checkUser(HashMap<String, Object> hm);

    Integer findUid(String email);

    Integer checkRole(int sessionId);
  
    int join(UserDTO dto);

}
