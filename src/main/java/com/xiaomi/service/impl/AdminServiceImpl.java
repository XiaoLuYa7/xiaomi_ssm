package com.xiaomi.service.impl;

import com.xiaomi.mapper.AdminMapper;
import com.xiaomi.pojo.Admin;
import com.xiaomi.pojo.AdminExample;
import com.xiaomi.service.AdminService;
import com.xiaomi.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Lu
 * @Date: 2023/4/29 19:44
 * @Description:
 */
@Service
public class AdminServiceImpl implements AdminService {
    //自动注入
    @Autowired
    //数据访问层的对像
    private AdminMapper adminMapper;

    /**
     * 用户登录的业务层
     * @param name
     * @param pwd
     * @return
     */
    @Override
    public Admin login(String name, String pwd) {
        //根据传入的用户到数据库中查询相应对象
        //如果有条件，则必须创建Example对象
        AdminExample adminExample = new AdminExample();
        //将name作为条件进行查询
        adminExample.createCriteria().andANameEqualTo(name);
        List<Admin> admins = adminMapper.selectByExample(adminExample);
        //查询到的用户
        Admin admin;
        //大于0则代表查询到用户
        if (admins.size()>0){
            //用户名不能重复，存在的话第一个元素就是指定的用户
            admin = admins.get(0);
            //查询到之后比较密码是否正确
            String pwdMd5 = MD5Util.getMD5(pwd);
            if(admin.getaPass().equals(pwdMd5)){
                return admin;
            }
        }
        return null;
    }
}