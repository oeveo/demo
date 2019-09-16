package com.springboot.demo.controller;

import com.springboot.demo.entity.User;
import com.springboot.demo.service.MainService;
import com.springboot.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private MainService mainService;

    @Value("${urlstr}")
    private String urlstr;

    /**
     * 用户注册
     * @param account
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("accounts/register")
    public String accountsRegister(User account, HttpServletRequest request, Model
            model,MultipartFile file){
        if(account==null||account.getName()==null){
            model.addAttribute("errorMsg","");
            return "user/accounts/register";
        }else{
            String confirmPasswd=request.getParameter("confirmPasswd");
            if(account.getPasswd().equals(confirmPasswd)){
                String errorMsg="";

                // 0.上传图片到服务器
                if(file.isEmpty()){
                    errorMsg="文件为空...";
                    model.addAttribute("errorMsg",errorMsg);
                }
                // 获取文件名
                String fileName=file.getOriginalFilename();
                // 文件上传路径
                String filePath="F:/IDEAworkspace/Real estate " +
                        "sales/demo/src/main/resources/static/static/imgs/account/";
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
                account.setAvatar(fileName);
                account.setCreateTime(new Date());
                account.setEnable(2);

                // 1.插入用户信息
                userService.insert(account);

                // 2.发送邮件
                StringBuilder content = new StringBuilder();
                content.append("<h2>好房网：账户激活邮件</h2>")
                        .append("<p " +
                                "style='text-align:left'>激活链接：http://"+
                                urlstr+"activeUser?email="+account.getEmail()+"</p>")
                        .append("<p> 时间为："+ new Date()+"</p>");
                mainService.sendMail(account.getEmail(),"账户激活邮件",content.toString());

                // 3.跳转登录页面
                model.addAttribute("errorMsg","");
                model.addAttribute("email",account.getEmail());
                return "user/accounts/registerSubmit";
            }else{
                model.addAttribute("errorMsg","密码不一致，请重新输入信息！");
                return "user/accounts/register";
            }
        }
    }
    /**
     * 用户激活
     */
    @RequestMapping("/activeUser")
    public String activeUser(String email){
        userService.activeUser(email);
        return "user/accounts/signin";
    }

    /**
     * 用户登录
     */
    @RequestMapping("/accounts/signin")
    public String login(HttpServletRequest request,Model model){
        String email = request.getParameter("username");
        String passwd = request.getParameter("password");
        if(email==null||passwd==null){
            model.addAttribute("errorMsg","");
            return "user/accounts/signin";
        }
        User user=userService.login(email);
        if(user==null){
            model.addAttribute("errorMsg","用户不存在！");
            return "user/accounts/signin";
        }else{
            if(user.getPasswd().equals(passwd)){
                model.addAttribute("errorMsg","");
                HttpSession session=request.getSession();
                session.setAttribute("loginUser",user);
                return "user/accounts/profile";
            }else{
                model.addAttribute("errorMsg","密码错误！");
                return "user/accounts/signin";
            }
        }
    }

    /**
     * 用户退出
     */
    @RequestMapping("accounts/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.invalidate();
        return "redirect:/index";
    }

    /**
     * 个人信息
     */
    @RequestMapping("/accounts/profile")
    public String userInfo(HttpServletRequest request,User user,Model model){
        if(user.getEmail()==null){
            return "/user/accounts/profile";
        }
        else{
            userService.updateUser(user);
            request.getSession().setAttribute("loginUser",userService.login(user.getEmail()));
            return "/user/accounts/profile";
        }
    }

    /**
     * 修改密码
     */
    @RequestMapping("accounts/changePassword")
    public String changePassword(String email, String password, String newPassword,
                                 String confirmPassword,Model model,HttpServletRequest request){
        User user=userService.login(email);
        if(user!=null){
            if(user.getPasswd().equals(password)){
                if(newPassword.equals(confirmPassword)){
                    model.addAttribute("errorMsg","");
                    user.setPasswd(newPassword);
                    userService.updateUser(user);
                    request.getSession().setAttribute("loginUser",userService.login(user.getEmail()));
                    model.addAttribute("errorMsg","新密码不一致！");
                    return "/user/accounts/profile";
                }else{
                    model.addAttribute("errorMsg","新密码不一致！");
                    return "/user/accounts/profile";
                }
            }else{
                if(newPassword!=confirmPassword){
                    model.addAttribute("errorMsg","旧密码错误，新密码不一致！");
                    return "/user/accounts/profile";
                }else{
                    model.addAttribute("errorMsg","旧密码错误！");
                    return "/user/accounts/profile";
                }
            }
        }else{
            model.addAttribute("errorMsg","用户不存在！");
            return "/user/accounts/profile";
        }
    }

}
