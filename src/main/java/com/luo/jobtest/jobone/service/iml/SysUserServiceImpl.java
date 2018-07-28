package com.luo.jobtest.jobone.service.iml;


import com.luo.jobtest.jobone.dao.SysUserMapper;
import com.luo.jobtest.jobone.entity.SysUser;
import com.luo.jobtest.jobone.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {


    @Autowired
    private SysUserMapper sysUserMapper;
    @Override
    public SysUser findByUsername(String name) {
        return sysUserMapper.findByUsername(name);
    }

}
