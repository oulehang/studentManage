package com.student.service;

import com.student.bean.User;

import java.util.List;

/**
 * Created by Administrator on 2019/2/28.
 */
public interface UserService {
    User getById(int id);

    List<User> selectAll();

    User getAdmin(User user);

    int updateUserByAccount(User user);

    void addUser(User user);
}
