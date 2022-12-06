package com.study.spbtjpajoin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.spbtjpajoin.domain.dto.UserJoinRequest;
import com.study.spbtjpajoin.domain.dto.UserLoginRequest;
import com.study.spbtjpajoin.exception.ErrorCode;
import com.study.spbtjpajoin.exception.HospitalException;
import com.study.spbtjpajoin.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class UserControllerTest {

    @MockBean
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    //UserJoinRequest userJoinRequest = UserJoinRequest.buil

    @Test
    @DisplayName("login success")
    void login_success() throws Exception{
        String userName = "kjh";
        String password = "1q2w3e4r";

        when(userService.login(any(), any())) //id랑 password 두개 받으니까 any, any로
                .thenReturn("token"); //잘 로그인되면 토큰 리턴

        mockMvc.perform(post("users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName, password))))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("login failed - no username")
    void login_failed1() throws Exception{
        String userName = "kjh";
        String password = "1q2w3e4r";

        when(userService.login(any(), any())) //id랑 password 두개 받으니까 any, any로
                .thenThrow(new HospitalException(ErrorCode.USERNAME_NOT_FOUND, ""));

        mockMvc.perform(post("users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName, password))))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("login failed - not match with password")
    void login_failed2() throws Exception{

        String userName = "kjh";
        String password = "1q2w3e4r";

        when(userService.login(any(), any())) //id랑 password 두개 받으니까 any, any로
                .thenThrow(new HospitalException(ErrorCode.INVALID_PASSWORD, ""));

        mockMvc.perform(post("users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName, password))))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}