package com.luo.jobtest.jobone.schedule;

import com.baomidou.mybatisplus.plugins.Page;
import com.luo.jobtest.jobone.entity.Email;
import com.luo.jobtest.jobone.entity.SysUser;
import com.luo.jobtest.jobone.entity.User;
import com.luo.jobtest.jobone.service.EmailService;
import com.luo.jobtest.jobone.service.SysUserService;
import com.luo.jobtest.jobone.service.UserService;
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
    @Autowired
    private EmailService emailService;

/*
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
*/
    @Async
    @Transactional(rollbackOn = Exception.class)
    @Scheduled(cron = "0 0/1 * * * ?")
    public void  insertBatchEmail(){
        List<Email>  listMail =new ArrayList<>();
        SysUser sysUser =new SysUser();
        sysUser.setName("22");
        Page page=new Page(1,50);
        try{
            listMail=sysUserService.findALLEmail(page,sysUser);
        }catch (Exception e){
            logger.error("查询数据失败");
            e.printStackTrace();
        }
        //logger.info(listMail.toString());

        try {
            emailService.batchInsert(listMail);
        }catch (Exception e){
            logger.error("批量插入数据失败");
            e.printStackTrace();
        }



    }



}
