package com.chunjae.allforclass.dao;

import com.chunjae.allforclass.dto.LecDTO;
import com.chunjae.allforclass.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminMapper {

    List<LecDTO> lecList();

    List<UserDTO> uList();

    int updateLecResult(LecDTO dto);

    int confirm(int lid);

    int deleteLec(int lid);
}
