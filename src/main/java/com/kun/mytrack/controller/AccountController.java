package com.kun.mytrack.controller;

import com.kun.mytrack.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/updateAccount")
    public String updateAccount(@RequestParam Map<String, String> params, RedirectAttributes redirectAttributes, HttpSession session) {
        // 从 HttpSession 中获取用户 ID
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            // 如果未找到用户 ID，处理错误，例如重定向到登录页面或返回错误消息
            return "redirect:/login";
        }
        boolean success = accountService.updateAccount(params, userId);
        if (success) {
            redirectAttributes.addFlashAttribute("message", "账号信息更新成功");
        } else {
            redirectAttributes.addFlashAttribute("error", "账号信息更新失败，请稍后重试");
        }
        return "redirect:/myaccount";
    }

    @GetMapping("/myaccount")
    public String showAccount(Model model, HttpSession session) {
        // 从 HttpSession 中获取用户 ID
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            // 如果未找到用户 ID，处理错误，例如重定向到登录页面或返回错误消息
            return "redirect:/login";
        }
        // 调用 AccountService 中的方法获取用户信息，假设该方法为 getUserById
        Map<String, Object> userInfo = accountService.getUserById(userId);
        // 将用户信息添加到模型中
        model.addAttribute("userInfo", userInfo);
        return "myaccount";
    }
}