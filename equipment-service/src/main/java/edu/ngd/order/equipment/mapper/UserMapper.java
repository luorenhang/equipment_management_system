package edu.ngd.order.equipment.mapper;

import edu.ngd.order.equipment.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户Mapper接口
 */
@Mapper
public interface UserMapper {
    /**
     * 新增用户
     * @param user 用户实体
     * @return 影响行数
     */
    int insert(User user);
    
    /**
     * 根据ID删除用户
     * @param id 用户ID
     * @return 影响行数
     */
    int deleteById(Integer id);
    
    /**
     * 更新用户信息
     * @param user 用户实体
     * @return 影响行数
     */
    int update(User user);
    
    /**
     * 根据ID查询用户
     * @param id 用户ID
     * @return 用户实体
     */
    User selectById(Integer id);
    
    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户实体
     */
    User selectByUsername(@Param("username") String username);
    
    /**
     * 根据邮箱查询用户
     * @param email 邮箱
     * @return 用户实体
     */
    User selectByEmail(@Param("email") String email);
    
    /**
     * 查询所有用户
     * @return 用户列表
     */
    List<User> selectAll();
}