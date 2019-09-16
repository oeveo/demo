package com.springboot.demo.service;

import com.springboot.demo.entity.User;

public interface UserService {
    void insert(User account);

    void activeUser(String email);

    User login(String email);

    void updateUser(User user);
}
