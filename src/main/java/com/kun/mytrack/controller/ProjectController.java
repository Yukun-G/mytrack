package com.kun.mytrack.controller;

import com.kun.mytrack.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/projects")
    public String showProjects(
            @RequestParam(required = false) String projectName,
            @RequestParam(required = false) String projectDescription,
            @RequestParam(required = false) String projectStatus,
            Model model,
            HttpSession session) {
        // 从会话中获取用户 ID
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            // 如果未找到用户 ID，处理错误，例如重定向到登录页面
            return "redirect:/login";
        }
        // 调用 ProjectService 中的方法获取项目列表，默认查询开启状态的项目
        if (projectStatus == null) {
            projectStatus = "1";
        }
        List<Map<String, Object>> projects = projectService.getProjects(userId, projectName, projectDescription, projectStatus);
        model.addAttribute("projects", projects);
        return "projects.html";
    }

    @GetMapping("/project/{name}")
    public String getProjectByName(@PathVariable("name") String projectName, Model model, HttpSession session) {
        // 从会话中获取用户 ID
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            // 如果未找到用户 ID，处理错误，例如重定向到登录页面
            return "redirect:/login";
        }
        Map<String, Object> project = projectService.getProject(projectName);
        if (project!= null) {
            model.addAttribute("project", project);
            session.setAttribute("project", project);
            return "project-detail.html";
        } else {
            // 处理项目不存在的情况，例如返回错误页面或消息
            // Handle the case where the project does not exist, such as returning an error page or message
            return "project-not-found.html";
        }
    }

    @GetMapping("/project/overview")
    public String show_overview(Model model) {
        // 可以在这里添加模型数据，比如从数据库获取的数据
        // model.addAttribute("project", projectService.getProject());
        return "overview";
    }

    @GetMapping("/project/activity")
    public String show_activity(Model model) {
        // 可以在这里添加模型数据，比如从数据库获取的数据
        // model.addAttribute("project", projectService.getProject());
        return "activity";
    }

    @GetMapping("/project/issues.html")
    public String show_issues_page(@RequestParam(required = false) String status,
                                  @RequestParam(required = false) String title,
                                  @RequestParam(required = false) String createdBy,
                                  @RequestParam(required = false) String assignedTo,
                                  Model model,
                                  HttpSession session) {
        // 从会话中获取用户 ID
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            // 如果未找到用户 ID，处理错误，例如重定向到登录页面
            return "redirect:/login";
        }
        if (status == null) {
            status = "1";
        }
        List<Map<String, Object>> issues = projectService.getIssues(userId, status, title, createdBy, assignedTo);
        // 可以在这里添加模型数据，比如从数据库获取的数据
        model.addAttribute("issues", issues);
        return "issues";
    }

    @GetMapping("/project/issues")
    public String show_issues(@RequestParam(required = false) String status,
                              @RequestParam(required = false) String title,
                              @RequestParam(required = false) String createdBy,
                              @RequestParam(required = false) String assignedTo,
                              Model model,
                              HttpSession session) {
        // 从会话中获取用户 ID
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            // 如果未找到用户 ID，处理错误，例如重定向到登录页面
            return "redirect:/login";
        }
        if (status == null) {
            status = "1";
        }
        List<Map<String, Object>> issues = projectService.getIssues(userId, status, title, createdBy, assignedTo);
        // 可以在这里添加模型数据，比如从数据库获取的数据
        model.addAttribute("issues", issues);
        return "fragments/issuesTable :: issuesTableBody"; // 返回Thymeleaf片段
    }

    @GetMapping("/project/resource")
    public String show_resource(Model model) {
        // 可以在这里添加模型数据，比如从数据库获取的数据
        // model.addAttribute("project", projectService.getProject());
        return "resource";
    }

    @GetMapping("/project/configuration")
    public String show_configuration(Model model) {
        // 可以在这里添加模型数据，比如从数据库获取的数据
        // model.addAttribute("project", projectService.getProject());
        return "configuration";
    }

}