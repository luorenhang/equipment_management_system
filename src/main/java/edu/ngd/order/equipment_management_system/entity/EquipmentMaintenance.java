package edu.ngd.order.equipment_management_system.entity;

import lombok.Data;
import java.util.Date;

/**
 * 设备保养实体类
 */
@Data
public class EquipmentMaintenance {
    /**
     * 保养ID
     */
    private Long id;
    
    /**
     * 设备ID
     */
    private Long equipmentId;
    
    /**
     * 保养日期
     */
    private Date maintenanceDate;
    
    /**
     * 保养类型
     */
    private String maintenanceType;
    
    /**
     * 保养项目
     */
    private String maintenanceItems;
    
    /**
     * 保养人
     */
    private String maintenancePerson;
    
    /**
     * 保养结果
     */
    private String maintenanceResult;
    
    /**
     * 下次保养日期
     */
    private Date nextMaintenanceDate;
    
    /**
     * 保养描述
     */
    private String description;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
}