package com.chunjae.allforclass.service;

import com.chunjae.allforclass.dto.*;

import java.util.HashMap;
import java.util.List;

public interface RoomService {

    LecDTO detailLec(int lid);

    VideoDTO detailvideo(int lid);

    PurDTO enterroom(int pid);

    List<RefDTO> detailref(int lid);
    int insertref(String realpath, RefDTO refdto);

    List<ReplyDTO> replylist(int lid);

    int replyinsert(ReplyDTO rdto);

    int replydelte(int rid);

    int insertVid(VideoDTO vdto);
}
