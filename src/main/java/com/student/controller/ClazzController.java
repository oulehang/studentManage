package com.student.controller;

import com.alibaba.fastjson.JSONObject;
import com.student.bean.Clazz;
import com.student.bean.DatagridResult;
import com.student.service.ClazzService;
import com.student.util.StringTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2019/3/5.
 */
@Controller
@RequestMapping("/clazz")
public class ClazzController {
    private final int SUCCESS=1;//成功
    private final int FAILURE=2;//失败
    private final int EXIST=3;//失败，已存在
    @Autowired
    private ClazzService clazzService;

    /**
     * 班级下拉框
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/clazzList")
    public String clazzList(HttpServletRequest request){
        String gradeid = request.getParameter("gradeid");
        if(StringTool.isEmpty(gradeid)){
            return "";
        }
        List<Clazz> clazzList=clazzService.getClazzByGradeId(gradeid);
        String result= JSONObject.toJSONString(clazzList);
        return result;
    }

    /**
     * 班级详细信息；
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("clazzDetailList")
    public DatagridResult clazzDetailList(HttpServletRequest request){
        //获取分页参数
        int page = Integer.parseInt(request.getParameter("page"));
        int rows = Integer.parseInt(request.getParameter("rows"));
        //年级ID
        String gradeid = request.getParameter("gradeid");

        Clazz clazz=new Clazz();
        if(!StringTool.isEmpty(gradeid)){
            clazz.setGradeid(Integer.parseInt(gradeid));
        }

        DatagridResult result= clazzService.clazzDetailList(page,rows,clazz);
        return result;
    }

    /**
     * 添加班级
     * @param name
      @param gradeid
     * @return
     */
    @ResponseBody
    @RequestMapping("addClazz")
    public String addClazz(String name,int gradeid){
        String msg="";
       int a= clazzService.addClazz(name,gradeid);
        if(a==SUCCESS){
            msg="success";//成功
        }else if (a==FAILURE){
            msg="failure";//失败
        }else if(a==EXIST){
            msg="exist";//班级已存在
        }
        return msg;
    }

    @RequestMapping("clazzListView")
    public String gradeListView(){
        return "/other/clazzList";
    }
}
