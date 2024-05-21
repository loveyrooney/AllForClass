package com.chunjae.allforclass.dao;

import com.chunjae.allforclass.dto.RefDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface RoomMapper {
    int insert(HashMap<String, Object> o);
    int subinsert(HashMap<String, Object> o);

    List<RefDTO> list();

    RefDTO detailref(int lid);
}
