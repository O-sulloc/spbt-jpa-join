package com.study.spbtjpajoin.service;

import com.study.spbtjpajoin.domain.User;
import com.study.spbtjpajoin.domain.dto.UserJoinRequest;
import com.study.spbtjpajoin.domain.dto.UserDto;
import com.study.spbtjpajoin.exception.ErrorCode;
import com.study.spbtjpajoin.exception.HospitalException;
import com.study.spbtjpajoin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public UserDto join(UserJoinRequest userJoinRequest) {
        userRepository.findByUserName(userJoinRequest.getUserName())
                .ifPresent(user -> {
                    throw new HospitalException(ErrorCode.DUPLICATED_USER_NAME, ErrorCode.DUPLICATED_USER_NAME.getMessage());
                    //Custom Exception
                });

        User user = userRepository.save(userJoinRequest.toEntity(encoder.encode(userJoinRequest.getPassword()))); //entity 들어가야됨
        //encoder.encode(userJoinRequest.getPassword()); //password encode

        return new UserDto(user.getUserName(), user.getPassword());
    }
}
