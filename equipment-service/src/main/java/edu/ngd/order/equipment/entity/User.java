package edu.ngd.order.equipment.entity;

import edu.ngd.order.common.entity.BasicObject;
import lombok.Data;

/**
 * 用户实体类
 */
@Data
public class User extends BasicObject {
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 邮箱
     */
    private String email;
}
