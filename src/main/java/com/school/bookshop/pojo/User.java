package com.school.bookshop.pojo;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String address;
    private String tell;
}
