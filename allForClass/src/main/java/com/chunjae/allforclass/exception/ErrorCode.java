package com.chunjae.allforclass.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    //FAIL_TO_MAILSEND(HttpStatus.INTERNAL_SERVER_ERROR, "메일 발신 과정에서 장애가 발생하였습니다."),
    //INVALID_ACCESS(HttpStatus.UNAUTHORIZED,"[미승인강의] 승인받은 강의가 아닙니다."),
    FAIL_TO_PAYMENT(HttpStatus.INTERNAL_SERVER_ERROR, "[결제확인불가] 수강 신청에 실패하였습니다. 관리자에게 문의 바랍니다."),
    FAIL_TO_PAYCHECK(HttpStatus.INTERNAL_SERVER_ERROR, "[정상결제] 수강 신청에 실패하였습니다. 관리자에게 문의 바랍니다."),
    FAIL_TO_CANCELCLASS(HttpStatus.INTERNAL_SERVER_ERROR, "[정상환불] 수강 취소에 실패하였습니다. 관리자에게 문의 바랍니다."),
    FAIL_TO_REFUND(HttpStatus.INTERNAL_SERVER_ERROR, "[환불확인불가] 수강 취소에 실패하였습니다. 관리자에게 문의 바랍니다.");
    //NOT_EXIST_BOARD(HttpStatus.NOT_FOUND, "페이지를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
