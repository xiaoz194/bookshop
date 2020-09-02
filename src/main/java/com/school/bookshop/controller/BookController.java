package com.school.bookshop.controller;

import com.school.bookshop.mapper.BookMapper;
import com.school.bookshop.pojo.Book;
import com.school.bookshop.pojo.Category;
import com.school.bookshop.pojo.Ordr;
import com.school.bookshop.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

@Controller
@RequestMapping("book")
public class BookController {
    @Autowired
    BookMapper bookMapper;

    //------------图书类别管理模块---------------
    /**
     * 图书类别管理
     * **/
    @ResponseBody
    @RequestMapping("/category")
    public ModelAndView category(ModelAndView modelAndView, HttpServletRequest request){
        HttpSession session = request.getSession();
        String loginName = (String)session.getAttribute("loginName");
        if (loginName==null){
            modelAndView.setViewName("admin/login");
            return modelAndView;
        }
        List<Category> categories = bookMapper.selectAllCategory();
        modelAndView.addObject("lst", categories);
        modelAndView.setViewName("admin/category");
        return modelAndView;
    }

    /**
     * 删除选中的一条分类信息
     * **/
    @ResponseBody
    @RequestMapping("/deleteCategory")
    public ModelAndView deleteCategory(ModelAndView modelAndView,HttpServletRequest request,
                                       Integer id){
        HttpSession session = request.getSession();
        Object loginName = session.getAttribute("loginName");
        if (loginName == null){
            modelAndView.setViewName("admin/login");
            return modelAndView;
        }
        bookMapper.deleteCategory(id);
        category(modelAndView,request);
        return modelAndView;
    }

    /**
     * 增加一条分类
     * **/
    @ResponseBody
    @RequestMapping("/addCategory")
    public ModelAndView addCategory(ModelAndView modelAndView,HttpServletRequest request,
                                    @RequestParam("cname")String cname){
        Category category = new Category();
        category.setCname(cname);
        System.out.println("add function...");
        HttpSession session = request.getSession();
        Object loginName = session.getAttribute("loginName");
        if (loginName == null){
            modelAndView.setViewName("admin/login");
            return modelAndView;
        }
        bookMapper.addCategory(category);
        category(modelAndView, request);
        return modelAndView;
    }
    //--------------------图书信息管理模块------------------
    /**
     * 显示图书信息
     * 关联查询
     * **/
    @ResponseBody
    @RequestMapping("info")
    public ModelAndView info(ModelAndView modelAndView,HttpServletRequest request){
        HttpSession session = request.getSession();
        Object loginName = session.getAttribute("loginName");
        if (loginName == null){
            modelAndView.setViewName("admin/login");
            return modelAndView;
        }
        List<Book> books = bookMapper.selectAllBooks();
        modelAndView.addObject("lst", books);
        modelAndView.setViewName("admin/book");
        return modelAndView;
    }
    /**
     * 根据ID删除图书
     * **/
    @ResponseBody
    @RequestMapping("/deleteBookById")
    public ModelAndView deleteProductById(ModelAndView modelAndView,HttpServletRequest request,int id){
        HttpSession session = request.getSession();
        String loginName = (String)session.getAttribute("loginName");
        if (loginName==null){
            modelAndView.setViewName("admin/login");
            return modelAndView;
        }
        bookMapper.deleteBookById(id);
        List<Book> books = bookMapper.selectAllBooks();
        modelAndView.addObject("lst", books);
        modelAndView.setViewName("admin/book");
        return modelAndView;
    }

    /**
     * 添加图书信息
     * **/
    @ResponseBody
    @RequestMapping("/doAddBook")
    public ModelAndView doAddBook(ModelAndView modelAndView, HttpServletRequest request,
                                  @RequestParam("bookname")String bookName,
                                  @RequestParam("author")String author,
                                  @RequestParam("dsc")String dsc,
                                  @RequestParam("money")Integer money,
                                  @RequestParam("file")MultipartFile file){
        HttpSession session = request.getSession();
        Object loginName = session.getAttribute("loginName");
        if (loginName == null){
            modelAndView.setViewName("admin/login");
            return modelAndView;
        }
        String data = request.getParameter("selectCate");
        Integer cid = Integer.parseInt(data);
        Book book = new Book();
        book.setBookName(bookName);
        book.setAuthor(author);
        book.setCid(cid);
        book.setDsc(dsc);
        book.setMoney(money);
        if(file.isEmpty()){
            System.out.println("上传失败！");
        }

        //拿到文件名
        String filename = file.getOriginalFilename();
        //存放上传图片的文件夹
        File fileDir = UploadUtils.getImgDirFile();
        System.out.println(fileDir.getAbsolutePath());
        try{
            //构建真实的文件路径
            File newFile = new File(fileDir.getAbsolutePath()+File.separator+filename);
            System.out.println(newFile.getAbsolutePath());
            file.transferTo(newFile);
        }catch (Exception e){
            e.printStackTrace();
        }
        String fpath = "/imgs/"+filename;
        book.setPic(fpath);

        bookMapper.addBook(book);
        List<Book> books = bookMapper.selectAllBooks();
        modelAndView.addObject("lst", books);
        modelAndView.setViewName("admin/book");
        return modelAndView;
    }



    /**
     * 选择类别，并刷新页面 使下面内容是相关类别的问题
     * **/
    @ResponseBody
    @GetMapping("/refresh")
    public ModelAndView refresh(ModelAndView modelAndView,HttpServletRequest request,
                                Integer id){
        System.out.println("refresh page...");
        List<Category> categories =bookMapper.selectAllCategory();
        modelAndView.addObject("cate", categories);
        List<Book> books = bookMapper.cateBooks(id);
        modelAndView.addObject("lst", books);
        modelAndView.setViewName("home/index");
        return modelAndView;
    }

//    /**
//     *查看图书商品详情
//     * **/
//    @ResponseBody
//    @RequestMapping("/detail")
//    public ModelAndView detail(ModelAndView modelAndView,Integer id,
//                               HttpServletRequest request){
//        boolean flag = true;
//        HttpSession session = request.getSession();
//        String uid = (String)session.getAttribute("uid");
//        if (uid == null){
//            flag = false;
//        }
//        Book book = bookMapper.selectById(id);
//        modelAndView.addObject("lst", book);
//        List<Category> categories =bookMapper.selectAllCategory();
//        modelAndView.addObject("cate", categories);
//        modelAndView.addObject("flag", flag);
//        modelAndView.setViewName("home/detail");
//
//        return modelAndView;
//    }



}
