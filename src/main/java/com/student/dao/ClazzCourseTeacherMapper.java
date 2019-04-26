package com.student.dao;

import com.student.bean.ClazzCourseTeacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClazzCourseTeacherMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ClazzCourseTeacher record);

    ClazzCourseTeacher selectByPrimaryKey(Integer id);


    int updateByPrimaryKey(ClazzCourseTeacher record);

    List<ClazzCourseTeacher> selectByTeacherId(@Param("teacherid") int teacherid);

    List<ClazzCourseTeacher> selectByClazzId(int clazzid);
}