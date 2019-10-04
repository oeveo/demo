package com.springboot.demo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springboot.demo.entity.Comment;
import com.springboot.demo.entity.House;
import com.springboot.demo.entity.User;
import com.springboot.demo.service.CommentService;
import com.springboot.demo.service.HouseSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class HouseController {

    @Autowired
    private HouseSevice houseSevice;
    @Autowired
    private CommentService commentService;

    /**
     * 房产列表
     * @return
     */
    @RequestMapping("/house/list")
    public String houseList(){
        return "/house/listing";
    }

    /**
     * 添加房产界面
     * @param model
     * @return
     */
    @RequestMapping("/house/toAdd")
    public String addHouse(Model model){
        model.addAttribute("communitys",houseSevice.getAllCommunitys());
        return "/house/addHouse";
    }

    /**
     * 添加房产
     * @param house
     * @param files
     * @return
     */
    @RequestMapping("/house/add")
    public String doAddHouse(House house, MultipartFile [] files, Model model){
        String properties="";
        for (String string:house.getFeatureList()) {
            properties=properties+string+",";
        }
        house.setProperties(properties);
        house.setFeatureList(null);
        house.setCreateTime(new Date());

        List<String> imgPath=new ArrayList<>();
        // 文件上传路径
        String filePath="F:/IDEAworkspace/Real estate sales/demo/src/main/resources/static/static/imgs/house/";
        String errorMsg="";
        for(MultipartFile file:files){
            // 0.上传图片到服务器
            if(file.isEmpty()){
                errorMsg="文件为空...";
                model.addAttribute("errorMsg",errorMsg);
            }
            String fileName=file.getOriginalFilename();
            imgPath.add(fileName);
            File dest=new File(filePath+fileName);
            // 检查是否存在根目录，不存在，则创建文件
            if(!dest.getParentFile().exists()){
                dest.getParentFile().mkdirs();
            }
            try {
                file.transferTo(dest);
            }catch (IllegalStateException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        house.setImages(imgPath.get(0));
        house.setFloorPlan(imgPath.get(1));
        house.setState(1);
        houseSevice.addHouse(house);
        return "/house/addHouse";
//        return "redirect:/house/ownlist/1";
    }

    /**
     * 我的房产
     */
    @Cacheable("ownlist")
    @RequestMapping("/house/ownlist/{page}")
    public String ownlist(@PathVariable("page") int page,HttpServletRequest request, Model model){
        PageHelper.startPage(page,2);
        User user=(User)request.getSession().getAttribute("loginUser");
        List<House> houses=houseSevice.findOwnHouse(user.getId());
        PageInfo pageInfo=new PageInfo(houses);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("houses",houses);
        model.addAttribute("pageType","own");
        model.addAttribute("method","/house/ownlist/");
        return "/house/ownlist";
    }
    /**
     * 房产详情
     */
    @RequestMapping("house/detail")
    public String houseDetial(Integer id,Model model){
        House house=houseSevice.findById(id);
        List<String> featureList= Arrays.asList(house.getProperties().split(","));
        List<Comment> comments=commentService.findByHouseId(id);
        model.addAttribute("house",house);
        model.addAttribute("featureList",featureList);
        model.addAttribute("comments",comments);
        return "/house/detail";
    }
    /**
     * 删除房产
     */

    /**
     * 我的收藏
     */
}
