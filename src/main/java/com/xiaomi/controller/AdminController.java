package com.xiaomi.controller;

import com.xiaomi.pojo.Admin;
import com.xiaomi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Lu
 * @Date: 2023/4/30 16:53
 * @Description:
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    //注册的是该类的实现类
    private AdminService adminService;

    //实现登录判断，并且跳转到响应页面
    @RequestMapping("/login.action")
    public String login(@RequestParam(value = "name") String name, String pwd, HttpServletRequest request){
        Admin admin = adminService.login(name, pwd);
        if(admin==null){
            request.setAttribute("errmsg","用户名或密码不正确");
            return "";
        }
        //登录成功将用户姓名和密码放入请求域
        request.setAttribute("admin",admin);
        return "main";
    }
}