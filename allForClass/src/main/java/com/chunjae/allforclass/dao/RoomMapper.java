package com.chunjae.allforclass.dao;

import com.chunjae.allforclass.dto.RefDTO;
import com.chunjae.allforclass.dto.ReplyDTO;
import com.chunjae.allforclass.dto.VideoDTO;
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

    int replydelete(int rid);

    VideoDTO detailvideo(int lid);

    int insertVid(VideoDTO vdto);

    int deleteVid(int vid);

    int deleteRef(String fileName);
}
