package com.chunjae.allforclass.service;

public interface UserService {

    boolean checkUser(String email, String pwd);

    int findUid(String email);
}
