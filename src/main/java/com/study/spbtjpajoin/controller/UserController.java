package com.study.spbtjpajoin.controller;

import com.study.spbtjpajoin.domain.Response;
import com.study.spbtjpajoin.domain.User;
import com.study.spbtjpajoin.domain.dto.UserDto;
import com.study.spbtjpajoin.domain.dto.UserJoinRequest;
import com.study.spbtjpajoin.domain.dto.UserJoinResponse;
import com.study.spbtjpajoin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest userJoinRequest) {
        UserDto userDto = userService.join(userJoinRequest);

        return Response.success(new UserJoinResponse(userDto.getUserName(), userDto.getPassword()));
    }
}
