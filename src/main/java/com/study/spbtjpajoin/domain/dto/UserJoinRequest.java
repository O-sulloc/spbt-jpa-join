package com.study.spbtjpajoin.domain.dto;

import com.study.spbtjpajoin.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserJoinRequest {

    private String userName;
    private String password;

    public User toEntity() {

        return User.builder()
                .userName(this.userName)
                .password(this.password)
                .build();
    }
}
