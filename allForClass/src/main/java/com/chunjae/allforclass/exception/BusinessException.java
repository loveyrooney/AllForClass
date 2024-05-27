package com.chunjae.allforclass.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BusinessException extends Exception{
    private final ErrorCode errorCode;
}
