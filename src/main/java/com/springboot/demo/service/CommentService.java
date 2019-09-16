package com.springboot.demo.service;

import com.springboot.demo.entity.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CommentService {

    List<Comment> findByHouseId(Integer id);
}
