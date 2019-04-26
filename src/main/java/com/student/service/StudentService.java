package com.student.service;

import com.student.bean.DatagridResult;
import com.student.bean.Student;

import java.util.List;

/**
 * Created by Administrator on 2019/3/5.
 */

public interface StudentService {
    DatagridResult getStudentList(Integer page, Integer rows, Student student);

    int addStudent(Student student);

    int editStudent(Student student);

    int deleteStudent(List<String> ids);


    Student getStudentByNumber(String number);

    int editStudent2(Student student);
}
