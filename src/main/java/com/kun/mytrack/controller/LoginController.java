package com.kun.mytrack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 处理 GET 请求，显示登录页面
    @GetMapping("/login")
    public String showLoginPage() {
        return "login.html";
    }

    // 处理 POST 请求，处理登录逻辑
    @PostMapping("/login")
    public String login(
            @RequestParam String name,
            @RequestParam String password,
            Model model,
            HttpSession session) {
        // 编写 SQL 查询语句，根据用户输入的 name 查找用户信息
        String sql = "SELECT id, password FROM user WHERE name =?";
        try {
            // 使用 BeanPropertyRowMapper 将结果映射到 User 对象
            User user = jdbcTemplate.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<>(User.class),
                    name);

            // 将用户输入的密码与数据库中查询到的密码进行比较
            if (user!= null && user.getPassword().equals(password)) {
                // 登录成功，将用户信息存储在会话中
                session.setAttribute("username", name);
                session.setAttribute("userId", user.getId());
                // 重定向到主页或其他页面
                return "redirect:/home";
            } else {
                // 登录失败，返回登录页面并添加错误消息
                model.addAttribute("error", "用户名或密码错误");
                return "login.html";
            }
        } catch (Exception e) {
            // 处理异常，可能是用户不存在等情况
            model.addAttribute("error", "用户名或密码错误");
            return "login.html";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 清除 session 中的用户信息
        session.removeAttribute("userId");
        session.removeAttribute("username");
        // 可以添加更多需要清除的 session 属性
        // You can add more session attributes that need to be cleared
        // 重定向到登录页面
        return "redirect:/login";
    }

    // 内部类，用于存储用户信息
    private static class User {
        private int id;
        private String password;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}