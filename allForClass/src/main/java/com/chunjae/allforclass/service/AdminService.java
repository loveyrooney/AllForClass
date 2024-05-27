package com.chunjae.allforclass.service;

import com.chunjae.allforclass.dto.LecDTO;
import com.chunjae.allforclass.dto.UserDTO;

import java.util.List;

public interface AdminService {
    List<LecDTO> lecList();

    List<UserDTO> uList();

    int updateLecResult(LecDTO dto);

    int confirm(int lid);

    int deleteLec(int lid);

    List<LecDTO> cList();
}
