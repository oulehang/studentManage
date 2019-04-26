package com.student.dao;

import com.student.bean.Teacher;

import java.util.List;

public interface TeacherMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Teacher record);


    Teacher selectByPrimaryKey(Integer id);


    int updateByPrimaryKey(Teacher record);

    List<Teacher> teacherList();

    int addTeacher(Teacher teacher);

    Teacher getTeacherByNumber(String account);
}