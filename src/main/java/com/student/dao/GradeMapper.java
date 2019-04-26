package com.student.dao;

import com.student.bean.Grade;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GradeMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(Grade record);


    int updateByPrimaryKeySelective(Grade record);

    int updateByPrimaryKey(Grade record);

    List<Grade> getAllGrade();

    Grade selectByPrimaryKey(int id);

    List<Object> getGradeCourse(int gradeId);

    List<Object> getCourse(int courseid);

    Grade selectByName(@Param("name") String name);
}