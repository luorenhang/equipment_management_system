package edu.ngd.order.equipment_management_system.mapper;

import edu.ngd.order.equipment_management_system.entity.EquipmentMaintenance;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 设备保养Mapper接口
 */
@Mapper
public interface EquipmentMaintenanceMapper {
    /**
     * 创建保养记录
     * @param maintenance 保养记录信息
     * @return 影响行数
     */
    int insert(EquipmentMaintenance maintenance);
    
    /**
     * 根据ID查询保养记录
     * @param id 保养记录ID
     * @return 保养记录信息
     */
    EquipmentMaintenance selectById(Long id);
    
    /**
     * 查询保养记录列表
     * @param maintenance 保养记录信息（用于条件查询）
     * @return 保养记录列表
     */
    List<EquipmentMaintenance> selectList(EquipmentMaintenance maintenance);
    
    /**
     * 更新保养记录信息
     * @param maintenance 保养记录信息
     * @return 影响行数
     */
    int updateById(EquipmentMaintenance maintenance);
    
    /**
     * 根据ID删除保养记录
     * @param id 保养记录ID
     * @return 影响行数
     */
    int deleteById(Long id);
}