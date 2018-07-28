package com.luo.jobtest.jobone.schedule;

import com.luo.jobtest.jobone.entity.SysUser;
import com.luo.jobtest.jobone.entity.User;
import com.luo.jobtest.jobone.service.SysUserService;
import com.luo.jobtest.jobone.service.UserService;
import jdk.nashorn.internal.ir.ContinueNode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Configuration
@Component
public class InsertUserSchedule {

    private static final Log logger =  LogFactory.getLog(InsertUserSchedule.class);
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private UserService userService;


    @Async
    @Transactional(rollbackOn = Exception.class)
    @Scheduled(cron = "0 0/1 * * * ?")
    public void insertUser(){
        SysUser sysUser=new SysUser();
        try {
            sysUser = sysUserService.findByUsername("22");
        }catch (Exception e){
            e.printStackTrace();
            logger.info("读取数据异常");
        }
        List<User> listUser=new ArrayList<>();
        for(int i=4;i<=100;i++) {
            User user = new User();
            user.setId(i);
            user.setName(sysUser.getName());
            user.setEmail("1232@126com");
            listUser.add(user);
        }
        try {
            //userService.save(user);
            userService.batchInsert(listUser);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("插入数据异常");

        }

    }


}
