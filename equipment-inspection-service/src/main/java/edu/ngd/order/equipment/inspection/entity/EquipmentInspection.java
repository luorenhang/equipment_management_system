package edu.ngd.order.equipment.inspection.entity;

import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 * 设备点检实体类
 */
@Data
public class EquipmentInspection {
    /**
     * 主键
     */
    private Integer id;
    
    /**
     * 点检编号
     */
    private String checkNumber;
    
    /**
     * 计划结束时间
     */
    private Date endTime;
    
    /**
     * 计划开始时间
     */
    private Date startTime;
    
    /**
     * 实际开始时间
     */
    private Date operateStartTime;
    
    /**
     * 实际结束时间
     */
    private Date operateEndTime;
    
    /**
     * 设备名称
     */
    private String equipmentName;
    
    /**
     * 设备编号
     */
    private String equipmentNumber;
    
    /**
     * 设备类型
     */
    private String deviceType;
    
    /**
     * 点检结果
     */
    private String checkResult;
    
    /**
     * 点检人
     */
    private String checkUser;
    
    /**
     * 启用状态
     */
    private String enableStatus;
    
    /**
     * 条码
     */
    private String barCode;
    
    /**
     * 状态
     */
    private String status;
    
    /**
     * 点检项目列表（关联关系，非数据库字段）
     */
    private List<CheckProject> checkProjectList;
}