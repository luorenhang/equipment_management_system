package edu.ngd.order.common.entity;

import lombok.Data;
import java.util.Date;

/**
 * 基础对象实体类
 */
@Data
public class BasicObject {
    /**
     * 主键
     */
    private Integer id;
    
    /**
     * 租户
     */
    private Integer tenant;
    
    /**
     * 系统版本
     */
    private Integer rdmVersion;
    
    /**
     * 实体
     */
    private String rdmExtensionType;
    
    /**
     * 类名
     */
    private String className;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 创建人
     */
    private String creator;
    
    /**
     * 最后更新时间
     */
    private Date lastUpdateTime;
    
    /**
     * 更新人
     */
    private String modifier;
}