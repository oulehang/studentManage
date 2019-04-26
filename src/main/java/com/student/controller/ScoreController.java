package com.student.controller;

import com.student.bean.Exam;
import com.student.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/3/22.
 */
@Controller
@RequestMapping("score")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @ResponseBody
    @RequestMapping("scoreList")
    public List<Map<String, Object>> scoreList(HttpServletRequest request, Exam exam){
        List<Map<String, Object>> result = scoreService.getScoreList(exam);
        return result;
    }

    /**
     * 年级统计获取列名
     * @param request
     * @param exam
     * @param response
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("columnList")
    public List columnList(HttpServletRequest request, Exam exam, HttpServletResponse response) throws IOException {
        //获取数据
        List<Object> result = scoreService.columnList(exam);
        return result;
    }

    /**
     * setScore
     * 登记成绩
     */
    @ResponseBody
    @RequestMapping("setScore")
    public void setScore(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String[] score = request.getParameterValues("score[]");
        scoreService.setScore(score);
        //返回数据
        response.getWriter().write("success");

    }

    /**
     * 导出Excel
     * exportScore
     */
    @ResponseBody
    @RequestMapping("exportScore")
    public void exportScore(HttpServletRequest request,HttpServletResponse response, Exam exam ) throws IOException {
        //获取分页参数
        Enumeration<String> pNames = request.getParameterNames();
        scoreService.exportScore(response, exam);

    }
}
