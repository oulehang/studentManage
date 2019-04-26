package com.student.service;

import com.student.bean.Course;
import com.student.bean.DatagridResult;

import java.util.List;

/**
 * Created by Administrator on 2019/3/7.
 */
public interface CourseService {
    DatagridResult courseList(int page, int rows);

    List<Course> getCourseByGradeId(int i);

    List<Course> courseListAll();

    int addCourse(String name);
}
