package com.school.bookshop.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Ordr {
    private Integer id;
    private Integer bid;
    private Integer uid;
    private Date time;
    private String strDateTime;
    private String count;
    private Book book;
    private User user;
    private Integer totalPrice;
    private Integer flag;
}
