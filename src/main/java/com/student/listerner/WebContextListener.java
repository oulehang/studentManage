package com.student.listerner;

import com.student.bean.System;
import com.student.service.SystemService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

/**
 * 系统初始化
 * @author
 *
 */
public class WebContextListener implements InitializingBean, ServletContextAware {

    @Autowired
    private SystemService systemService;


    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        ServletContextEvent servletContextEvent=new ServletContextEvent(servletContext);
        ServletContext application = servletContextEvent.getServletContext();
        //获取系统初始化对象
        System sys =systemService.getAll();
        //放到域中
        application.setAttribute("systemInfo", sys);
        Object systemInfo = application.getAttribute("systemInfo");
    }
}
