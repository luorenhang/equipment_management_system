package edu.ngd.order.equipment_management_system.demos.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据库连接测试控制器
 */
@RestController
public class DatabaseController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 测试数据库连接
     * @return 连接状态信息
     */
    @RequestMapping("/test-db-connection")
    public Map<String, Object> testDatabaseConnection() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 获取数据库连接
            Connection connection = jdbcTemplate.getDataSource().getConnection();
            
            if (connection != null && !connection.isClosed()) {
                // 测试查询
                String testSql = "SELECT 1 as test_result";
                Integer testResult = jdbcTemplate.queryForObject(testSql, Integer.class);
                
                result.put("status", "success");
                result.put("message", "数据库连接成功！");
                result.put("test_result", testResult);
                result.put("database_product", connection.getMetaData().getDatabaseProductName());
                result.put("database_version", connection.getMetaData().getDatabaseProductVersion());
                
                // 关闭连接
                connection.close();
            } else {
                result.put("status", "error");
                result.put("message", "数据库连接失败：连接为空或已关闭");
            }
        } catch (SQLException e) {
            result.put("status", "error");
            result.put("message", "数据库连接失败：" + e.getMessage());
            result.put("exception", e.getClass().getName());
        }
        
        return result;
    }
}