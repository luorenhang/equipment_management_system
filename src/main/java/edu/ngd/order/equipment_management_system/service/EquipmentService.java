package edu.ngd.order.equipment_management_system.service;

import edu.ngd.order.equipment_management_system.entity.Equipment;
import edu.ngd.order.equipment_management_system.mapper.EquipmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 设备台账Service类
 */
@Service
public class EquipmentService {
    
    @Autowired
    private EquipmentMapper equipmentMapper;
    
    /**
     * 创建设备
     * @param equipment 设备信息
     * @return 设备ID
     */
    public Long createEquipment(Equipment equipment) {
        equipmentMapper.insert(equipment);
        return equipment.getId();
    }
    
    /**
     * 根据ID查询设备
     * @param id 设备ID
     * @return 设备信息
     */
    public Equipment getEquipmentById(Long id) {
        return equipmentMapper.selectById(id);
    }
    
    /**
     * 查询设备列表
     * @param equipment 设备信息（用于条件查询）
     * @return 设备列表
     */
    public List<Equipment> getEquipmentList(Equipment equipment) {
        return equipmentMapper.selectList(equipment);
    }
    
    /**
     * 更新设备信息
     * @param equipment 设备信息
     * @return 影响行数
     */
    public int updateEquipment(Equipment equipment) {
        return equipmentMapper.updateById(equipment);
    }
    
    /**
     * 根据ID删除设备
     * @param id 设备ID
     * @return 影响行数
     */
    public int deleteEquipmentById(Long id) {
        return equipmentMapper.deleteById(id);
    }
}