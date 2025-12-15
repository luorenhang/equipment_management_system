package edu.ngd.order.equipment.controller;

import edu.ngd.order.equipment.entity.User;
import edu.ngd.order.equipment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    /**
     * 用户注册
     * @param user 用户实体
     * @return 注册结果
     */
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();
        
        boolean success = userService.register(user);
        if (success) {
            result.put("code", 200);
            result.put("message", "注册成功");
        } else {
            result.put("code", 500);
            result.put("message", "用户名或邮箱已存在");
        }
        
        return result;
    }
    
    /**
     * 用户登录
     * @param loginInfo 登录信息，包含用户名和密码
     * @return 登录结果
     */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> loginInfo) {
        Map<String, Object> result = new HashMap<>();
        
        String username = loginInfo.get("username");
        String password = loginInfo.get("password");
        
        User user = userService.login(username, password);
        if (user != null) {
            result.put("code", 200);
            result.put("message", "登录成功");
            result.put("data", user);
        } else {
            result.put("code", 500);
            result.put("message", "用户名或密码错误");
        }
        
        return result;
    }
    
    /**
     * 获取用户信息
     * @param id 用户ID
     * @return 用户信息
     */
    @GetMapping("/info/{id}")
    public Map<String, Object> getUserInfo(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        
        User user = userService.getUserById(id);
        if (user != null) {
            // 移除密码等敏感信息
            user.setPassword(null);
            result.put("code", 200);
            result.put("data", user);
        } else {
            result.put("code", 500);
            result.put("message", "用户不存在");
        }
        
        return result;
    }
    
    /**
     * 退出登录
     * @return 退出结果
     */
    @PostMapping("/logout")
    public Map<String, Object> logout() {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "退出成功");
        return result;
    }
}