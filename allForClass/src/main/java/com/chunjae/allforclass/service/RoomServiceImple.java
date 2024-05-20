package com.chunjae.allforclass.service;

import com.chunjae.allforclass.dao.*;
import com.chunjae.allforclass.dto.RefDTO;
import com.chunjae.allforclass.dto.ReplyDTO;
import com.chunjae.allforclass.dto.VideoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImple implements RoomService{

    private static final Logger logger = LoggerFactory.getLogger(RoomServiceImple.class);

    @Autowired
    private LectureMapper lectureMapper;
    @Autowired
    private PurchaseMapper purchaseMapper;
    @Autowired
    private RefMapper refMapper;
    @Autowired
    private ReplyMapper replyMapper;
    @Autowired
    private VideoMapper videoMapper;

    @Override
    public String enterroom(int pid) {
        return null;
    }

    @Override
    public VideoDTO detailvideo(int lid) {
        return null;
    }

    @Override
    public RefDTO detailref(int lid) {
        return null;
    }

    @Override
    public List<ReplyDTO> replylist() {
        return null;
    }

    @Override
    public VideoDTO insertvideo(VideoDTO vdto) {
        return null;
    }

    @Override
    public RefDTO insertref(RefDTO rdto) {
        return null;
    }

    @Override
    public void delReply(int rid) {

    }
}
