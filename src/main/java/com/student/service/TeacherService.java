package com.student.service;

import com.student.bean.Clazz;
import com.student.bean.DatagridResult;
import com.student.bean.Grade;
import com.student.bean.Teacher;

import java.util.List;

/**
 * Created by Administrator on 2019/3/7.
 */
public interface TeacherService {
    DatagridResult teacherList(int page, int rows);

    int deleteTeacher(List<String> ids);

    Boolean addTeacher(Teacher teacher);

    Teacher getStudentByNumber(String account);

    String getExamClazz(String account, Grade grade, Clazz clazz);

    String getTeacherResult(String number);

    String getExamClazz(String account, Grade grade);
}
