package com.learn.jwt.repository;

import com.learn.jwt.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findByUsername(String username);
}
