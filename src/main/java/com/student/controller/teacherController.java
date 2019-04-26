package com.student.controller;

import com.alibaba.fastjson.JSONObject;
import com.student.bean.*;
import com.student.service.ClazzService;
import com.student.service.TeacherService;
import com.student.service.UserService;
import com.student.util.StringTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2019/3/7.
 */
@Controller
@RequestMapping("/teacher")
public class teacherController {

    @Autowired
    private ClazzService clazzService;

    @Autowired
    private  TeacherService teacherService;
    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("teacherList")
    public String  teacherList(HttpServletRequest request,HttpServletResponse response) throws IOException {
        //获取分页参数
        int page = Integer.parseInt(request.getParameter("page"));
        int rows = Integer.parseInt(request.getParameter("rows"));
        Teacher teacher=new Teacher();
        DatagridResult result=teacherService.teacherList(page,rows);
        String result1 = JSONObject.toJSONString(result);
        return result1;
    }

    /**
     * 删除教师
     * @param ids
     * @param response
     * @throws IOException
     */
    @RequestMapping("/deleteTeacher")
    public void deleteTeacher(@RequestParam("ids[]")List<String> ids, HttpServletResponse response) throws IOException {
        int a= teacherService.deleteTeacher(ids);
        if(a<1){
            response.getWriter().write("false");
        }
        response.getWriter().write("success");

    }

    /**
     * 添加教师
     * @param course
     * @param number
     * @param name
     * @param sex
     * @param phone
     * @param qq
     * @param response
     * @param request
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("addTeacher")
    public void addTeacher(@RequestParam("course[]") List<String> course,String number, String name,String sex, String phone,
                           String qq, HttpServletResponse response, HttpServletRequest request) throws IOException {

        String msg="";
        Teacher teacher=new Teacher();
        teacher.setNumber(number);
        teacher.setName(name);
        teacher.setCourse(course);
        teacher.setPhone(phone);
        teacher.setSex(sex);
        teacher.setQq(qq);
        boolean flag= teacherService.addTeacher(teacher);
        if(!flag){
            response.getWriter().write("false");
        }

        User user=new User();
        user.setType(3);//1管理员 2学生 3教师
        user.setName(teacher.getName());
        user.setAccount(teacher.getNumber());
        user.setPassword("123456");
        userService.addUser(user);

        response.getWriter().write("success");
    }

    /**
     * 获取某个教师信息
     * getTeacher
     */
    @ResponseBody
    @RequestMapping("getTeacher")
    public String getTeacher(HttpServletRequest request){
        //获取当前用户
        User user = (User) request.getSession().getAttribute("user");
        String number = user.getAccount();
        String result = teacherService.getTeacherResult(number);
       return result;
    }







    /**
     * 获取某次考试老师的课程
     * getExamCourse
     * @return
     */
    @ResponseBody
    @RequestMapping("getExamCourse")
    public void getExamCourse(HttpServletRequest request,HttpServletResponse response) throws IOException {
        int gradeid = Integer.parseInt(request.getParameter("gradeid"));
        Grade grade = new Grade();
        grade.setId(gradeid);
        String scid = request.getParameter("clazzid");
        if(StringTool.isEmpty(scid)){
            response.getWriter().write("");
            return;
        }
        int clazzid = Integer.parseInt(scid);
        Clazz clazz = new Clazz();
        clazz.setId(clazzid);

        User user = (User) request.getSession().getAttribute("user");

        String result = teacherService.getExamClazz(user.getAccount(), grade, clazz);

        response.getWriter().write(result);
    }

    /**
     * scoreList
    * 获取某次考试老师的班级
     * @return
     */
    @ResponseBody
    @RequestMapping("getExamClazz")
    public String getExamClazz(HttpServletRequest request,Exam exam){
        int gradeid = exam.getGradeid();
        Grade grade = new Grade();
        grade.setId(gradeid);
        User user = (User) request.getSession().getAttribute("user");
        String result = teacherService.getExamClazz(user.getAccount(), grade);
        return result;
    }

    @RequestMapping("teacherListView")
    public String teacherListView(){
        return "/teacher/teacherList";
    }

    @RequestMapping("toExamTeacherView")
    public String toExamTeacherView(){
        return "/teacher/examTeacherList";
    }

    @RequestMapping("toTeacherNoteListView")
    public String toTeacherNoteListView(){
        return "/teacher/teacherNoteList";
    }

    @RequestMapping("toTeacherPersonalView")
    public String toTeacherPersonalView(HttpServletRequest request){
        //转发页面到学生个人信息页面时，将学生信息加入session
        User user = (User) request.getSession().getAttribute("user");
        Teacher teacher = teacherService.getStudentByNumber(user.getAccount());

        request.getSession().setAttribute("userDetail", teacher);
        return "/teacher/teacherPersonal";
    }
}
