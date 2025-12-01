package edu.ngd.order.equipment.fault.mapper;

import edu.ngd.order.equipment.fault.entity.EquipmentFaultCode;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 设备故障代码Mapper接口
 */
@Mapper
public interface EquipmentFaultCodeMapper {
    /**
     * 创建故障代码
     * @param faultCode 故障代码信息
     * @return 影响行数
     */
    int insert(EquipmentFaultCode faultCode);
    
    /**
     * 根据ID查询故障代码
     * @param id 故障代码ID
     * @return 故障代码信息
     */
    EquipmentFaultCode selectById(Integer id);
    
    /**
     * 查询故障代码列表
     * @param faultCode 故障代码信息（用于条件查询）
     * @return 故障代码列表
     */
    List<EquipmentFaultCode> selectList(EquipmentFaultCode faultCode);
    
    /**
     * 更新故障代码信息
     * @param faultCode 故障代码信息
     * @return 影响行数
     */
    int updateById(EquipmentFaultCode faultCode);
    
    /**
     * 根据ID删除故障代码
     * @param id 故障代码ID
     * @return 影响行数
     */
    int deleteById(Integer id);
}