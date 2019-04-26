package com.student.dao;

import com.student.bean.GradeCourse;

import java.util.List;

public interface GradeCourseMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(GradeCourse record);


    GradeCourse selectByPrimaryKey(Integer id);


    int updateByPrimaryKey(GradeCourse record);

    List<GradeCourse> selectByGradeId(int gradeid);
}