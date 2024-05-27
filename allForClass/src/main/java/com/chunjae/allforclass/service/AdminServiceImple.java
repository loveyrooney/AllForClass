package com.chunjae.allforclass.service;

import com.chunjae.allforclass.dao.AdminMapper;
import com.chunjae.allforclass.dto.LecDTO;
import com.chunjae.allforclass.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.lang.invoke.LambdaMetafactory;
import java.util.List;

@Service
public class AdminServiceImple implements AdminService{

    private final AdminMapper amapper;

    public AdminServiceImple(AdminMapper amapper) {
        this.amapper = amapper;
    }

    @Override
    public List<LecDTO> lecList() {
        return amapper.lecList();
    }

    @Override
    public List<UserDTO> uList() {
        return amapper.uList();
    }

    @Override
    public int updateLecResult(LecDTO dto) {
        return amapper.updateLecResult(dto);
    }

    @Override
    public int confirm(int lid) {
        return amapper.confirm(lid);
    }

    @Override
    public int deleteLec(int lid) {
        return amapper.deleteLec(lid);
    }
}
