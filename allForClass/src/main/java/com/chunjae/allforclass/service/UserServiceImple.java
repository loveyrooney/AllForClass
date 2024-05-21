package com.chunjae.allforclass.service;

import com.chunjae.allforclass.dao.UserMapper;
import com.chunjae.allforclass.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class UserServiceImple implements UserService{

    private final UserMapper userMapper;
    @Autowired
    public UserServiceImple(UserMapper userMapper){
        this.userMapper=userMapper;
    }

    @Override
    public boolean checkUser(String email, String pwd) {
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("email", email);
        hm.put("pwd", pwd);

        String ckemail =userMapper.checkUser(hm);

        boolean result=false;

        if(ckemail!=null && ckemail!="")
            result=true;

        return result;
    }

    @Override
    public int findUid(String email) {
        return userMapper.findUid(email);
    }

    @Override
    public int join(UserDTO dto) {

        int result = userMapper.join(dto);

        return result;
    }
}
