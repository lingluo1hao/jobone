package com.luo.jobtest.jobone.dao;


import com.luo.jobtest.jobone.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper {
    SysUser findByUsername(String name);
}
