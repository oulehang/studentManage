package com.student.dao;

import com.student.bean.Exam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExamMapper {

   int  deleteByPrimaryKey(int id);
    int insert(Exam record);

    int insertSelective(Exam record);

    int updateByPrimaryKey(Exam record);

    List<Exam> examListByPage(@Param("gradeid") String gradeid,@Param("clazzid") String clazzid);

    List<Exam> getTeacherExamList(@Param("gradeId")List<Integer> gradeId,@Param("courseId") List<Integer> courseId);
}