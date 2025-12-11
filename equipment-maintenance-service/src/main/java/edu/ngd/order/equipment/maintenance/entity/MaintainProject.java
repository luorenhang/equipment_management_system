package edu.ngd.order.equipment.maintenance.entity;

import lombok.Data;

/**
 * 设备保养项目实体类
 */
@Data
public class MaintainProject {
    /**
     * 主键
     */
    private Integer id;
    
    /**
     * 保养标准
     */
    private String maintainStandard;
    
    /**
     * 保养项目
     */
    private String maintainProject;
    
    /**
     * 保养结果
     */
    private String maintainResult;
    
    /**
     * 保养内容
     */
    private String maintainContent;
    
    /**
     * 原因
     */
    private String reason;
    
    /**
     * 保养ID
     */
    private Integer maintainId;
    
    /**
     * 备注
     */
    private String remark;
}