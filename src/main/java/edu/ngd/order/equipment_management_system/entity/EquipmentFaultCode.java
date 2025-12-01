package edu.ngd.order.equipment_management_system.entity;

import lombok.Data;
import java.util.Date;

/**
 * 设备故障代码实体类
 */
@Data
public class EquipmentFaultCode {
    /**
     * 故障代码ID
     */
    private Long id;
    
    /**
     * 故障代码
     */
    private String faultCode;
    
    /**
     * 故障名称
     */
    private String faultName;
    
    /**
     * 故障类型
     */
    private String faultType;
    
    /**
     * 故障描述
     */
    private String faultDescription;
    
    /**
     * 处理方法
     */
    private String handlingMethod;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
}