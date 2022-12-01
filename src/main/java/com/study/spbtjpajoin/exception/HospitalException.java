package com.study.spbtjpajoin.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.ExceptionHandler;

@AllArgsConstructor
@Getter
public class HospitalException extends RuntimeException {

    private ErrorCode errorCode;
    private String message;
}
