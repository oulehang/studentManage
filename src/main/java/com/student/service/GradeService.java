package com.student.service;

import com.student.bean.DatagridResult;
import com.student.bean.Grade;

import java.util.List;

/**
 * Created by Administrator on 2019/3/5.
 */
public interface GradeService {
    List<Grade> gradeList();

    DatagridResult gradeListByPage(int page, int rows);

    int addGrade(String name, List<String> clazzid);

    Grade selectById(int gradeid1);
}
