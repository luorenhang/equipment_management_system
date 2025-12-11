package edu.ngd.order.equipment.repair.mapper;

import edu.ngd.order.equipment.repair.entity.EquipmentRepair;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 设备维修Mapper接口
 */
@Mapper
public interface EquipmentRepairMapper {
    /**
     * 创建维修记录
     * @param repair 维修记录信息
     * @return 影响行数
     */
    int insert(EquipmentRepair repair);
    
    /**
     * 根据ID查询维修记录
     * @param id 维修记录ID
     * @return 维修记录信息
     */
    EquipmentRepair selectById(Integer id);
    
    /**
     * 查询维修记录列表
     * @param repair 维修记录信息（用于条件查询）
     * @return 维修记录列表
     */
    List<EquipmentRepair> selectList(EquipmentRepair repair);
    
    /**
     * 更新维修记录信息
     * @param repair 维修记录信息
     * @return 影响行数
     */
    int updateById(EquipmentRepair repair);
    
    /**
     * 根据ID删除维修记录
     * @param id 维修记录ID
     * @return 影响行数
     */
    int deleteById(Integer id);
}