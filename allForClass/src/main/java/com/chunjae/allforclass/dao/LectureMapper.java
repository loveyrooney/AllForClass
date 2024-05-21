package com.chunjae.allforclass.dao;

import com.chunjae.allforclass.dto.LecDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LectureMapper {
    LecDTO detailLec(int lid);
}
