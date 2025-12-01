package edu.ngd.order.equipment_management_system.entity;

import lombok.Data;
import java.util.Date;

/**
 * 设备点检实体类
 */
@Data
public class EquipmentInspection {
    /**
     * 点检ID
     */
    private Long id;
    
    /**
     * 设备ID
     */
    private Long equipmentId;
    
    /**
     * 点检日期
     */
    private Date inspectionDate;
    
    /**
     * 点检人
     */
    private String inspector;
    
    /**
     * 点检项目
     */
    private String inspectionItems;
    
    /**
     * 点检结果
     */
    private String inspectionResult;
    
    /**
     * 问题描述
     */
    private String problemDescription;
    
    /**
     * 处理建议
     */
    private String handlingSuggestion;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
}