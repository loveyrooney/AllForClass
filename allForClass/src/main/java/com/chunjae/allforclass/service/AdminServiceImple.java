package com.chunjae.allforclass.service;

import com.chunjae.allforclass.dao.AdminMapper;
import com.chunjae.allforclass.dto.LecDTO;
import com.chunjae.allforclass.dto.UserDTO;
import org.springframework.stereotype.Service;

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
}
