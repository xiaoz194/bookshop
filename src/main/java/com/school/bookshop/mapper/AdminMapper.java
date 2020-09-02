package com.school.bookshop.mapper;

import com.school.bookshop.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {
    Integer doLogin(Admin admin);
}
