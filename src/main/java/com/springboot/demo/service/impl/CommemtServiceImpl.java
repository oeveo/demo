package com.springboot.demo.service.impl;

import com.springboot.demo.entity.Comment;
import com.springboot.demo.entity.CommentExample;
import com.springboot.demo.mapper.CommentMapper;
import com.springboot.demo.mapper.CommunityMapper;
import com.springboot.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommemtServiceImpl implements CommentService{

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> findByHouseId(Integer id) {
        CommentExample commentExample=new CommentExample();
        CommentExample.Criteria criteria=commentExample.createCriteria();
        criteria.andHouseIdEqualTo(id);
        return commentMapper.selectByExample(commentExample);
    }
}
