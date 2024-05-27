package com.chunjae.allforclass.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    //INVALID_ACCESS(HttpStatus.UNAUTHORIZED,"로그인 후 이용할 수 있습니다."),
    //FAIL_TO_MAILSEND(HttpStatus.INTERNAL_SERVER_ERROR, "메일 발신 과정에서 장애가 발생하였습니다."),
    FAIL_TO_PAYMENT(HttpStatus.INTERNAL_SERVER_ERROR, "결제 과정에서 장애가 발생하였습니다."),
    NOT_EXIST_BOARD(HttpStatus.NOT_FOUND, "페이지를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
