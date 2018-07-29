package com.luo.jobtest.jobone.controller;


import com.luo.jobtest.jobone.entity.User;
import com.luo.jobtest.jobone.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "/lwUser")
public class LwUserController {
    @Resource
    private UserService userService;

    @RequestMapping(value = "/test")
    public String test(Model model) {
        //查询数据库所有用户
        List<User> sysUsers=userService.findAll();
        model.addAttribute("users",sysUsers);
        return  "sysUsers";
    }
}
