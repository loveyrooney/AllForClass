package com.chunjae.allforclass.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;

@Mapper
public interface UserMapper {

    String checkUser(HashMap<String, Object> hm);

    int findUid(String email);
}
