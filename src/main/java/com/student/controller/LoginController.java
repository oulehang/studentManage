package com.student.controller;

import com.student.bean.User;
import com.student.service.UserService;
import com.student.util.VCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Administrator on 2019/3/1.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;
    /**
     * 获取验证码
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/getVCode")
    public void getVCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //创建验证码生成器对象
        VCodeGenerator vcGenerator = new VCodeGenerator();
        //生成验证码
        String vcode = vcGenerator.generatorVCode();
        //将验证码保存在session域中,以便判断验证码是否正确
        request.getSession().setAttribute("vcode", vcode);
        //生成验证码图片
        BufferedImage vImg = vcGenerator.generatorRotateVCodeImage(vcode, true);
        //输出图像
        ImageIO.write(vImg, "gif", response.getOutputStream());
    }

    /**
     * 用户登录验证
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取用户输入的账户
        String account = request.getParameter("account");
        //获取用户输入的密码
        String password = request.getParameter("password");
        //获取用户输入的验证码
        String vcode = request.getParameter("vcode");
        //获取登录类型
        int type = Integer.parseInt(request.getParameter("type"));

        //返回信息
        String msg = "";

//        //获取session中的验证码
//        String sVcode = (String) request.getSession().getAttribute("vcode");
//        if(!sVcode.equalsIgnoreCase(vcode)){//先判断验证码是否正确
//            msg = "vcodeError";
//        } else{	//判断用户名和密码是否正确
            //将账户和密码封装
            User user = new User();
            user.setAccount(account);
            user.setPassword(password);
            user.setType(type);
            //创建系统数据层对象

            //查询用户是否存在
            User loginUser = userService.getAdmin(user);
            if(loginUser == null){//如果用户名或密码错误
                msg = "loginError";
            } else{ //正确
                if(User.USER_ADMIN == type){
                    msg = "admin";
                } else if(User.USER_STUDENT == type){
                    msg = "student";
                } else if(User.USER_TEACHER == type){
                    msg = "teacher";
                }
                //将该用户名保存到session中
                request.getSession().setAttribute("user", loginUser);
            }
//        }
        //返回登录信息
        response.getWriter().write(msg);

    }
    @RequestMapping("/adminView")
    public String adminView(){
        return "admin/admin";
    }

    @RequestMapping("/studentView")
    public String studentView(){
        return "student/student";
    }

    @RequestMapping("/teacherView")
    public String teacherView(){
        return "teacher/teacher";
    }

    /**
     * 退出系统
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    @RequestMapping("/loginOut")
    private void loginOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //退出系统时清除系统登录的用户
        request.getSession().removeAttribute("user");
        String contextPath = request.getContextPath();
        //转发到登录界面
        response.sendRedirect(contextPath+"/index.jsp");
    }

}
