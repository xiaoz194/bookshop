package com.school.bookshop.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Book implements Serializable {
    private Integer id;
    private String bookName;
    private String author;
    private String dsc;
    private String pic;
    private Integer money;
    private Integer cid;
    private Integer collected;
    private Category category;
}
