package edu.ngd.order.equipment.repair.service;

import edu.ngd.order.equipment.repair.entity.EquipmentRepair;
import edu.ngd.order.equipment.repair.mapper.EquipmentRepairMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 设备维修Service类
 */
@Service
public class EquipmentRepairService {
    
    @Autowired
    private EquipmentRepairMapper equipmentRepairMapper;
    
    /**
     * 创建维修记录
     * @param repair 维修记录信息
     * @return 维修记录ID
     */
    public Integer createRepair(EquipmentRepair repair) {
        equipmentRepairMapper.insert(repair);
        return repair.getId();
    }
    
    /**
     * 根据ID查询维修记录
     * @param id 维修记录ID
     * @return 维修记录信息
     */
    public EquipmentRepair getRepairById(Integer id) {
        return equipmentRepairMapper.selectById(id);
    }
    
    /**
     * 查询维修记录列表
     * @param repair 维修记录信息（用于条件查询）
     * @return 维修记录列表
     */
    public List<EquipmentRepair> getRepairList(EquipmentRepair repair) {
        return equipmentRepairMapper.selectList(repair);
    }
    
    /**
     * 更新维修记录信息
     * @param repair 维修记录信息
     * @return 影响行数
     */
    public int updateRepair(EquipmentRepair repair) {
        return equipmentRepairMapper.updateById(repair);
    }
    
    /**
     * 根据ID删除维修记录
     * @param id 维修记录ID
     * @return 影响行数
     */
    public int deleteRepairById(Integer id) {
        return equipmentRepairMapper.deleteById(id);
    }
}