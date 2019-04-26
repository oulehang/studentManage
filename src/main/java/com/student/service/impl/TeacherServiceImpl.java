package com.student.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.student.bean.*;
import com.student.dao.*;
import com.student.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2019/3/7.
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private ClazzCourseTeacherMapper clazzCourseTeacherMapper;

    @Autowired
    private GradeMapper gradeMapper;

    @Autowired
    private ClazzMapper clazzMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public DatagridResult teacherList(int page, int rows) {
        PageHelper.startPage(page,rows);
        List<Teacher> teacherList=teacherMapper.teacherList();
        for (Teacher teacher:teacherList) {
            List<CourseItem> item=new ArrayList<CourseItem>();
            int teacherId = teacher.getId();
            List<ClazzCourseTeacher> cct=clazzCourseTeacherMapper.selectByTeacherId(teacherId);
            for (ClazzCourseTeacher list:cct ) {
                CourseItem courseItem=new CourseItem();
                Grade grade=gradeMapper.selectByPrimaryKey(list.getGradeid());
                Clazz clazz=clazzMapper.selectByPrimaryKey(list.getClazzid());
                Course course=courseMapper.selectByPrimaryKey(list.getCourseid());
                courseItem.setGrade(grade);
                courseItem.setClazz(clazz);
                courseItem.setCourse(course);
                courseItem.setTeacher(teacher);
                item.add(courseItem);
            }
            if(item.size()>0){
                teacher.setCourseList(item);
            }

        }

        //得到分页的结果对象
        PageInfo<Teacher> pageInfo = new PageInfo<Teacher>(teacherList);
        DatagridResult datagridResult=new DatagridResult();
        datagridResult.setTotal(pageInfo.getTotal());
        datagridResult.setRows(teacherList);
        return datagridResult;
    }

    @Override
    public int deleteTeacher(List<String> ids) {
        for (String id:ids) {
            int a=teacherMapper.deleteByPrimaryKey(Integer.parseInt(id));
            if(a<1){
                return 0;
            }
        }
        return 1;
    }

    @Override
    public Boolean addTeacher(Teacher teacher) {
       int a=  teacherMapper.insert(teacher);
        if(a>0){
            int teacherId=teacher.getId();
            for (String course:teacher.getCourse()){
                String[] gcc = course.split("_");
                int gradeid = Integer.parseInt(gcc[0]);
                int clazzid = Integer.parseInt(gcc[1]);
                int courseid = Integer.parseInt(gcc[2]);
                ClazzCourseTeacher cct=new ClazzCourseTeacher();
                cct.setGradeid(gradeid);
                cct.setClazzid(clazzid);
                cct.setCourseid(courseid);
                cct.setTeacherid(teacherId);
                clazzCourseTeacherMapper.insert(cct);
            }
        }else {
            return false;
        }
      return true;
    }

    @Override
    public Teacher getStudentByNumber(String account) {
        return teacherMapper.getTeacherByNumber(account);
    }

    @Override
    public String getExamClazz(String account, Grade grade, Clazz clazz) {
        Teacher teacher= teacherMapper.getTeacherByNumber(account);
        //获取某教师的年纪班级课程
        List<CourseItem> item=new ArrayList<CourseItem>();
        List<ClazzCourseTeacher> cct = clazzCourseTeacherMapper.selectByTeacherId(teacher.getId());
        for (ClazzCourseTeacher list:cct ) {
            CourseItem courseItem=new CourseItem();
            Grade grade1=gradeMapper.selectByPrimaryKey(list.getGradeid());
            Clazz clazz1=clazzMapper.selectByPrimaryKey(list.getClazzid());
            Course course=courseMapper.selectByPrimaryKey(list.getCourseid());
            courseItem.setGrade(grade1);
            courseItem.setClazz(clazz1);
            courseItem.setCourse(course);
            courseItem.setTeacher(teacher);
            item.add(courseItem);
        }
        if(item.size()>0){
            teacher.setCourseList(item);
        }

        List<CourseItem> courseList = teacher.getCourseList();
        if(courseList.size() == 0){
            return null;
        }
        String result = JSONArray.toJSONString(courseList).toString();
        //返回
        return result;
    }

    @Override
    public String getTeacherResult(String number) {
        Teacher teacher = teacherMapper.getTeacherByNumber(number);
        //获取某教师的年纪班级课程
        List<CourseItem> item=new ArrayList<CourseItem>();
        List<ClazzCourseTeacher> cct = clazzCourseTeacherMapper.selectByTeacherId(teacher.getId());
        for (ClazzCourseTeacher list:cct ) {
            CourseItem courseItem=new CourseItem();
            Grade grade1=gradeMapper.selectByPrimaryKey(list.getGradeid());
            Clazz clazz1=clazzMapper.selectByPrimaryKey(list.getClazzid());
            Course course=courseMapper.selectByPrimaryKey(list.getCourseid());
            courseItem.setGrade(grade1);
            courseItem.setClazz(clazz1);
            courseItem.setCourse(course);
            courseItem.setTeacher(teacher);
            item.add(courseItem);
        }
        if(item.size()>0){
            teacher.setCourseList(item);
        }

        List<CourseItem> courseList = teacher.getCourseList();
        if(courseList.size() == 0){
            return null;
        }
        String result = JSONObject.toJSONString(teacher);
        //返回
        return result;
    }

    @Override
    public String getExamClazz(String account, Grade grade) {
        Teacher teacher = teacherMapper.getTeacherByNumber(account);
        //获取某年级下老师的班级
        List<Clazz> clazzList = new LinkedList<Clazz>();
        List<ClazzCourseTeacher> cct = clazzCourseTeacherMapper.selectByTeacherId(teacher.getId());
        for (ClazzCourseTeacher list:cct ) {
            boolean flag = true;
            for (Clazz clazz:clazzList) {
                if(list.getClazzid()==clazz.getId()){
                    flag=false;
                    break;
                }
            }
            if(flag){
                Clazz clazz1=clazzMapper.selectByPrimaryKey(list.getClazzid());
                clazzList.add(clazz1);
            }
        }

        String result = JSONArray.toJSONString(clazzList);

        return result;
    }


}
