package com.student.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.student.bean.DatagridResult;
import com.student.bean.Exam;
import com.student.bean.User;
import com.student.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Administrator on 2019/3/7.
 */
@Controller
@RequestMapping("/exam")
public class ExamController {

    @Autowired
    private ExamService examService;

    /**
     * 管理员添加年级考试
     * @param request
     * @param exam
     * @return
     */
    @ResponseBody
    @RequestMapping("addExam")
    public String  addExam(HttpServletRequest request, Exam exam){
        String msg="";
        int a= examService.addExam(exam);
        if(a>0){
            msg="success";
        }
        return msg;
    }


    /**
     * 删除考试列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("deleteExam")
    public String deleteExam(HttpServletRequest request){
        String msg=null;
        int id=Integer.parseInt(request.getParameter("id"));
       int a= examService.deleteExamById(id);
        if(a>0){
            msg="success";
        }
        return msg;
    }


    /**
     * 根据年级或班级获取考试信息
     * @param request
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("gradeListByPage")
    public DatagridResult examList(HttpServletRequest request) throws IOException {
        int page = Integer.parseInt(request.getParameter("page"));
        int rows = Integer.parseInt(request.getParameter("rows"));
        //年级ID
        String gradeid = request.getParameter("gradeid");
        //班级ID
        String clazzid = request.getParameter("clazzid");
        DatagridResult result=examService.examListByPage(page,rows,gradeid,clazzid);
        return result;
    }

    /**
     * 获取某个学生的考试列表
     */
    @ResponseBody
    @RequestMapping("studentExamList")
    public String  studentExamList(HttpServletRequest request){
        User user =(User) request.getSession().getAttribute("user");
        String account = user.getAccount();
       List<Exam> list= examService.studentExamList(account);
        String result = JSONObject.toJSONString(list);
        return result;
    }

    /**
     * 获取教师的考试列表
     * @return
     */
    @ResponseBody
    @RequestMapping("teacherExamList")
    public String teacherExamList(HttpServletRequest request){
        User user =(User) request.getSession().getAttribute("user");
        String account = user.getAccount();
        List<Exam> list= examService.teacherExamList(account);
        String result = JSONObject.toJSONString(list);
        return result;
    }

    @RequestMapping("examListView")
    public String examListView(){
        return "/other/examList";
    }

    public JSONObject getJsonObject(HttpServletRequest request) throws IOException {
        InputStream inStream = request.getInputStream();
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        String result = new String(outSteam.toByteArray(), "utf-8");
//            result = ToUTF8.toUTF8(result);
        JSONObject object = JSON.parseObject(result);
        return object;
    }

}
