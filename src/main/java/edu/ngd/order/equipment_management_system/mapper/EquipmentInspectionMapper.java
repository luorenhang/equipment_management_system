package edu.ngd.order.equipment_management_system.mapper;

import edu.ngd.order.equipment_management_system.entity.EquipmentInspection;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 设备点检Mapper接口
 */
@Mapper
public interface EquipmentInspectionMapper {
    /**
     * 创建点检记录
     * @param inspection 点检记录信息
     * @return 影响行数
     */
    int insert(EquipmentInspection inspection);
    
    /**
     * 根据ID查询点检记录
     * @param id 点检记录ID
     * @return 点检记录信息
     */
    EquipmentInspection selectById(Long id);
    
    /**
     * 查询点检记录列表
     * @param inspection 点检记录信息（用于条件查询）
     * @return 点检记录列表
     */
    List<EquipmentInspection> selectList(EquipmentInspection inspection);
    
    /**
     * 更新点检记录信息
     * @param inspection 点检记录信息
     * @return 影响行数
     */
    int updateById(EquipmentInspection inspection);
    
    /**
     * 根据ID删除点检记录
     * @param id 点检记录ID
     * @return 影响行数
     */
    int deleteById(Long id);
}