package com.study.spbtjpajoin.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // !!!!!enum!!!!

    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "유저 네임 중복"), //가입시 유저네임 중복
    USERNAME_NOT_FOUND(HttpStatus.NOT_FOUND,"없는 아이디입니다."), //없는 유저네임으로 로그인 시도
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED,"비밀번호가 일치하지 않습니다."); //일치하지 않는 패스워드로 로그인 시

    private HttpStatus status;
    private String message;


}
