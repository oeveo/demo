package com.springboot.demo.entity;

import lombok.Data;

@Data
public class Community {
    private Integer id;

    private String cityCode;

    private String name;

    private String cityName;
}