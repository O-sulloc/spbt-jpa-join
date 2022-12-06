package com.study.spbtjpajoin.service;

import com.study.spbtjpajoin.domain.User;
import com.study.spbtjpajoin.domain.dto.UserJoinRequest;
import com.study.spbtjpajoin.domain.dto.UserDto;
import com.study.spbtjpajoin.domain.dto.UserLoginRequest;
import com.study.spbtjpajoin.exception.ErrorCode;
import com.study.spbtjpajoin.exception.HospitalException;
import com.study.spbtjpajoin.repository.UserRepository;
import com.study.spbtjpajoin.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.token.secret}") //환경변수로 설정해둠
    private String secretKey;

    private long expireTime = 1000 * 60 * 60L; //1시간

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

    public String login(String userName, String password) {
        // 1. 있는 username으로 로그인하는지
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new HospitalException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));
                // 없는 username으로 로그인하려고 하면 예외 throw

        // 2. username과 Password 일치하는지
        if (!encoder.matches(password, user.getPassword())) {
            // db에 암호화된 password가 입력 들어온 Pssword와 일치하는지 판별 (순서 중요)

            throw new HospitalException(ErrorCode.INVALID_PASSWORD, ErrorCode.INVALID_PASSWORD.getMessage());

        }

        String token = JwtTokenUtil.createToken(userName, secretKey, expireTime);
        return token;
    }
}
