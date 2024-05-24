package com.chunjae.allforclass.service;

import com.chunjae.allforclass.dao.LectureMapper;
import com.chunjae.allforclass.dto.LecDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class MainServiceImple implements MainService{

    private final LectureMapper lectureMapper;
    @Autowired
    public MainServiceImple(LectureMapper lectureMapper){
        this.lectureMapper=lectureMapper;
    }

    @Override
    public List<LecDTO> findLecList(boolean confirm, String searchtxt) {
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("confirm", confirm);
        hm.put("searchtxt", searchtxt);

        List<LecDTO> list = lectureMapper.findLecList(hm);

        return list;
    }

}
