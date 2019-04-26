package com.student.dao;

import com.student.bean.User;

import java.util.List;


public interface UserMapper {

    User getById(int id);

    List<User> selectAll();

    User getAdmin(User user);

    int editPassword(User user);

    int updateUserByAccount(User user);

    void insert(User user);
}