package com.chunjae.allforclass.dao;

import com.chunjae.allforclass.dto.LecDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface LectureMapper {
    LecDTO detailLec(int lid);

    List<LecDTO> findLecList(HashMap<String, Object> hm);

}
