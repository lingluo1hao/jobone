package com.luo.jobtest.jobone.entity;

/**
 * @author  lww
 *
 */
public class SysUser {
    private  long  id;
    private  String name;
    private  String password;

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

}
