package com.study.boot.Data.entity;

import java.util.Date;

public class Admin {
    private Integer id;
    private String username;
    private String password;
    private Date createTime;
    private Integer isActive;

    public Admin(Integer id, String username, String password, Date createTime, Integer isActive) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.createTime = createTime;
        this.isActive = isActive;
    }

    public Admin() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }
}
