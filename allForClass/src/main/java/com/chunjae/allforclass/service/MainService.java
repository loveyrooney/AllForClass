package com.chunjae.allforclass.service;

import com.chunjae.allforclass.dto.LecDTO;

import java.util.List;

public interface MainService {


    List<LecDTO> findLecList(boolean confirm);
}
