package com.student.service;

import com.student.bean.Exam;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/3/22.
 */
public interface ScoreService {
    List columnList(Exam exam);

    List<Map<String, Object>> getScoreList(Exam exam);

    void setScore(String[] score);

    void exportScore(HttpServletResponse response, Exam exam);
}
