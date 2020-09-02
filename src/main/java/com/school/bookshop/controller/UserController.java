package com.school.bookshop.controller;

import com.school.bookshop.mapper.BookMapper;
import com.school.bookshop.mapper.UserMapper;
import com.school.bookshop.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    BookMapper bookMapper;
    //----------管理员管理用户模块---------------
    /**
     * 管理员管理用户信息
     * **/
    @ResponseBody
    @RequestMapping("/selectAllUser")
    public ModelAndView selectAllUser(ModelAndView modelAndView, HttpServletRequest request){
        HttpSession session = request.getSession();
        String loginName = (String)session.getAttribute("loginName");
        if (loginName==null){
            modelAndView.setViewName("admin/login");
            return modelAndView;
        }
        List<User> users = userMapper.selectAllUsers();
        modelAndView.addObject("lst", users);
        modelAndView.setViewName("admin/userinfo");
        return modelAndView;
    }
    /**
     * 管理删除一条用户信息
     * **/
    @ResponseBody
    @RequestMapping("/deleteUserById")
    public ModelAndView deleteUserById(ModelAndView modelAndView,HttpServletRequest request,
                                       Integer id){
        HttpSession session = request.getSession();
        Object loginName = session.getAttribute("loginName");
        if (loginName == null){
            modelAndView.setViewName("admin/login");
            return modelAndView;
        }
        userMapper.deleteUserById(id);
//        category(modelAndView,request);
        List<User> users = userMapper.selectAllUsers();
        modelAndView.addObject("lst", users);
        modelAndView.setViewName("admin/userinfo");
        return modelAndView;
    }
//
//    /**
//     * 用户登录
//     * **/
//    @ResponseBody
//    @RequestMapping("/doLogin")
//    public ModelAndView doLogin(ModelAndView modelAndView, @RequestParam("username")String username,
//                                @RequestParam("password")String password,HttpServletRequest request){
//        boolean flag = true;
//        User user = new User();
//        user.setUsername(username);
//        user.setPassword(password);
//        Integer res = userMapper.doLogin(user);
//        if (res == null){
//            flag=false;
//            System.out.println("账号或密码错误！请重新登录!");
//            modelAndView.addObject("flag", flag);
//            modelAndView.setViewName("home/login");
//            return modelAndView;
//        }
//        Integer uid = user.getId();
//        HttpSession session = request.getSession();
//        session.setAttribute("uid",uid);
//        flag = true;
//        List<Book> books = bookMapper.selectAllBooks();
//        modelAndView.addObject("lst", books);
//        modelAndView.addObject("flag", flag);
//        modelAndView.setViewName("home/index");
//        return modelAndView;
//    }
    /**
     * 用户注册
     * **/
    @ResponseBody
    @RequestMapping("/doReg")
    public ModelAndView regist(ModelAndView modelAndView,@RequestParam("username")String username,
                               @RequestParam("password")String password,
                               @RequestParam("address")String address,
                               @RequestParam("tell")String tell){
        User user =  new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setAddress(address);
        user.setTell(tell);
        Integer res = userMapper.selectByNamePwd(user);
        if (res == null){
            userMapper.regist(user);
            System.out.println("注册成功！");
            modelAndView.setViewName("home/login");
            return modelAndView;
        }else {

            System.out.println("该用户名已注册！请重新注册！");
            modelAndView.setViewName("home/regist");
            return modelAndView;
        }
    }




}
