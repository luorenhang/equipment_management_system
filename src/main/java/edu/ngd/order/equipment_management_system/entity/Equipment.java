package edu.ngd.order.equipment_management_system.entity;

import lombok.Data;
import java.util.Date;

/**
 * 设备台账实体类
 */
@Data
public class Equipment {
    /**
     * 设备ID
     */
    private Long id;
    
    /**
     * 设备编码
     */
    private String equipmentCode;
    
    /**
     * 设备名称
     */
    private String equipmentName;
    
    /**
     * 设备类型
     */
    private String equipmentType;
    
    /**
     * 设备型号
     */
    private String model;
    
    /**
     * 制造商
     */
    private String manufacturer;
    
    /**
     * 购买日期
     */
    private Date purchaseDate;
    
    /**
     * 使用寿命（月）
     */
    private Integer serviceLife;
    
    /**
     * 设备状态
     */
    private String status;
    
    /**
     * 责任人
     */
    private String responsiblePerson;
    
    /**
     * 存放位置
     */
    private String location;
    
    /**
     * 设备描述
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