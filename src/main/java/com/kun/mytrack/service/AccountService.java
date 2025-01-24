package com.kun.mytrack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccountService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean  updateAccount(Map<String, String> params, int userId) {
        try {
            // 实现更新账户的逻辑，例如更新昵称和邮箱等信息
            // Implement the logic to update the account, such as updating nickname and email information
            String nickname = params.get("nickname");
            String email = params.get("email");
            String sql = "UPDATE user SET nickname =?, email =? WHERE id =?";
            int rowsUpdated = jdbcTemplate.update(sql, nickname, email, userId);
            return rowsUpdated > 0;
        } catch (Exception e) {
            // 可以根据需要记录异常信息，例如使用日志记录
            // You can log the exception information if necessary
            e.printStackTrace();
            return false;
        }
    }

    public Map<String, Object> getUserById(int userId) {
        String sql = "SELECT nickname, email FROM user WHERE id =?";
        return jdbcTemplate.queryForMap(sql, userId);
    }
}