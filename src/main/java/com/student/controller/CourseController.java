package com.student.controller;

import com.alibaba.fastjson.JSONObject;
import com.student.bean.Course;
import com.student.bean.DatagridResult;
import com.student.service.CourseService;
import com.student.util.StringTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2019/3/7.
 */
@Controller
@RequestMapping("/course")
public class CourseController {
    private final int SUCCESS=1;//成功
    private final int FAILURE=2;//失败
    private final int EXIST=3;//失败，已存在
    @Autowired
   private CourseService courseService;

    /**
     * 分页获取年级信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("courseList")
    public DatagridResult courseList(HttpServletRequest request){
        //获取分页参数
        int page = Integer.parseInt(request.getParameter("page"));
        int rows = Integer.parseInt(request.getParameter("rows"));

        DatagridResult result = courseService.courseList(page,rows);
        //返回数据
        return result;
    }

    /**
     * 获取某一年纪下的课程
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("courseList2")
    public String courseList2(HttpServletRequest request){
    //获取年级下的课程
        String gradeId=request.getParameter("gradeid");
        if(StringTool.isEmpty(gradeId)){
            return "";
        }
        List<Course> courseList= courseService.getCourseByGradeId(Integer.parseInt(gradeId));
        String result= JSONObject.toJSONString(courseList);
        return result;
    }

    /**
     * 获取全部课程
     * @return
     */
    @ResponseBody
    @RequestMapping("courseListAll")
    public String courseListAll(HttpServletRequest request){
        List<Course> list = courseService.courseListAll();
        String result=JSONObject.toJSONString(list);
        //返回数据
        return result;
    }
    @ResponseBody
    @RequestMapping("addCourse")
    public String addCourse(String name){
        String msg="";
        int a= courseService.addCourse(name);
        if(a==SUCCESS){
            msg="success";//成功
        }else if (a==FAILURE){
            msg="failure";//失败
        }else if(a==EXIST){
            msg="exist";//班级已存在
        }
        return msg;
    }
    @RequestMapping("courseListView")
    public String courseListView(){
        return "/other/courseList";
    }
}
