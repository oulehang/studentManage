package com.student.controller;

import com.student.bean.User;
import com.student.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/AllUser")
    public String  getUserByid(String id, Model model){

        List<User>  user= userService.selectAll();
        model.addAttribute("list",user);
        return  "test";
    }


}
