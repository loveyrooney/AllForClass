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

    /**등록한 강의 목록*/
    @Override
    public List<LecDTO> findMyLecList(String lectype, String curr_day, String curr_session, int uid) {
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("lectype", lectype);
        hm.put("curr_day", curr_day);
        hm.put("curr_session", curr_session);
        hm.put("uid", uid);

        List<LecDTO> list = mymapper.findMyLecList(hm);

        return list;
    }

    /**등록한 강의 목록 총 갯수*/
    @Override
    public int lecTotalCount(String lectype, String curr_day, String curr_session, int uid) {
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("lectype", lectype);
        hm.put("curr_day", curr_day);
        hm.put("curr_session", curr_session);
        hm.put("uid", uid);

        int totalCount = mymapper.lecTotalCount(hm);
        return totalCount;
    }

    /**강의 신청시 해당 시간에 강의 있는지 조회*/
    @Override
    public int checkLecTime(int uid, String startdate, String timesession) {
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("uid", uid);
        hm.put("startdate", startdate);
        hm.put("timesession", timesession);

        int result = mymapper.checkLecTime(hm);

        return result;
    }
}
