package com.school.bookshop.mapper;

import com.school.bookshop.pojo.Ordr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {


    List<Ordr> selectFlag(@Param("uid")Integer uid);//根据标记为查询
    List<Ordr> selectAllOrders();
    void updateFlag(@Param("id")Integer id);
    void addOrdr(Ordr ordr);
    List<Ordr> selectByUid(@Param("uid")Integer uid);
    void delOrdr(Integer id);
    Ordr selectByid(@Param("id")Integer id);
    Ordr selectBybId(@Param("bid")Integer bid);
}
