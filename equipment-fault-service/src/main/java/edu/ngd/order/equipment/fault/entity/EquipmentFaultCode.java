package edu.ngd.order.equipment.fault.entity;

import lombok.Data;

/**
 * 设备故障代码实体类
 */
@Data
public class EquipmentFaultCode {
    /**
     * 主键
     */
    private Integer id;
    
    /**
     * 故障描述
     */
    private String codeDescribe;
    
    /**
     * 状态
     */
    private Integer status;
    
    /**
     * 故障代码
     */
    private String code;
    
    /**
     * 备注
     */
    private String codeRemark;
}