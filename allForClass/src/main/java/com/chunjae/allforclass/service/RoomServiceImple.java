package com.chunjae.allforclass.service;

import com.chunjae.allforclass.dao.*;
import com.chunjae.allforclass.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Service
public class RoomServiceImple implements RoomService{

//    private static final Logger logger = LoggerFactory.getLogger(RoomServiceImple.class);

    private final LectureMapper lmapper;
    private final PurchaseMapper pmapper;
    private final RoomMapper rmapper;

    public RoomServiceImple(LectureMapper lmapper, PurchaseMapper pmapper, RoomMapper rmapper){
        this.lmapper = lmapper;
        this.pmapper = pmapper;
        this.rmapper = rmapper;
    }

    @Override
    public LecDTO detailLec(int lid) {
        return lmapper.detailLec(lid);
    }

    @Override
    public VideoDTO detailvideo(int lid) {
        return null;
    }

    @Override
    public PurDTO enterroom(int pid) {
        return null;
    }

    @Override
    public RefDTO detailref(int lid) {
        return null;
    }

//    @Transactional
//    @Override
//    public void insertref(String realpath, RefDTO refdto) throws IOException {
//
//    }

    @Override
    public List<ReplyDTO> replylist() {
        return null;
    }

    @Override
    public int replyinsert(HashMap<String, Object> hm) {
        return 0;
    }

    @Override
    public void insertref(String realpath, RefDTO refdto) {

    }

}
