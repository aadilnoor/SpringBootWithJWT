package com.learn.jwt.service;

import com.learn.jwt.entity.UserInfo;
import com.learn.jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserInfoService implements IUserInfoService, UserDetailsService {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private  PasswordEncoder passwordEncoder;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo = userRepository.findByUsername(username);
        return userInfo.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found exception : " + username));
    }

    @Override
    public UserInfo addUserInfo(UserInfo userInfo) {
        if (Objects.nonNull(userInfo)) {
            userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
            return userRepository.save(userInfo);
        }
        return null;
    }

    @Override
    public List<UserInfo> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserInfo getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found exception : " + id));
    }
}
