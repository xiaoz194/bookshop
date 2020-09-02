package com.school.bookshop.controller;

import com.school.bookshop.mapper.AdminMapper;
import com.school.bookshop.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class AdminController {
    @Autowired
    AdminMapper adminMapper;
    /**
     * 管理员登录
     * **/
    @ResponseBody
    @RequestMapping("/adminLogin")
    public ModelAndView doLogin(ModelAndView modelAndView, HttpServletRequest request,
                                @RequestParam("username")String username,
                                @RequestParam("password")String password){
        Admin admin  = new Admin();
        admin.setUsername(username);
        admin.setPassword(password);
        Integer res = adminMapper.doLogin(admin);
        if (res == null){
            System.out.println("账号或密码错误！请重新登录!");
            modelAndView.setViewName("admin/login");
            return modelAndView;
        }
        HttpSession session = request.getSession();
        session.setAttribute("loginName",username);
        modelAndView.setViewName("admin/index");
        return modelAndView;
    }
    /**
     * 管理员登出系统
     * **/
    @ResponseBody
    @RequestMapping("/adminLogout")
    public ModelAndView logout(ModelAndView modelAndView, HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        modelAndView.setViewName("admin/login");
        return modelAndView;
    }



}
