package edu.ngd.order.equipment.maintenance.entity;

import lombok.Data;

/**
 * 保养项目实体类
 */
@Data
public class MaintenanceItem {
    /**
     * 主键
     */
    private Integer id;
    
    /**
     * 保养记录ID
     */
    private Integer maintenanceId;
    
    /**
     * 保养项目
     */
    private String maintenanceItem;
    
    /**
     * 保养内容
     */
    private String maintenanceContent;
    
    /**
     * 保养结果
     */
    private String maintenanceResult;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 状态
     */
    private Integer status;
}