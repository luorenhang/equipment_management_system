package edu.ngd.order.equipment_management_system.entity;

import lombok.Data;
import java.util.Date;

/**
 * 设备维修实体类
 */
@Data
public class EquipmentRepair {
    /**
     * 维修ID
     */
    private Long id;
    
    /**
     * 设备ID
     */
    private Long equipmentId;
    
    /**
     * 故障代码ID
     */
    private Long faultCodeId;
    
    /**
     * 维修日期
     */
    private Date repairDate;
    
    /**
     * 维修人
     */
    private String repairPerson;
    
    /**
     * 维修项目
     */
    private String repairItems;
    
    /**
     * 维修结果
     */
    private String repairResult;
    
    /**
     * 维修费用
     */
    private Double repairCost;
    
    /**
     * 维修描述
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