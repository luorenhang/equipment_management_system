package edu.ngd.order.equipment.inspection.entity;

import lombok.Data;

/**
 * 设备点检设备关联实体类
 */
@Data
public class CheckEquipment {
    /**
     * 主键
     */
    private Integer id;
    
    /**
     * 点检类型
     */
    private String checkType;
    
    /**
     * 点检内容
     */
    private String checkContent;
    
    /**
     * 设备ID
     */
    private Integer equipmentId;
    
    /**
     * 点检项目
     */
    private String checkProject;
    
    /**
     * 点检标准
     */
    private String checkStandard;
    
    /**
     * 备注
     */
    private String remark;
}