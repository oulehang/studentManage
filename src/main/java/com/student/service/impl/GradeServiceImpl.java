package com.student.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.student.bean.*;
import com.student.dao.ClazzMapper;
import com.student.dao.CourseMapper;
import com.student.dao.GradeCourseMapper;
import com.student.dao.GradeMapper;
import com.student.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/3/5.
 */
@Service
public class GradeServiceImpl implements GradeService {
    private final int SUCCESS=1;//成功
    private final int FAILURE=2;//失败
    private final int EXIST=3;//失败，已存在

    @Autowired
    private GradeMapper gradeMapper;

    @Autowired
    private ClazzMapper clazzMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private GradeCourseMapper gradeCourseMapper;
    @Override
    public List<Grade> gradeList() {
        List<Grade> gradeList=gradeMapper.getAllGrade();
        for (Grade grade: gradeList) {
            List<Clazz> clazzList=clazzMapper.selectByGradeId(grade.getId());
            grade.setClazzList(clazzList);
        }
        return gradeList;
    }

    @Override
    public DatagridResult gradeListByPage(int page, int rows) {
        PageHelper.startPage(page,rows);
        List<Grade> gradeList=gradeMapper.getAllGrade();
        for (Grade grade: gradeList) {
            List<Course> courseList=courseMapper.selectByGradeId(grade.getId());
            grade.setCourseList(courseList);
        }
        PageInfo<Grade> pageInfo= new PageInfo<Grade>(gradeList);
        DatagridResult result = new DatagridResult();
        result.setTotal(pageInfo.getTotal()); //设置总记录数
        result.setRows(gradeList);  //设置每页展示数据集合
        return result;
    }

    @Override
    public int addGrade(String name, List<String> courseID) {
        Grade grade1= gradeMapper.selectByName(name);
        if(grade1!=null){
            return EXIST;
        }
        Grade grade = new Grade();
        grade.setName(name);
        int i = gradeMapper.insert(grade);
        if(i>0){
            Grade grade2 = gradeMapper.selectByName(name);
            for (String courseid:courseID) {
                GradeCourse gradeCourse = new GradeCourse();
                gradeCourse.setCourseid(Integer.parseInt(courseid));
                gradeCourse.setGradeid(grade2.getId());
                gradeCourseMapper.insert(gradeCourse);
            }
        }else {
            return FAILURE;
        }
        return SUCCESS;
    }

    @Override
    public Grade selectById(int gradeid1) {
        return gradeMapper.selectByPrimaryKey(gradeid1);
    }
}
