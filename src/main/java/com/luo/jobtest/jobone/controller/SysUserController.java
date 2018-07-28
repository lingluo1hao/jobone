package com.luo.jobtest.jobone.controller;


import com.luo.jobtest.jobone.entity.SysUser;
import com.luo.jobtest.jobone.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("test")
    @ResponseBody
    public SysUser test(@RequestParam String name){
        return  sysUserService.findByUsername(name);

    }
}
