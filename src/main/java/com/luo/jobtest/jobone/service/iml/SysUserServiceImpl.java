package com.luo.jobtest.jobone.service.iml;


import com.baomidou.mybatisplus.plugins.Page;
import com.luo.jobtest.jobone.dao.SysUserMapper;
import com.luo.jobtest.jobone.entity.Email;
import com.luo.jobtest.jobone.entity.SysUser;
import com.luo.jobtest.jobone.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {


    @Autowired
    private SysUserMapper sysUserMapper;
    @Override
    public SysUser findByUsername(String name) {
        return sysUserMapper.findByUsername(name);
    }

    @Override
    public List<Email> findALLEmail(Page page, SysUser sysUser) {
        return sysUserMapper.findAllEmail(page,sysUser);
    }


}
