package com.chunjae.allforclass.service;

import com.chunjae.allforclass.dto.*;

import java.util.HashMap;
import java.util.List;

public interface RoomService {

    LecDTO detailLec(int lid);

    VideoDTO detailvideo(int lid);

    PurDTO enterroom(int pid);

    RefDTO detailref(int lid);

    List<ReplyDTO> replylist();

    int replyinsert(HashMap<String, Object> hm);

    int insertref(String realpath, RefDTO refdto);
}
