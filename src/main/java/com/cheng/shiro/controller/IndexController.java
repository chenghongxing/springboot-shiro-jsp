package com.cheng.shiro.controller;

import com.cheng.shiro.entity.User;
import com.cheng.shiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by chenghx on 2020/12/4 15:12
 */
@Controller
@RequestMapping("user")
public class IndexController {
    @Autowired
    private UserService userService;

    @PostMapping("register")
    public String register(User user){
        try {
            userService.register(user);
            return "redirect:/login.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/register.jsp";
        }
    }

    @RequestMapping("login")
    public String login(String username,String password){
       Subject subject = SecurityUtils.getSubject();//获取主题对象
        try {
            subject.login(new UsernamePasswordToken(username,password));
            return "redirect:/index.jsp";
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("用户名错误！");
        }catch (IncorrectCredentialsException e){
            e.printStackTrace();
            System.out.println("密码错误！");
        }
        return "redirect:/login.jsp";
    }

    @RequestMapping("logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login.jsp";
    }


}
