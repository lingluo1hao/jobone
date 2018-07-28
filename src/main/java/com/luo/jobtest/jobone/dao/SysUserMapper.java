package com.luo.jobtest.jobone.dao;


import com.luo.jobtest.jobone.entity.Email;
import com.luo.jobtest.jobone.entity.SysUser;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface SysUserMapper {
    SysUser findByUsername(String name);

    /**
     *
     * @since   aaa
     * @param page
     * @param email
     * @return list<email></email>
     * @author lww
     */
    List<Email> findAllEmail(Page page, SysUser email);
}
