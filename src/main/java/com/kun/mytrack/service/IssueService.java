package com.kun.mytrack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class IssueService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Map<String, Object> getIssue(String issueId) {
        String sql = "SELECT id, project_id, title, description, created_at, created_by, updated_at, updated_by, " +
                "type, status, target_date, assigned_to FROM issue WHERE id =?";
        try {
            return jdbcTemplate.queryForMap(sql, issueId);
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            // 当没有查询到结果时，返回 null 或抛出异常，根据实际需求处理
            // Return null or throw an exception when no result is found, handle according to actual requirements
            return null;
        }
    }
}
