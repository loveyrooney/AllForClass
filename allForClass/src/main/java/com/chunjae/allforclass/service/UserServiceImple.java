package com.chunjae.allforclass.service;

import com.chunjae.allforclass.dao.UserMapper;
import com.chunjae.allforclass.dto.UserDTO;
import org.mindrot.jbcrypt.BCrypt;
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
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Override
    public boolean checkUser(String email, String pwd, boolean disable) {
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("email", email);
        hm.put("disable", disable);
        String pwdFromDB =userMapper.checkUser(hm);
        //System.out.println(pwdFromDB);
        boolean result=false;

        //if(BCrypt.checkpw(pwd,pwdFromDB))
        if(pwd.equals(pwdFromDB))
            result=true;

        return result;
    }

    @Override
    public int findUid(String email) {
        Integer result = userMapper.findUid(email);
        if(result==null)
            return 0;
        else
            return result;
    }

    @Override
    public String checkRole(int sessionId) {
        Integer result = userMapper.checkRole(sessionId);
        String role = "";
        if(result!=null){
            if(result==1)
                role = "student";
            else if(result==2)
                role = "teacher";
            else
                role = "admin";
        }
        return role;
    }

    @Override
    public int join(UserDTO dto) {
        //dto.setPwd(hashPassword(dto.getPwd()));
        int result = userMapper.join(dto);
        return result;
    }

    @Override
    public HashMap<String,Object> findUser(int sessionId) {
        return userMapper.findUser(sessionId);
    }



    @Override
    public int emailCheck(String email) {
        int cnt = userMapper.emailCheck(email);
        return cnt;
    }

    @Override
    public int updateUser(UserDTO dto) {
        //if(dto.getPwd()!=null && !dto.getPwd().equals(""))
            //dto.setPwd(hashPassword(dto.getPwd()));
        int result = userMapper.updateUser(dto);
        return result;
    }

    @Override
    public int deleteUser(int uid) {
        int result = userMapper.deleteUser(uid);
        return result;
    }


}
