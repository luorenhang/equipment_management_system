package edu.ngd.order.equipment.inspection.entity;

import lombok.Data;

/**
 * 设备点检项目实体类
 */
@Data
public class CheckProject {
    /**
     * 主键
     */
    private Integer id;
    
    /**
     * 点检ID
     */
    private Integer checkId;
    
    /**
     * 原因
     */
    private String reason;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 状态
     */
    private Integer status;
    
    /**
     * 点检项目
     */
    private String checkProject;
    
    /**
     * 点检内容
     */
    private String checkContent;
    
    /**
     * 点检结果
     */
    private String checkResult;
    
    /**
     * 点检标准
     */
    private String checkStandard;
}