package com.student.controller;

import com.alibaba.fastjson.JSONArray;
import com.student.bean.DatagridResult;
import com.student.bean.Grade;
import com.student.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2019/3/5.
 */
@Controller
@RequestMapping("/Grade")
public class GradeController {
    private final int SUCCESS=1;//成功
    private final int FAILURE=2;//失败
    private final int EXIST=3;//失败，已存在
    @Autowired
    private GradeService gradeService;

    @ResponseBody
    @RequestMapping("gradeList")
    public String gerdaList(){
        List<Grade> gradeList= gradeService.gradeList();
        String result = JSONArray.toJSONString(gradeList);
        return result;
    }
    @ResponseBody
    @RequestMapping("gradeListByPage")
    public DatagridResult gradeListByPage(HttpServletRequest request){
        //获取分页参数
        int page = Integer.parseInt(request.getParameter("page"));
        int rows = Integer.parseInt(request.getParameter("rows"));
        DatagridResult result= gradeService.gradeListByPage(page,rows);
        return result;
    }

    /**
     * 添加年级信息
     * @return
     */
    @ResponseBody
    @RequestMapping("addGrade")
    public String addGrade(String name,HttpServletRequest request){
        String msg="";
        String[] clazzids = request.getParameterValues("clazzid");
        List<String> clazzidList = Arrays.asList(clazzids);
        int a=gradeService.addGrade(name,clazzidList);
        if(a==SUCCESS){
            msg="success";//成功
        }else if (a==FAILURE){
            msg="failure";//失败
        }else if(a==EXIST){
            msg="exist";//年级已存在
        }
        return msg;
    }
    @RequestMapping("gradeListView")
    public String gradeListView(){
        return "/other/gradeList";
    }
}
