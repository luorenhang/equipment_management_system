package edu.ngd.order.equipment.entity;

import lombok.Data;

/**
 * 设备附件实体类
 */
@Data
public class Annex {
    /**
     * 主键
     */
    private Integer id;
    
    /**
     * 附件名称
     */
    private String annexName;
    
    /**
     * 备注
     */
    private String notes;
    
    /**
     * 附件说明
     */
    private String annexRemarks;
    
    /**
     * 设备台账ID
     */
    private Integer equipmentLedger;
    
    /**
     * 附件地址
     */
    private String annexAddress;
}