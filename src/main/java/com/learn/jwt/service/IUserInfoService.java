package com.learn.jwt.service;

import com.learn.jwt.entity.UserInfo;

import java.util.List;

public interface IUserInfoService {

    UserInfo addUserInfo(UserInfo userInfo);
    List<UserInfo> getAllUsers();
    UserInfo getUserById(Integer id);

}
