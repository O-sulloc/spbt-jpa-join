package com.study.spbtjpajoin.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // !!!!!enum!!!!

    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "유저 네임 중복");

    private HttpStatus status;
    private String message;


}
