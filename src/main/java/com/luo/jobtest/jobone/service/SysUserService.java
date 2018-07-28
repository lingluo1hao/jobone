package com.luo.jobtest.jobone.service;


import com.luo.jobtest.jobone.entity.SysUser;

public interface SysUserService {
    SysUser findByUsername(String username);
}
