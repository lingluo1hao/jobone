package com.luo.jobtest.jobone;

import com.luo.jobtest.jobone.entity.User;
import com.luo.jobtest.jobone.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JoboneApplicationTests {

    @Resource
    private  UserService userService;
    @Test
    public void contextLoads() {
        User user=new User();
        user.setId(3);
        user.setEmail("luowenwu3hao@163.com");
        user.setName("test1");
        userService.save(user);
    }

}
