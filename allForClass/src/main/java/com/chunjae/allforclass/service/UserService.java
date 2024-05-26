package com.chunjae.allforclass.service;


import com.chunjae.allforclass.dto.UserDTO;

import java.util.HashMap;


public interface UserService {

    boolean checkUser(String email, String pwd);

    int findUid(String email);

    String checkRole(int sessionId);
    
    int join(UserDTO dto);

    HashMap<String,Object> findUser(int sessionId);


    int emailCheck(String email);

    int updateUser(UserDTO dto);

    int deleteUser(int uid);
}
