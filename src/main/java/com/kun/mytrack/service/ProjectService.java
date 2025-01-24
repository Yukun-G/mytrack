package com.kun.mytrack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class ProjectService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getProjects(int userId, String projectName, String projectDescription, String projectStatus) {
        String sql = "SELECT name, description, created_at FROM project WHERE status=" + projectStatus;
        if(projectName != null){
            sql += " AND name LIKE '%"+projectName+"%' ";
        }
        if(projectDescription != null){
            sql += " AND description LIKE '%"+projectDescription+"%' ";
        }

        return jdbcTemplate.queryForList(sql);
    }

    public Map<String, Object> getProject(String projectName) {
        String sql = "SELECT id, name, description, created_at FROM project WHERE name =?";
        try {
            return jdbcTemplate.queryForMap(sql, projectName);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            // 当没有查询到结果时，返回 null 或抛出异常，根据实际需求处理
            // Return null or throw an exception when no result is found, handle according to actual requirements
            return null;
        }
    }

    public List<Map<String, Object>> getIssues(int userId, String status, String title, String createdBy, String assignedTo) {
        String sql = "SELECT id, status, title, description,created_by,assigned_to, created_at, updated_at FROM issue WHERE status=" + status;
        if(title != null){
            sql += " AND title LIKE '%"+title+"%' ";
        }

        return jdbcTemplate.queryForList(sql);
    }
}