package com.student.service.impl;

import com.student.bean.System;
import com.student.bean.SystemInfo;
import com.student.bean.User;
import com.student.dao.SystemMapper;
import com.student.dao.UserMapper;
import com.student.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2019/3/7.
 */
@Service
public class SystemServiceImpl implements SystemService {
    @Autowired
    private SystemMapper systemMapper;

    @Autowired
    private UserMapper userMapper;
    @Override
    public System editSystemInfo(String id, String name, String value) {
        int i = systemMapper.editSystem(id,name,value);

        System system=systemMapper.selectByPrimaryKey(Integer.parseInt(id));
        return system;
    }

    @Override
    public System getAll() {
        System system=systemMapper.selectAll();
        return system;
    }

    @Override
    public int editPassword(User user) {
       return userMapper.editPassword(user);
    }
}
