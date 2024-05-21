package com.chunjae.allforclass.dao;

import com.chunjae.allforclass.dto.RefDTO;
import com.chunjae.allforclass.dto.ReplyDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface RoomMapper {
    int insertref(HashMap<String, Object> o);

    List<RefDTO> detailref(int lid);

    List<ReplyDTO> replylist(int lid);

    int replyinsert(ReplyDTO rdto);
}
