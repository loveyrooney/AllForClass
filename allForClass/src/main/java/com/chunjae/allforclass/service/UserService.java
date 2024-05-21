package com.chunjae.allforclass.service;

import java.util.Optional;

public interface UserService {

    boolean checkUser(String email, String pwd);

    int findUid(String email);

    String checkRole(int sessionId);
}
