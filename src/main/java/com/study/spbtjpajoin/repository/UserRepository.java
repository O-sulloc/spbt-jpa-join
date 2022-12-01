package com.study.spbtjpajoin.repository;

import com.study.spbtjpajoin.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName); //userName 중복 확인

}
