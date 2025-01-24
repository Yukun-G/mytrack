package com.kun.mytrack.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String showHomePage(Model model, HttpSession session) {
        // 从会话中获取用户名
        String username = (String) session.getAttribute("username");
        if (username == null) {
            // 如果未登录，重定向到登录页面
            return "redirect:/login";
        }
        // 将用户名添加到会话中
        session.setAttribute("username", username);
        return "home.html";
    }
}