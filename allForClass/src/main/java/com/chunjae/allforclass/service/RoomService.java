package com.chunjae.allforclass.service;

import com.chunjae.allforclass.dto.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface RoomService {

    LecDTO detailLec(int lid);

    VideoDTO detailvideo(int lid);

    List<RefDTO> detailref(int lid);
    int insertref(String realpath, RefDTO refdto);

    List<ReplyDTO> replylist(int lid);

    int replyinsert(ReplyDTO rdto);

    int replydelte(int rid);

    int insertVid(VideoDTO vdto);

    int deleteVid(int vid);

}
