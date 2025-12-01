package edu.ngd.order.equipment_management_system.mapper;

import edu.ngd.order.equipment_management_system.entity.Equipment;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 设备台账Mapper接口
 */
@Mapper
public interface EquipmentMapper {
    /**
     * 创建设备
     * @param equipment 设备信息
     * @return 影响行数
     */
    int insert(Equipment equipment);
    
    /**
     * 根据ID查询设备
     * @param id 设备ID
     * @return 设备信息
     */
    Equipment selectById(Long id);
    
    /**
     * 查询设备列表
     * @param equipment 设备信息（用于条件查询）
     * @return 设备列表
     */
    List<Equipment> selectList(Equipment equipment);
    
    /**
     * 更新设备信息
     * @param equipment 设备信息
     * @return 影响行数
     */
    int updateById(Equipment equipment);
    
    /**
     * 根据ID删除设备
     * @param id 设备ID
     * @return 影响行数
     */
    int deleteById(Long id);
}