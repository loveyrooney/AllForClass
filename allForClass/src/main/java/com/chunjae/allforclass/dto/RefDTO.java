package com.chunjae.allforclass.dto;

import org.springframework.web.multipart.MultipartFile;

public class RefDTO {

    private int refid;
    private int lid;
    private String refpath;

    private MultipartFile[] files;

    public RefDTO() {
    }

    public int getRefid() {
        return refid;
    }

    public void setRefid(int refid) {
        this.refid = refid;
    }

    public int getLid() {
        return lid;
    }

    public void setLid(int lid) {
        this.lid = lid;
    }

    public String getRefpath() {
        return refpath;
    }

    public void setRefpath(String refpath) {
        this.refpath = refpath;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }
}
