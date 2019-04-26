package com.student.dao;

import com.student.bean.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2019/3/5.
 */
public interface StudentMapper {

    long getStudentCount(Student student);

    List<Student> getStudentList(Student student);

    int addStudent(Student student);

    int updateByPrimaryKey(Student student);

    int deleteStudents(@Param("ids") List<String> ids);

    Student getStudentByNumber(String account);

    int updateByNumber(Student student);
}
