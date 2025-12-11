package edu.ngd.order.equipment.entity;

import lombok.Data;
import java.util.List;

/**
 * 设备台账实体类
 */
@Data
public class Equipment {
    /**
     * 主键
     */
    private Integer id;
    
    /**
     * 存放地点（数据字典取数）
     */
    private String storageLocation;
    
    /**
     * 设备名称
     */
    private String equipmentName;
    
    /**
     * 设备编号
     */
    private String equipmentNumber;
    
    /**
     * 产线（数据字典取数）
     */
    private String productionLine;
    
    /**
     * 状态
     */
    private String status;
    
    /**
     * 设备类型（数据字典取数）
     */
    private String deviceType;
    
    /**
     * 设备状态
     */
    private String equipmentStatus;
    
    /**
     * 责任人列表（关联关系，非数据库字段）
     */
    private List<Responsible> responsibleList;
}