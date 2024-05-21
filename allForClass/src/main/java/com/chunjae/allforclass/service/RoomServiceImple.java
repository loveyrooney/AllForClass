package com.chunjae.allforclass.service;

import com.chunjae.allforclass.dao.*;
import com.chunjae.allforclass.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class RoomServiceImple implements RoomService {

    private final LectureMapper lmapper;
    private final PurchaseMapper pmapper;
    private final RoomMapper rmapper;

    public RoomServiceImple(LectureMapper lmapper, PurchaseMapper pmapper, RoomMapper rmapper) {
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
    public List<RefDTO> detailref(int lid) {
        return rmapper.detailref(lid);
    }

    @Transactional
    @Override
    public int insertref(String path, RefDTO refdto) {
        Logger logger = LoggerFactory.getLogger(RoomServiceImple.class);
        logger.info(".....  room service imple  dto...{}", refdto.getFiles().length);
        HashMap<String, Object> o = new HashMap<>();
        o.put("refdto", refdto);

        if (refdto.getFiles() != null) {
            String[] fnames = fileUpload(path, refdto);
            List<String> filenames = Arrays.asList(fnames);
            o.put("filenames", filenames);
            logger.info("filenames....{}", filenames.get(0));
        }
        int result = 0;
        if (refdto.getFiles() != null) {
            result = rmapper.insertref(o);
        }
        logger.info("key out {}", o.get("id"));
        logger.info("data complete... {}", result);
        return result;
    }

    private String[] fileUpload(String path, RefDTO refdto) {
        System.out.println(refdto.getFiles().length + "........length.....");
        File[] saveFile = new File[refdto.getFiles().length];
        String[] filenames = new String[refdto.getFiles().length];

        for (int i = 0; i < refdto.getFiles().length; i++) {
            UUID uuid = UUID.randomUUID();

            String fname = refdto.getFiles()[i].getOriginalFilename();
            System.out.println("original filename ,..." + fname);

            System.out.println("fname...." + fname);
            fname = URLEncoder.encode(fname, StandardCharsets.UTF_8)
                    .replace("+", "%20");
            String filename = uuid + "_" + fname;
            saveFile[i] = new File(path, filename);
            System.out.println("filename....length : " + filename.length());
            filenames[i] = filename;
        } // file 생성

        try {
            for (int i = 0; i < saveFile.length; i++) {
                refdto.getFiles()[i].transferTo(saveFile[i]);
            }
        } catch (IOException e) {
            for (int i = 0; i < saveFile.length; i++)
                saveFile[i].delete();
            throw new RuntimeException();
        }
        return filenames;

    }


    @Override
    public List<ReplyDTO> replylist(int lid) {
        List<ReplyDTO> rlist = rmapper.replylist(lid);
        return rlist;
    }

    @Override
    public int replyinsert(ReplyDTO rdto) {
        return rmapper.replyinsert(rdto);
    }

}
