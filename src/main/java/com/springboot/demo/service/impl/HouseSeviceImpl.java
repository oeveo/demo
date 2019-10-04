package com.springboot.demo.service.impl;

import com.springboot.demo.entity.Community;
import com.springboot.demo.entity.House;
import com.springboot.demo.entity.HouseExample;
import com.springboot.demo.mapper.CommunityMapper;
import com.springboot.demo.mapper.HouseMapper;
import com.springboot.demo.service.HouseSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseSeviceImpl implements HouseSevice {

    @Autowired
    private CommunityMapper communityMapper;
    @Autowired
    private HouseMapper houseMapper;

    @Override
    public List<Community> getAllCommunitys() {
        return communityMapper.getAllCommunitys();
    }

    @Override
    public void addHouse(House house) {
        houseMapper.insert(house);
    }

    @Override
    public List<House> findOwnHouse(Integer id) {
        HouseExample houseExample=new HouseExample();
        HouseExample.Criteria criteria=houseExample.createCriteria();
        criteria.andUserIdEqualTo(id);
        return houseMapper.selectByExample(houseExample);
    }

    @Override
    public House findById(Integer id) {
        return houseMapper.selectByPrimaryKey(id);
    }
}
