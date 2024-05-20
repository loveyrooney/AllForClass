package com.chunjae.allforclass.service;

import com.chunjae.allforclass.dto.RefDTO;
import com.chunjae.allforclass.dto.ReplyDTO;
import com.chunjae.allforclass.dto.VideoDTO;

import java.util.List;

public interface RoomService {
    String enterroom(int pid);

    VideoDTO detailvideo(int lid);

    RefDTO detailref(int lid);

    List<ReplyDTO> replylist();

    VideoDTO insertvideo(VideoDTO vdto);

    RefDTO insertref(RefDTO rdto);

    void delReply(int rid);
}
