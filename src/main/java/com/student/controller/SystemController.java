package com.student.controller;

import com.student.bean.System;
import com.student.bean.User;
import com.student.service.SystemService;
import com.student.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2019/3/7.
 */
@Controller
@RequestMapping("system")
public class SystemController {

    @Autowired
    private UserService userService;
    @Autowired
    private SystemService systemService;

    /**
     * 修改系统信息
     * @param name
     * @param id
     * @param value
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("editSystemInfo")
    public String editSystemInfo(String name,String id ,String value, HttpServletRequest request){
        String msg="";
        System sys = systemService.editSystemInfo(id,name,value);
        if (sys==null){
            return msg;
        }
        request.getServletContext().setAttribute("systemInfo",sys);
        msg="success";
        return msg;
    }

   /**
     * 修改密码
     * @return
     */
    @ResponseBody
    @RequestMapping("editPasswod")
    public void editPasswod(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user1=new User();
        user1.setAccount(request.getParameter("account"));
        user1.setPassword(request.getParameter("oldPassword"));
        User user2 = userService.getAdmin(user1);
        if(user2==null){
            response.getWriter().write("error");
            return;
        }else{
            User user = new User();
            user.setAccount(request.getParameter("account"));
            user.setPassword(request.getParameter("password"));
            int i = systemService.editPassword(user);
            if(i>0){
                response.getWriter().write("success");
            }
        }

    }
    /**
     * 教师修改密码
     * @return
     */
    @ResponseBody
    @RequestMapping("teacherEditPassword")
    public void teacherEditPassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user1=new User();
        user1.setAccount(request.getParameter("account"));
        user1.setPassword(request.getParameter("oldPassword"));
        user1.setType(3);//教师
        User user2 = userService.getAdmin(user1);
        if(user2==null){
            response.getWriter().write("error");
            return;
        }else{
            User user = new User();
            user.setAccount(request.getParameter("account"));
            user.setPassword(request.getParameter("password"));
            int i = systemService.editPassword(user);
            if(i>0){
                response.getWriter().write("success");
            }
        }

    }

    @RequestMapping("adminPersonalView")
    public String adminPersonalView(){
        return "/admin/adminPersonal";
    }
}
