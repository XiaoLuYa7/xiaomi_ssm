package com.xiaomi.service;

import com.xiaomi.pojo.Admin;

/**
 * @Author: Lu
 * @Date: 2023/4/29 19:43
 * @Description:
 */
public interface AdminService {

    //判断登录
    Admin login(String name,String pwd);
}