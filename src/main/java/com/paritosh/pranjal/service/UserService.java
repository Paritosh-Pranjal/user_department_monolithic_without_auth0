package com.paritosh.pranjal.service;

import com.paritosh.pranjal.entity.User;

import java.util.List;

public interface UserService {
    List<User> getUser();

    User saveUser (User user);

    User getSingleUser(Long id);

    void deleteUser(Long id);

    User updateUser(User user);
}
