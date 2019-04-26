package com.student.service;

import com.student.bean.System;
import com.student.bean.User;

/**
 * Created by Administrator on 2019/3/7.
 */
public interface SystemService {
    System editSystemInfo(String id, String name, String value);

    System getAll();

    int editPassword(User user);
}
