package com.bfarming.service;


import com.bfarming.model.Category;
import com.bfarming.model.User;
import com.bfarming.model.UserAddress;

public interface UserService {

    User findUserByEmail(String email);

    User findUserByMobile(String mobile);

    User findUserPassword(String password);

    void save(User user);

    void updateUser(User user);

    User findUserById(Long userId);

    void save(UserAddress address);
}
