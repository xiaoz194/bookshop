package com.school.bookshop.controller;
import com.school.bookshop.mapper.BookMapper;
import com.school.bookshop.mapper.OrderMapper;
import com.school.bookshop.mapper.UserMapper;
import com.school.bookshop.pojo.Book;
import com.school.bookshop.pojo.Category;
import com.school.bookshop.pojo.Ordr;
import com.school.bookshop.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/shop")

public class ShopController {
    @Autowired
    OrderMapper ordrMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    BookMapper bookMapper;


    /**
     * 用户登录
     * **/
    @ResponseBody
    @RequestMapping("/doLogin")
    public ModelAndView doLogin(ModelAndView modelAndView, @RequestParam("username")String username,
                                @RequestParam("password")String password,HttpServletRequest request){
        boolean flag = true;
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        User user1 = userMapper.doLogin(user);
        if (user1 == null){
            flag=false;
            System.out.println("账号或密码错误！请重新登录!");
            modelAndView.addObject("flag", flag);
            modelAndView.setViewName("home/login");
            return modelAndView;
        }
        Integer uid = user1.getId();
        HttpSession session = request.getSession();
        session.setAttribute("uid",uid);
        flag = true;
        List<Category> categories = bookMapper.selectAllCategory();
        modelAndView.addObject("cate", categories);
        List<Book> books = bookMapper.selectAllBooks();
        modelAndView.addObject("lst", books);
        modelAndView.addObject("flag", flag);
        modelAndView.setViewName("home/index");
        return modelAndView;
    }

    /**
     * 跳转至前台index首页
     * **/
    @ResponseBody
    @RequestMapping("/toHomeIndex")
    public ModelAndView toHomeIndex(ModelAndView modelAndView,HttpServletRequest request){
        boolean flag = true;
        HttpSession session = request.getSession();
        Object uid = session.getAttribute("uid");
        if (uid == null){
            flag = false;
        }
        List<Category> categories = bookMapper.selectAllCategory();
        modelAndView.addObject("cate", categories);
        List<Book> books = bookMapper.selectAllBooks();
        modelAndView.addObject("lst", books);
        modelAndView.addObject("flag", flag);
        modelAndView.setViewName("home/index");
        return modelAndView;
    }

    /**
     *查看图书商品详情
     * **/
    @ResponseBody
    @RequestMapping("/detail")
    public ModelAndView detail(ModelAndView modelAndView,Integer id,
                               HttpServletRequest request){
        boolean flag = true;
        HttpSession session = request.getSession();
        Object uid = session.getAttribute("uid");
        if (uid == null){
            flag = false;
        }
        Book book = bookMapper.selectById(id);
        modelAndView.addObject("lst", book);
        List<Category> categories =bookMapper.selectAllCategory();
        modelAndView.addObject("cate", categories);
        modelAndView.addObject("flag", flag);
        modelAndView.setViewName("home/detail");

        return modelAndView;
    }
    /**
     * 用户添加图书至购物车
     * **/
    @ResponseBody
    @RequestMapping("/addCar")
    public ModelAndView addCar(ModelAndView modelAndView, HttpServletRequest request, Integer id){
        HttpSession session = request.getSession();
        Object uid = session.getAttribute("uid");
        if (uid == null){
            modelAndView.setViewName("home/login");
            return modelAndView;
        }
        Integer u_id = Integer.parseInt(uid.toString());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Date date = new Date();
        String format = df.format(date);
        String count = request.getParameter("count");
        Ordr ordr = new Ordr();
        ordr.setBid(id);
        ordr.setUid(u_id);
        ordr.setCount(count);
        ordr.setStrDateTime(format);
        ordr.setTime(date);
        ordrMapper.addOrdr(ordr);
        System.out.println("添加成功!");
        toHomeIndex(modelAndView,request);
        return modelAndView;
    }

    /**
     * 跳转至购物车界面
     * **/
    @ResponseBody
    @RequestMapping("/toCar")
    public ModelAndView toCar(ModelAndView modelAndView,HttpServletRequest request){
        boolean flag = true;
        HttpSession session = request.getSession();
        Object uid = session.getAttribute("uid");
        if (uid == null){
            flag = false;
            modelAndView.setViewName("home/error");
            return modelAndView;
        }
        Integer u_id = Integer.parseInt(uid.toString());
        List<Ordr> ordrs = ordrMapper.selectByUid(u_id);
        for(Ordr ordr:ordrs){//计算总价
            Integer money = ordr.getBook().getMoney();
            String count = ordr.getCount();
            int i = Integer.parseInt(count);
            int total = money*i;
            ordr.setTotalPrice(total);
        }
        List<Category> categories = bookMapper.selectAllCategory();
        modelAndView.addObject("cate", categories);
        modelAndView.addObject("lst", ordrs);
        modelAndView.addObject("flag", flag);
        modelAndView.setViewName("home/car");
        return modelAndView;
    }

    /**
     * 取消商品加入购物车
     * **/
    @ResponseBody
    @RequestMapping("/deleteOrder")
    public ModelAndView deleteOrder(ModelAndView modelAndView,HttpServletRequest request,Integer id){
        boolean flag = true;
        HttpSession session = request.getSession();
        Object uid = session.getAttribute("uid");
        if (uid == null){
            flag = false;
            modelAndView.setViewName("home/error");
            return modelAndView;
        }
        ordrMapper.delOrdr(id);
        Integer u_id = Integer.parseInt(uid.toString());
        List<Ordr> ordrs = ordrMapper.selectByUid(u_id);
        modelAndView.addObject("lst", ordrs);
        modelAndView.addObject("flag", flag);
        List<Category> categories = bookMapper.selectAllCategory();
        modelAndView.addObject("cate", categories);
        modelAndView.setViewName("home/car");
        return modelAndView;
    }
    /**
     * 购买图书
     * **/
    @ResponseBody
    @RequestMapping("/doBuy")
    public ModelAndView doBuy(ModelAndView modelAndView,HttpServletRequest request,Integer id){
        boolean flag = true;
        HttpSession session = request.getSession();
        Object uid = session.getAttribute("uid");
        if (uid == null){
            flag = false;
            modelAndView.setViewName("home/error");
            return modelAndView;
        }
        Ordr ordr = ordrMapper.selectByid(id);
        System.out.println("下单成功，请等待商家确认订单......");
        Integer f = ordr.getFlag();
        if (f == 1){
            List<Ordr> ordrs = new ArrayList<Ordr>();
            ordrs.add(ordr);
            for(Ordr o:ordrs){//计算总价
                Integer money = o.getBook().getMoney();
                String count = o.getCount();
                int i = Integer.parseInt(count);
                int total = money*i;
                o.setTotalPrice(total);
            }
            modelAndView.addObject("lst", ordrs);
            modelAndView.addObject("flag", flag);
            modelAndView.setViewName("home/ordr");
        }else {
            List<Ordr> ordrs = ordrMapper.selectFlag((Integer) uid);
            for(Ordr o:ordrs){//计算总价
                Integer money = o.getBook().getMoney();
                String count = o.getCount();
                int i = Integer.parseInt(count);
                int total = money*i;
                o.setTotalPrice(total);
            }
            modelAndView.addObject("lst", ordrs);
            modelAndView.addObject("flag", flag);
            List<Category> categories = bookMapper.selectAllCategory();
            modelAndView.addObject("cate", categories);
            modelAndView.setViewName("home/ordr");
        }
        return modelAndView;
    }


    /**
     * 跳转至我的订单
     * **/
    @ResponseBody
    @RequestMapping("/toMyOrdr")
    public ModelAndView toMyOrdr(ModelAndView modelAndView,HttpServletRequest request){
        boolean flag = true;
        HttpSession session = request.getSession();
        Object uid = session.getAttribute("uid");
        if (uid == null){
            flag = false;
            modelAndView.setViewName("home/error");
            return modelAndView;
        }
        List<Ordr> ordrs = ordrMapper.selectFlag((Integer) uid);
        for(Ordr ordr:ordrs){//计算总价
            Integer money = ordr.getBook().getMoney();
            String count = ordr.getCount();
            int i = Integer.parseInt(count);
            int total = money*i;
            ordr.setTotalPrice(total);
        }
        modelAndView.addObject("lst", ordrs);
        modelAndView.addObject("flag", flag);
        List<Category> categories = bookMapper.selectAllCategory();
        modelAndView.addObject("cate", categories);
        modelAndView.setViewName("home/ordr");
        return modelAndView;
    }

    /**
     * 搜索功能,未登录状态也可以进行的操作。
     * **/
    @ResponseBody
    @RequestMapping("search")
    public ModelAndView search(ModelAndView modelAndView,HttpServletRequest request,
                               @RequestParam("bookName")String bookName){
        Book book = bookMapper.selectNameToGetId(bookName);
        modelAndView.addObject("lst", book);
        List<Category> categories =bookMapper.selectAllCategory();
        modelAndView.addObject("cate", categories);
        modelAndView.setViewName("home/detail");
        return modelAndView;
    }


    //=================用户个人中心======================
    //跳转至用户个人中心页面
    @ResponseBody()
    @RequestMapping("/center")
    public ModelAndView center(ModelAndView modelAndView,HttpServletRequest request){
        boolean flag = true;
        HttpSession session = request.getSession();
        Object uid = session.getAttribute("uid");
        if (uid == null){
            flag = false;
            modelAndView.setViewName("home/error");
            return modelAndView;
        }
        List<Category> categories = bookMapper.selectAllCategory();
        modelAndView.addObject("cate", categories);
        modelAndView.addObject("flag", flag);
        User user = userMapper.selectByUid((Integer) uid);
        modelAndView.addObject("lst", user);
        modelAndView.setViewName("home/center");
        return modelAndView;
    }

    //用户修改个人资料
    @ResponseBody
    @RequestMapping("/doEditPersonalInformation")
    public ModelAndView doEditPersonalInformation(ModelAndView modelAndView,
                                                  HttpServletRequest request,
                                                  @RequestParam("password")String password,
                                                  @RequestParam("address")String address,
                                                  @RequestParam("tell")String tell){
        boolean flag = true;
        HttpSession session = request.getSession();
        Object uid = session.getAttribute("uid");
        if (uid == null){
            flag = false;
            modelAndView.setViewName("home/error");
            return modelAndView;
        }
        User user = new User();
        user.setId((Integer) uid);
        user.setPassword(password);
        user.setAddress(address);
        user.setTell(tell);
        userMapper.update(user);
        List<Category> categories = bookMapper.selectAllCategory();
        modelAndView.addObject("cate", categories);
        modelAndView.addObject("flag", flag);
        User user1 = userMapper.selectByUid((Integer) uid);
        modelAndView.addObject("lst", user1);
        modelAndView.setViewName("home/center");
        return modelAndView;
    }

    /**
     * 用户登出系统
     * **/
    @ResponseBody
    @RequestMapping("/userLogout")
    public ModelAndView userLogout(ModelAndView modelAndView,HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        boolean flag = false;
        List<Category> categories = bookMapper.selectAllCategory();
        modelAndView.addObject("cate", categories);
        List<Book> books = bookMapper.selectAllBooks();
        modelAndView.addObject("lst", books);
        modelAndView.addObject("flag", flag);
        modelAndView.setViewName("home/index");
        return modelAndView;
    }






}
