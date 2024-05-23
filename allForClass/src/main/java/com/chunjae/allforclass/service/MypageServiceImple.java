package com.chunjae.allforclass.service;

import com.chunjae.allforclass.dao.LectureMapper;
import com.chunjae.allforclass.dao.MypageMapper;
import com.chunjae.allforclass.dao.PurchaseMapper;
import com.chunjae.allforclass.dto.CalDTO;
import com.chunjae.allforclass.dto.LecDTO;
import com.chunjae.allforclass.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class MypageServiceImple implements MypageService{

    private final MypageMapper mymapper;

    public MypageServiceImple(MypageMapper mypageMapper) {
        this.mymapper=mypageMapper;
    }

    /**강의 등록*/
    @Override
    public int insertLec(LecDTO dto) {
        int result = mymapper.insertLec(dto);

        return result;
    }

    /**회원 정보*/
    @Override
    public UserDTO detailMe(int uid) {
        UserDTO dto = mymapper.detailMe(uid);

        return dto;
    }

    /**수강 강의 목록*/
    @Override
    public List<CalDTO> findPurList(int uid) {
        List<CalDTO> list = mymapper.findPurList(uid);

        return list;
    }

    /**지난 강의 목록*/
    @Override
    public List<LecDTO> findPastMyLecList(String curr_day, String curr_session, int uid) {

        HashMap<String, Object> hm = new HashMap<>();
        hm.put("curr_day", curr_day);
        hm.put("curr_session", curr_session);
        hm.put("uid", uid);

        List<LecDTO> list = mymapper.findPastMyLecList(hm);

        return list;
    }

    /**예정 강의 목록*/
    @Override
    public List<LecDTO> findConfirmedMyLecList(String curr_day, String curr_session, int uid) {

        HashMap<String, Object> hm = new HashMap<>();
        hm.put("curr_day", curr_day);
        hm.put("curr_session", curr_session);
        hm.put("uid", uid);

        List<LecDTO> list = mymapper.findConfirmedMyLecList(hm);
        return list;
    }

    @Override
    public List<LecDTO> findWaitMyLecList(int uid) {
        List<LecDTO> list = mymapper.findWaitMyLecList(uid);
        return list;
    }
}
