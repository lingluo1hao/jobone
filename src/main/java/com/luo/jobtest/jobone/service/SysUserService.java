package com.luo.jobtest.jobone.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.luo.jobtest.jobone.entity.Email;
import com.luo.jobtest.jobone.entity.SysUser;

import java.util.List;

public interface SysUserService {
    SysUser findByUsername(String username);

    List<Email> findALLEmail(Page page, SysUser sysUser);
}
