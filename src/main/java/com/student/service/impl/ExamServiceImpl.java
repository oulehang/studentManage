package com.student.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.student.bean.*;
import com.student.dao.*;
import com.student.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2019/3/14.
 */
@Service
public class ExamServiceImpl implements ExamService {
    @Autowired
    private ExamMapper examMapper;

    @Autowired
    private GradeMapper gradeMapper;
    @Autowired
    private ClazzMapper clazzMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private GradeCourseMapper gradeCourseMapper;
    @Autowired
    private EscoreMapper escoreMapper;
    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private ClazzCourseTeacherMapper clazzCourseTeacherMapper;

    @Override
    public DatagridResult examListByPage(int page, int rows, String gradeid, String clazzid) {
        PageHelper.startPage(page,rows);
        List<Exam> examList=examMapper.examListByPage(gradeid,clazzid);
        for (Exam exam: examList) {
            Grade grade= gradeMapper.selectByPrimaryKey(exam.getGradeid());
            exam.setGrade(grade);
            Course course = courseMapper.selectByPrimaryKey(exam.getCourseid());
            exam.setCourse(course);
            Clazz clazz = clazzMapper.selectByPrimaryKey(exam.getClazzid());
            exam.setClazz(clazz);
        }

        PageInfo<Exam> pageInfo=new PageInfo<Exam>(examList);
        DatagridResult result = new DatagridResult();
        result.setTotal(pageInfo.getTotal()); //设置总记录数
        result.setRows(examList);  //设置每页展示数据集合

        return result;
    }

    @Override
    public int deleteExamById(int id) {
        return examMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int addExam(Exam exam) {
        try {
            int insert = examMapper.insert(exam);

            //插入考试表之后需要添加到考试成绩表
            if(insert>0){
                EScore eScore=new EScore();
                eScore.setExamid(exam.getId());
                eScore.setGradeid(exam.getGradeid());

                if(exam.getType()==Exam.EXAM_GRADE_TYPE){ //年级统考
                    Student student=new Student();
                    student.setGradeid(exam.getGradeid());
                    List<GradeCourse> gradeCourseList=gradeCourseMapper.selectByGradeId(exam.getGradeid());
                    for (GradeCourse gc:gradeCourseList) {
                        eScore.setCourseid(gc.getCourseid());//设置该年级下的课程
                        List<Student> studentList = studentMapper.getStudentList(student);
                        for (Student stu:studentList) {
                            eScore.setStudentid(stu.getId());//设置该年级下的所有学生
                            eScore.setClazzid(stu.getClazzid());
                            int i = escoreMapper.insert(eScore);
                        }
                    }
                }else if(exam.getType()==Exam.EXAM_NORMAL_TYPE){ //平时考试
                    Student student=new Student();
                    student.setClazzid(exam.getClazzid());
                    List<ClazzCourseTeacher> clazzCourseTeachers=clazzCourseTeacherMapper.selectByClazzId(exam.getClazzid());
                    for (ClazzCourseTeacher gc:clazzCourseTeachers) {
                        eScore.setCourseid(gc.getCourseid());
                        List<Student> studentList=studentMapper.getStudentList(student);
                        for (Student stu:studentList) {
                            eScore.setStudentid(stu.getId());//设置该年级下的所有学生
                            int i = escoreMapper.insert(eScore);
                        }
                    }

                }
            }
        }catch (Exception e) {
            return 0;
        }
        return 1;
    }

    @Override
    public List<Exam> studentExamList(String account) {
       Student student= studentMapper.getStudentByNumber(account);
        List<Exam> examList=examMapper.examListByPage(String.valueOf(student.getGradeid()),String.valueOf(student.getClazzid()));
        for (Exam exam: examList) {
            Grade grade= gradeMapper.selectByPrimaryKey(exam.getGradeid());
            exam.setGrade(grade);
            Course course = courseMapper.selectByPrimaryKey(exam.getCourseid());
            exam.setCourse(course);
            Clazz clazz = clazzMapper.selectByPrimaryKey(exam.getClazzid());
            exam.setClazz(clazz);
        }
        return examList;
    }

    @Override
    public List<Exam> teacherExamList(String account) {

        Teacher teacher= teacherMapper.getTeacherByNumber(account);
        //获取某教师的年纪班级课程
        List<CourseItem> item=new ArrayList<CourseItem>();
        List<ClazzCourseTeacher> cct = clazzCourseTeacherMapper.selectByTeacherId(teacher.getId());
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

        List<CourseItem> courseList = teacher.getCourseList();
        if(courseList.size() == 0){
            return null;
        }
        //年级id和班级id存入list
        List<Integer> gradeId=new LinkedList<Integer>();
        List<Integer> courseId=new ArrayList<Integer>();
        for(CourseItem list : courseList){
            gradeId.add(list.getGrade().getId());
            courseId.add(list.getCourse().getId());
        }
        List<Exam> examList= examMapper.getTeacherExamList(gradeId,courseId);
        for (Exam exam: examList) {
            Grade grade= gradeMapper.selectByPrimaryKey(exam.getGradeid());
            exam.setGrade(grade);
            Course course = courseMapper.selectByPrimaryKey(exam.getCourseid());
            exam.setCourse(course);
            Clazz clazz = clazzMapper.selectByPrimaryKey(exam.getClazzid());
            exam.setClazz(clazz);
        }
        return examList;
    }
}
