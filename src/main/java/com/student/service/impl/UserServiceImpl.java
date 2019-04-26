package com.student.service.impl;

import com.student.bean.User;
import com.student.dao.UserMapper;
import com.student.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2019/2/28.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getById(int id) {
        return userMapper.getById(id);
    }

    @Override
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public User getAdmin(User user) {
        return userMapper.getAdmin(user);
    }

    @Override
    public int updateUserByAccount(User user) {
        return userMapper.updateUserByAccount(user);
    }

    @Override
    public void addUser(User user) {
        userMapper.insert(user);
    }
}
