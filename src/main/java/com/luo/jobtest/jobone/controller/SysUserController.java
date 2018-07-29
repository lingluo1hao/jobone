package com.luo.jobtest.jobone.controller;


import com.luo.jobtest.jobone.config.BaseRabbitmq;
import com.luo.jobtest.jobone.config.Rabbitmq;
import com.luo.jobtest.jobone.entity.SysUser;
import com.luo.jobtest.jobone.service.QueueService;
import com.luo.jobtest.jobone.service.SysUserService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@CommonsLog
@Controller
@RequestMapping("/")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private QueueService queueService;

    @RequestMapping("test")
    @ResponseBody
    public SysUser test(@RequestParam String name){
        queueService.sendPushRegisterDeviceMessage("22",true,"abcdefg");
        log.info(BaseRabbitmq.KEY_PREFIX+Rabbitmq.KEY_BCB_6X_SYS_NAME);
        return  sysUserService.findByUsername(name);

    }
}
