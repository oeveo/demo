package com.springboot.demo.service;

import com.springboot.demo.entity.Community;
import com.springboot.demo.entity.House;

import java.util.List;

public interface HouseSevice {

    List<Community> getAllCommunitys();

    void addHouse(House house);

    List<House> findOwnHouse(Integer id);

    House findById(Integer id);
}
