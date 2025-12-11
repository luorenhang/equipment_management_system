package edu.ngd.order.equipment.maintenance.entity;

import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 * 设备保养实体类
 */
@Data
public class EquipmentMaintenance {
    /**
     * 主键
     */
    private Integer id;
    
    /**
     * 保养编号
     */
    private String maintainNumber;
    
    /**
     * 启用状态
     */
    private String enableStatus;
    
    /**
     * 操作结束时间
     */
    private Date operateEndTime;
    
    /**
     * 操作开始时间
     */
    private Date operateStartTime;
    
    /**
     * 设备类型
     */
    private String deviceType;
    
    /**
     * 设备编号
     */
    private String equipmentNumber;
    
    /**
     * 保养人
     */
    private String maintainUser;
    
    /**
     * 状态
     */
    private String status;
    
    /**
     * 开始时间
     */
    private Date startTime;
    
    /**
     * 结束时间
     */
    private Date endTime;
    
    /**
     * 条码
     */
    private String barCode;
    
    /**
     * 保养结果
     */
    private String maintainResult;
    
    /**
     * 设备名称
     */
    private String equipmentName;
    
    /**
     * 责任部门
     */
    private String maintainDept;
    
    /**
     * 保养项目列表
     */
    private List<MaintenanceItem> maintenanceItemList;
}