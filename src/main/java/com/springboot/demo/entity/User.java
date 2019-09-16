package com.springboot.demo.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Integer id;

    private String name;

    private String phone;

    private String email;

    private String aboutme;

    private String passwd;

    private String avatar;

    private Integer type;

    private Date createTime;

    private Integer enable;

    private Integer agencyId;
}