package com.school.bookshop.mapper;

import com.school.bookshop.pojo.Book;
import com.school.bookshop.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookMapper {
    List<Category> selectAllCategory();
    void deleteCategory(@Param("id") Integer id);
    void addCategory(Category category);
    List<Book> selectAllBooks();
    void deleteBookById(@Param("id") Integer id);
    void addBook(Book book);

    List<Book> cateBooks(@Param("id")Integer id);

    Book selectById(@Param("id") Integer id);
    Book selectNameToGetId(@Param("bookName")String bookName);
}
