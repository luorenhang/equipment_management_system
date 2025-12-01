package edu.ngd.order.equipment_management_system.service;

import edu.ngd.order.equipment_management_system.entity.EquipmentFaultCode;
import edu.ngd.order.equipment_management_system.mapper.EquipmentFaultCodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 设备故障代码Service类
 */
@Service
public class EquipmentFaultCodeService {
    
    @Autowired
    private EquipmentFaultCodeMapper equipmentFaultCodeMapper;
    
    /**
     * 创建故障代码
     * @param faultCode 故障代码信息
     * @return 故障代码ID
     */
    public Long createFaultCode(EquipmentFaultCode faultCode) {
        equipmentFaultCodeMapper.insert(faultCode);
        return faultCode.getId();
    }
    
    /**
     * 根据ID查询故障代码
     * @param id 故障代码ID
     * @return 故障代码信息
     */
    public EquipmentFaultCode getFaultCodeById(Long id) {
        return equipmentFaultCodeMapper.selectById(id);
    }
    
    /**
     * 查询故障代码列表
     * @param faultCode 故障代码信息（用于条件查询）
     * @return 故障代码列表
     */
    public List<EquipmentFaultCode> getFaultCodeList(EquipmentFaultCode faultCode) {
        return equipmentFaultCodeMapper.selectList(faultCode);
    }
    
    /**
     * 更新故障代码信息
     * @param faultCode 故障代码信息
     * @return 影响行数
     */
    public int updateFaultCode(EquipmentFaultCode faultCode) {
        return equipmentFaultCodeMapper.updateById(faultCode);
    }
    
    /**
     * 根据ID删除故障代码
     * @param id 故障代码ID
     * @return 影响行数
     */
    public int deleteFaultCodeById(Long id) {
        return equipmentFaultCodeMapper.deleteById(id);
    }
}