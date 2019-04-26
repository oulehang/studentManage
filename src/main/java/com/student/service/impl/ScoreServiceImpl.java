package com.student.service.impl;

import com.student.bean.Course;
import com.student.bean.EScore;
import com.student.bean.Exam;
import com.student.bean.Student;
import com.student.dao.EscoreMapper;
import com.student.dao.GradeMapper;
import com.student.dao.StudentMapper;
import com.student.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by Administrator on 2019/3/22.
 */
@Service
public class ScoreServiceImpl implements ScoreService{
    @Autowired
    private EscoreMapper escoreMapper;

    @Autowired
    private GradeMapper gradeMapper;

    @Autowired
    private StudentMapper studentMapper;
    @Override
    public List<Object> columnList(Exam exam) {
        List<Object> list = getColumn(exam);
        return list;
    }

    @Override
    public List<Map<String, Object>> getScoreList(Exam exam) {
        //数据集合
        List<Map<String, Object>> Datalist = new LinkedList<Map<String, Object>>();

        Student student = new Student();
        student.setGradeid(exam.getGradeid());
        student.setClazzid(exam.getClazzid());
        List<Student> studentList = studentMapper.getStudentList(student);
        for (Student stu:studentList) {
            LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
            map.put("name",stu.getName());
            map.put("number",stu.getNumber());
            int totalScore=0;
            List<EScore> list=escoreMapper.getScoreById(exam.getId(),stu.getId());
            for (EScore score: list) {
                totalScore += score.getScore();
                map.put("course"+score.getCourseid(), score.getScore());
                map.put("escoreid"+score.getCourseid(), score.getId());

            }
            if(exam.getType() == 1){
                map.put("total", totalScore);
            }
            Datalist.add(map);
        }
        return Datalist;
    }

    @Override
    public void setScore(String[] score) {
        List<String> scoreList= Arrays.asList(score);
        for (String s:scoreList) {
            String[] id_score = s.split("_");
            String id=id_score[0];
            String score1=id_score[1];
            escoreMapper.updateScoreById(id,score1);
        }
    }

    /**
     * 导出Excel表
     * @param response
     * @param exam
     */
    @Override
    public void exportScore(HttpServletResponse response, Exam exam) {
        //获取需要导出的数据
        List<Map<String, Object>> list = escoreMapper.getScoreList(exam);
        //获取考试信息
        Exam em =new Exam();
        //设置文件名
        String fileName = em.getName()+".xls";
        //定义输出类型
        response.setContentType("application/msexcel;charset=utf-8");
        //设定输出文件头
        try {
            response.setHeader("Content-Disposition", "attachment; filename="+ URLEncoder.encode(fileName, "UTF-8"));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        //获取导出的课程
        List<Object> courseList = getColumn(exam);

        //表头长度
        int len = 2 + courseList.size();
        if(exam.getType() == Exam.EXAM_GRADE_TYPE){
            len += 1;
        }
        //设置excel的列名
        String[] headers = new String[len];
        headers[0] = "姓名";
        headers[1] = "学号";

        int index = 2;
        for(Object obj : courseList){
            Course course = (Course) obj;
            headers[index++] = course.getName();
        }

        if(exam.getType() == Exam.EXAM_GRADE_TYPE){
            headers[len-1] = "总分";
        }

    }

    private List<Object> getColumn(Exam exam) {
        List<Object> list = null;
        if(exam.getType() == Exam.EXAM_GRADE_TYPE){ //年级考试
              list= gradeMapper.getGradeCourse(exam.getGradeid());
        } else{
            list=gradeMapper.getCourse(exam.getCourseid());
        }
        return list;
    }
}
