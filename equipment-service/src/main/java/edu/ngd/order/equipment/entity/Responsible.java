package edu.ngd.order.equipment.entity;

import lombok.Data;

/**
 * 设备责任人实体类
 */
@Data
public class Responsible {
    /**
     * 主键
     */
    private Integer id;
    
    /**
     * 责任人分类
     */
    private String responsibleType;
    
    /**
     * 备注
     */
    private String notes;
    
    /**
     * 责任人
     */
    private String responsiblePerson;
    
    /**
     * 设备台账ID
     */
    private Integer equipmentLedger;
    
    /**
     * 姓名（前端字段映射，非数据库字段）
     */
    private String name;
    
    /**
     * 职位（前端字段映射，非数据库字段）
     */
    private String position;
    
    /**
     * 联系方式（前端字段映射，非数据库字段）
     */
    private String contact;
}