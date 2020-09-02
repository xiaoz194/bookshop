package com.school.bookshop.mapper;

import com.school.bookshop.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> selectAllUsers();
    User doLogin(User user);

    void deleteUserById(@Param("id") Integer id);

    void regist(User user);

    Integer selectByNamePwd(User user);

    User selectByUid(@Param("uid")Integer uid);

    void update(User user); //更新的个人信息
}
