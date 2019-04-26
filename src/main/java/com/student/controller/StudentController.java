package com.student.controller;

import com.student.bean.*;
import com.student.service.ClazzService;
import com.student.service.GradeService;
import com.student.service.StudentService;
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
 * Created by Administrator on 2019/3/5.
 */
@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private GradeService gradeService;

    @Autowired
    private ClazzService clazzService;
    @Autowired
    private UserService userService;
    /**
     * 获取学生信息
     * 可选条件：年级，班级
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("getstudentList")
    private DatagridResult studentList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //年级ID
        String gradeid = request.getParameter("gradeid");
        //班级ID
        String clazzid = request.getParameter("clazzid");
        //获取分页参数
        int page = Integer.parseInt(request.getParameter("page"));
        int rows = Integer.parseInt(request.getParameter("rows"));

        //封装参数
        Student student = new Student();

        if(!StringTool.isEmpty(gradeid)){
            student.setGradeid(Integer.parseInt(gradeid));
        }
        if(!StringTool.isEmpty(clazzid)){
            student.setClazzid(Integer.parseInt(clazzid));
        }
        //获取数据
        DatagridResult result = studentService.getStudentList(page,rows,student);
        //返回数据
        return result;
    }

    /**
     *  * 获取学生信息
     * 可选条件：学号，姓名关键字
     * @param page
     * @param rows
     * @param number
     * @param name
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("getStudentByName")
    public DatagridResult getStudentByName(int page,int rows,String number,String name,HttpServletRequest request){
        //获取数据
        Student student=new Student();
        student.setName(name);
        student.setNumber(number);
        DatagridResult result =studentService.getStudentList(page,rows,student);
        return result;
    }

    /**
     * 添加学生
     * @param student
     * @param response
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("addStudent")
    public void addStudent(Student student,HttpServletResponse response) throws IOException {
        String msg="";
        int a= studentService.addStudent(student);
        if(a<0){
            response.getWriter().write("false");
        }
        User user=new User();
        user.setType(2);//1管理员 2学生 3教师
        user.setName(student.getName());
        user.setAccount(student.getNumber());
        user.setPassword("123456");
        userService.addUser(user);
        response.getWriter().write("success");
    }

    /**
     * 管理员页面修改学生
     * @param student
     * @param response
     * @throws IOException
     */
    @RequestMapping("editStudent")
    public void editStudent(Student student,HttpServletResponse response) throws IOException {
        int a= studentService.editStudent(student);
        if(a<1){
            response.getWriter().write("false");
        }
        response.getWriter().write("success");
    }

    /**
     * 删除学生
     * @param ids
     * @param numbers
     * @param response
     */
    @RequestMapping("deleteStudent")
    public void deleteStudent(@RequestParam("ids[]")List<String> ids, String[] numbers, HttpServletResponse response) throws IOException {
        int a= studentService.deleteStudent(ids);
        if(a<1){
            response.getWriter().write("false");
        }
        response.getWriter().write("success");
    }

    /**
     * 分页获取一个班级的学生
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("studentClazzList")
    public DatagridResult studentClazzList(HttpServletRequest request){
        User user =(User) request.getSession().getAttribute("user");
        String number=user.getAccount();
        //获取分页参数
        int page = Integer.parseInt(request.getParameter("page"));
        int rows = Integer.parseInt(request.getParameter("rows"));

        Student student=studentService.getStudentByNumber(number);
        int clazzid = student.getClazzid();
        int gradeid = student.getGradeid();
        Student student1=new Student();
        student1.setClazzid(clazzid);
        student1.setGradeid(gradeid);
        DatagridResult result =studentService.getStudentList(page,rows,student1);
        return result;
    }

    /**
     * 学生页面修改学生信息
     * 同时也要修改用户信息
     * @param request
     * @param response
     * @param student
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("editStudent2")
    public void editStudent2(HttpServletRequest request,HttpServletResponse response,Student student) throws IOException {
        int i = studentService.editStudent2(student);
        if(i>0){
            String number = student.getNumber();
            String name = student.getName();
            User user=new User();
            user.setAccount(number);
            user.setName(name);
            int a= userService.updateUserByAccount(user);
            User user1 = (User) request.getSession().getAttribute("user");
            user1.setName(name);
            request.getSession().setAttribute("user",user1);
            if(a>0){
                response.getWriter().write("success");
            }

        }
    }


    /**
     * 跳转到页面
     * @return
     */
    @RequestMapping("studentListView")
    public String studentView(){
        return "student/studentList";
    }

    @RequestMapping("toExamStudentView")
    public String toExamStudentView(){
        return "student/examStudentList";
    }

    @RequestMapping("toStudentNoteListView")
    public String toStudentNoteListView(){
        return "student/studentNoteList";
    }

    @RequestMapping("toStudentPersonalView")
    public String toStudentPersonalView(HttpServletRequest request){
        //转发页面到学生个人信息页面时，将学生信息加入session
        User user = (User) request.getSession().getAttribute("user");
        Student student = studentService.getStudentByNumber(user.getAccount());
        Clazz clazz = clazzService.getClazzById(student.getClazzid());
        student.setClazzName(clazz.getName());
        Grade grade = gradeService.selectById(student.getGradeid());
        student.setGradeName(grade.getName());
        request.getSession().setAttribute("userDetail", student);
        return "student/studentPersonal";
    }

}
