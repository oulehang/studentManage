package com.student.service;

import com.student.bean.DatagridResult;
import com.student.bean.Exam;

import java.util.List;

/**
 * Created by Administrator on 2019/3/14.
 */
public interface ExamService {

    DatagridResult examListByPage(int page, int rows, String gradeid, String clazzid);

    int deleteExamById(int id);

    int addExam(Exam exam);

    List<Exam> studentExamList(String account);

    List<Exam> teacherExamList(String account);
}
