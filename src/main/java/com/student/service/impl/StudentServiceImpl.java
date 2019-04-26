package com.student.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.student.bean.DatagridResult;
import com.student.bean.Student;
import com.student.bean.User;
import com.student.dao.StudentMapper;
import com.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2019/3/5.
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    /**
     * 获取学生信息
     * 可选条件：年级，班级，学号，姓名关键字
     * @param page
     * @param rows
     * @param student
     * @return
     */
    @Override
    public DatagridResult getStudentList(Integer page, Integer rows, Student student) {
        PageHelper.startPage(page , rows);
        List<Student> studentlist= studentMapper.getStudentList(student);
        //得到分页的结果对象
        PageInfo<Student> pageInfo = new PageInfo<Student>(studentlist);

        DatagridResult result = new DatagridResult();
        result.setTotal(pageInfo.getTotal()); //设置总记录数
        result.setRows(studentlist);  //设置每页展示数据集合
        return result;
    }

    @Override
    public int addStudent(Student student) {
        return studentMapper.addStudent(student);
    }

    @Override
    public int editStudent(Student student) {
        return studentMapper.updateByPrimaryKey(student);
    }

    @Override
    public int deleteStudent(List<String> ids) {
        return studentMapper.deleteStudents(ids);
    }


    @Override
    public Student getStudentByNumber(String nummber) {
        Student student = studentMapper.getStudentByNumber(nummber);
        return student;
    }

    @Override
    public int editStudent2(Student student) {
        return  studentMapper.updateByNumber(student);
    }


}
