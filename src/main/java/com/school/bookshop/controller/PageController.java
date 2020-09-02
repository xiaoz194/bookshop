package com.school.bookshop.controller;

import com.school.bookshop.mapper.BookMapper;
import com.school.bookshop.pojo.Book;
import com.school.bookshop.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("page")
public class PageController {

    @Autowired
    BookMapper bookMapper;

    /**
     * 跳转至管理员登录页面
     * **/
    @ResponseBody
    @RequestMapping("/toLogin")
    public ModelAndView toLogin(ModelAndView modelAndView){
        System.out.println("into...");
        modelAndView.setViewName("admin/login");
        return modelAndView;
    }
    /**
     * 跳转至管理员后台主页index
     * **/
    @ResponseBody
    @RequestMapping("/toIndex")
    public ModelAndView toIndex(ModelAndView modelAndView, HttpServletRequest request){
        System.out.println("11111111");

        HttpSession session = request.getSession();
        String loginName = (String)session.getAttribute("loginName");
        if (loginName==null){
            modelAndView.setViewName("admin/login");
            return modelAndView;
        }
        modelAndView.setViewName("admin/index");
        return modelAndView;
    }
    /**
     * 跳转至新增图书界面
     * **/
    @ResponseBody
    @RequestMapping("toAddBook")
    public ModelAndView toAddBook(ModelAndView modelAndView, HttpServletRequest request){
        HttpSession session = request.getSession();
        Object loginName = session.getAttribute("loginName");
        if (loginName == null){
            modelAndView.setViewName("admin/login");
            return modelAndView;
        }
        List<Category> categories = bookMapper.selectAllCategory();
        modelAndView.addObject("lst", categories);
        modelAndView.setViewName("admin/addbook");
        return modelAndView;
    }


    /**
     * 跳转至管理员登录页面
     * **/
    @ResponseBody
    @RequestMapping("/toHomeLogin")
    public ModelAndView toHomeLogin(ModelAndView modelAndView){
        System.out.println("into...");
        modelAndView.setViewName("home/login");
        return modelAndView;
    }

    /**
     * 跳转至用户注册页面
     * **/
    @ResponseBody
    @RequestMapping("/toReg")
    public ModelAndView toReg(ModelAndView modelAndView){
        System.out.println("into...");
        modelAndView.setViewName("home/regist");
        return modelAndView;
    }

}
