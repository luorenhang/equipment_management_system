package edu.ngd.order.equipment.service;

import edu.ngd.order.equipment.entity.User;
import edu.ngd.order.equipment.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 用户服务类
 */
@Service
public class UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    // 密码加密器
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    /**
     * 用户注册
     * @param user 用户实体
     * @return 注册结果
     */
    public boolean register(User user) {
        // 检查用户名是否已存在
        User existingUser = userMapper.selectByUsername(user.getUsername());
        if (existingUser != null) {
            return false;
        }
        
        // 检查邮箱是否已存在
        existingUser = userMapper.selectByEmail(user.getEmail());
        if (existingUser != null) {
            return false;
        }
        
        // 密码加密
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        
        // 设置创建人和修改人
        user.setCreator(user.getUsername());
        user.setModifier(user.getUsername());
        
        // 执行注册
        return userMapper.insert(user) > 0;
    }
    
    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登录成功返回用户实体，否则返回null
     */
    public User login(String username, String password) {
        // 根据用户名查询用户
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            return null;
        }
        
        // 验证密码
        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        
        return null;
    }
    
    /**
     * 根据ID查询用户
     * @param id 用户ID
     * @return 用户实体
     */
    public User getUserById(Integer id) {
        return userMapper.selectById(id);
    }
    
    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户实体
     */
    public User getUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }
    
    /**
     * 更新用户信息
     * @param user 用户实体
     * @return 更新结果
     */
    public boolean updateUser(User user) {
        return userMapper.update(user) > 0;
    }
    
    /**
     * 删除用户
     * @param id 用户ID
     * @return 删除结果
     */
    public boolean deleteUser(Integer id) {
        return userMapper.deleteById(id) > 0;
    }
}