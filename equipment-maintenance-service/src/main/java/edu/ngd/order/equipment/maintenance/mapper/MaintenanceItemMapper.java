package edu.ngd.order.equipment.maintenance.mapper;

import edu.ngd.order.equipment.maintenance.entity.MaintenanceItem;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 保养项目Mapper接口
 */
@Mapper
public interface MaintenanceItemMapper {
    /**
     * 创建保养项目
     * @param maintenanceItem 保养项目信息
     * @return 影响行数
     */
    int insert(MaintenanceItem maintenanceItem);
    
    /**
     * 批量创建保养项目
     * @param maintenanceItemList 保养项目列表
     * @return 影响行数
     */
    int insertBatch(List<MaintenanceItem> maintenanceItemList);
    
    /**
     * 根据保养记录ID查询保养项目列表
     * @param maintenanceId 保养记录ID
     * @return 保养项目列表
     */
    List<MaintenanceItem> selectByMaintenanceId(Integer maintenanceId);
    
    /**
     * 根据保养记录ID删除保养项目
     * @param maintenanceId 保养记录ID
     * @return 影响行数
     */
    int deleteByMaintenanceId(Integer maintenanceId);
}