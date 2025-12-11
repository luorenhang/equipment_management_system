package edu.ngd.order.equipment.repair.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

/**
 * 设备维修实体类
 */
@Data
public class EquipmentRepair {
    /**
     * 主键
     */
    private Integer id;
    
    /**
     * 派工时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date workTime;
    
    /**
     * 故障代码
     */
    private String repairCode;
    
    /**
     * 故障描述
     */
    private String faultDes;
    
    /**
     * 是否修复
     */
    private Integer repairResult;
    
    /**
     * 状态
     */
    private Integer status;
    
    /**
     * 设备编号
     */
    private String equipmentNumber;
    
    /**
     * 维修情况说明
     */
    private String repairExplain;
    
    /**
     * 维修人员
     */
    private String repairUser;
    
    /**
     * 设备名称
     */
    private String equipmentName;
}