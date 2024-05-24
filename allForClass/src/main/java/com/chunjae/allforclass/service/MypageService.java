package com.chunjae.allforclass.service;

import com.chunjae.allforclass.dto.CalDTO;
import com.chunjae.allforclass.dto.LecDTO;
import com.chunjae.allforclass.dto.UserDTO;

import java.util.List;

public interface MypageService {
    int insertLec(LecDTO dto);
    UserDTO detailMe(int uid);
    List<CalDTO> findPurList(int uid);
    List<LecDTO> findMyLecList(String lectype, String curr_day, String curr_session, int uid);
    int lecTotalCount(String lectype, String curr_day, String curr_session, int uid);
}
