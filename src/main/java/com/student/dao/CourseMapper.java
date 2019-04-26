package com.student.dao;

import com.student.bean.Course;
import com.student.bean.CourseItem;

import java.util.List;

public interface CourseMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Course record);


    Course selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Course record);

    List<Course> selectByGradeId(int gradeId);

    List<Course> selectAll();

    Course selectByName(String name);

    List<CourseItem> getCourseListByTeacherId(int id);
}