package com.school.bookshop.controller;

import com.school.bookshop.mapper.OrderMapper;
import com.school.bookshop.pojo.Ordr;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("order")
public class OrderController {
    @Autowired
    OrderMapper ordrMapper;
    //-----订单管理模块-----------
    /**
     * 订单信息展示
     * **/
    @ResponseBody
    @RequestMapping("/selectAllOrders")
    public ModelAndView selectAllOrders(ModelAndView modelAndView, HttpServletRequest request){
        HttpSession session = request.getSession();
        Object loginName = session.getAttribute("loginName");
        if (loginName == null){
            modelAndView.setViewName("admin/login");
            return modelAndView;
        }
        List<Ordr> orders = ordrMapper.selectAllOrders();
        //将数据库的Date类型转化为String类型 时间格式化
        for (Ordr order:orders){
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
            java.util.Date da = order.getTime();
            String str = df.format(da);
            order.setStrDateTime(str);
            Integer money = order.getBook().getMoney();
            String count = order.getCount();
            int i = Integer.parseInt(count);
            int total = money*i;
            order.setTotalPrice(total);
        }
        modelAndView.addObject("lst", orders);
        modelAndView.setViewName("admin/order");
        return modelAndView;
    }

    /**
     * 管理员确认订单
     * **/
    @ResponseBody
    @RequestMapping("/checking")
    public ModelAndView checking(ModelAndView modelAndView,Integer flag,HttpServletRequest request,Integer id){
        System.out.println("into checking model...");
        HttpSession session = request.getSession();
        String loginName = (String)session.getAttribute("loginName");
        if (loginName == null){
            modelAndView.setViewName("admin/login");
            return modelAndView;
        }
        if (flag == 0){
//            flag = 1;
            ordrMapper.updateFlag(id);
            selectAllOrders(modelAndView, request);
        }
        return modelAndView;
    }
    /**
     * 管理员删除订单
     * **/
    @ResponseBody
    @RequestMapping("/delOrdr")
    public ModelAndView delOrdr(ModelAndView modelAndView,HttpServletRequest request,Integer id){
        HttpSession session = request.getSession();
        String loginName = (String)session.getAttribute("loginName");
        if (loginName == null){
            modelAndView.setViewName("admin/login");
            return modelAndView;
        }
        ordrMapper.delOrdr(id);
        selectAllOrders(modelAndView, request);
        return modelAndView;
    }
//    /**
//     * 用户添加图书至购物车
//     * **/
//    @ResponseBody
//    @RequestMapping("/addCar")
//    public ModelAndView addCar(ModelAndView modelAndView, HttpServletRequest request, Integer bid){
//        HttpSession session = request.getSession();
//        String uid = (String)session.getAttribute("uid");
//        if (uid == null){
//            modelAndView.setViewName("home/login");
//            return modelAndView;
//        }
//        Integer u_id = Integer.parseInt(uid);
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//        String format = df.format(new Date());
//        String count = request.getParameter("count");
//        Ordr ordr = new Ordr();
//        ordr.setBid(bid);
//        ordr.setUid(u_id);
//        ordr.setCount(count);
//        ordr.setStrDateTime(format);
//        ordrMapper.addOrdr(ordr);
//        System.out.println("添加成功!");
//        modelAndView.setViewName("home/detail");
//        return modelAndView;
//    }





}
