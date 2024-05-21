package com.chunjae.allforclass.service;


import com.chunjae.allforclass.dto.UserDTO;


public interface UserService {

    boolean checkUser(String email, String pwd);

    int findUid(String email);

    String checkRole(int sessionId);
    
    int join(UserDTO dto);

}
