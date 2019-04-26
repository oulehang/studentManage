package com.student.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.student.bean.Clazz;
import com.student.bean.Course;
import com.student.bean.DatagridResult;
import com.student.dao.CourseMapper;
import com.student.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2019/3/7.
 */
@Service
public class CourseServiceImpl implements CourseService {
    private final int SUCCESS=1;//成功
    private final int FAILURE=2;//失败
    private final int EXIST=3;//失败，已存在
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public DatagridResult courseList(int page, int rows) {
        PageHelper.startPage(page,rows);
        List<Course> courseList=courseMapper.selectAll();
        PageInfo<Course> pageInfo=new PageInfo<Course>(courseList);
        DatagridResult result=new DatagridResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(courseList);
        return result;
    }

    @Override
    public List<Course> getCourseByGradeId(int i) {
        List<Course> courseList = courseMapper.selectByGradeId(i);
        return courseList;
    }

    @Override
    public List<Course> courseListAll() {
        List<Course> courseList=courseMapper.selectAll();
        return courseList;
    }

    @Override
    public int addCourse(String name) {
        Course course=courseMapper.selectByName(name);
        if(course!=null){
            return EXIST;
        }
        Course course1=new Course();
        course1.setName(name);
        int i = courseMapper.insert(course1);
        if(i>0){
            return SUCCESS;
        }else {
            return FAILURE;
        }
}
}
