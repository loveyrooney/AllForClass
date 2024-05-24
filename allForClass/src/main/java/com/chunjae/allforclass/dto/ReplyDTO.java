package com.chunjae.allforclass.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReplyDTO {

    private int rid;
    private int uid;
    private int lid;
    private int urole;
    private String content;
    private String writedate;

}
