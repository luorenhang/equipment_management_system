package edu.ngd.order.equipment_management_system.service;

import edu.ngd.order.equipment_management_system.entity.EquipmentMaintenance;
import edu.ngd.order.equipment_management_system.mapper.EquipmentMaintenanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 设备保养Service类
 */
@Service
public class EquipmentMaintenanceService {
    
    @Autowired
    private EquipmentMaintenanceMapper equipmentMaintenanceMapper;
    
    /**
     * 创建保养记录
     * @param maintenance 保养记录信息
     * @return 保养记录ID
     */
    public Long createMaintenance(EquipmentMaintenance maintenance) {
        equipmentMaintenanceMapper.insert(maintenance);
        return maintenance.getId();
    }
    
    /**
     * 根据ID查询保养记录
     * @param id 保养记录ID
     * @return 保养记录信息
     */
    public EquipmentMaintenance getMaintenanceById(Long id) {
        return equipmentMaintenanceMapper.selectById(id);
    }
    
    /**
     * 查询保养记录列表
     * @param maintenance 保养记录信息（用于条件查询）
     * @return 保养记录列表
     */
    public List<EquipmentMaintenance> getMaintenanceList(EquipmentMaintenance maintenance) {
        return equipmentMaintenanceMapper.selectList(maintenance);
    }
    
    /**
     * 更新保养记录信息
     * @param maintenance 保养记录信息
     * @return 影响行数
     */
    public int updateMaintenance(EquipmentMaintenance maintenance) {
        return equipmentMaintenanceMapper.updateById(maintenance);
    }
    
    /**
     * 根据ID删除保养记录
     * @param id 保养记录ID
     * @return 影响行数
     */
    public int deleteMaintenanceById(Long id) {
        return equipmentMaintenanceMapper.deleteById(id);
    }
}