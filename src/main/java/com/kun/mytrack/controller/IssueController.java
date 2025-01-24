package com.kun.mytrack.controller;

import com.kun.mytrack.service.IssueService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping("/issue/{id}")
    public String getIssueById(@PathVariable("id") String issueId, Model model, HttpSession session) {
        // 从会话中获取用户 ID
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            // 如果未找到用户 ID，处理错误，例如重定向到登录页面
            return "redirect:/login";
        }
        Map<String, Object> issue = issueService.getIssue(issueId);
        if (issue!= null) {
            model.addAttribute("issue", issue);
            session.setAttribute("issue", issue);
            return "project-detail.html";
        } else {
            // 处理项目不存在的情况，例如返回错误页面或消息
            // Handle the case where the project does not exist, such as returning an error page or message
            return "project-not-found.html";
        }
    }
}
