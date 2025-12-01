package edu.ngd.order.equipment_management_system.service;

import edu.ngd.order.equipment_management_system.entity.EquipmentInspection;
import edu.ngd.order.equipment_management_system.mapper.EquipmentInspectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 设备点检Service类
 */
@Service
public class EquipmentInspectionService {
    
    @Autowired
    private EquipmentInspectionMapper equipmentInspectionMapper;
    
    /**
     * 创建点检记录
     * @param inspection 点检记录信息
     * @return 点检记录ID
     */
    public Long createInspection(EquipmentInspection inspection) {
        equipmentInspectionMapper.insert(inspection);
        return inspection.getId();
    }
    
    /**
     * 根据ID查询点检记录
     * @param id 点检记录ID
     * @return 点检记录信息
     */
    public EquipmentInspection getInspectionById(Long id) {
        return equipmentInspectionMapper.selectById(id);
    }
    
    /**
     * 查询点检记录列表
     * @param inspection 点检记录信息（用于条件查询）
     * @return 点检记录列表
     */
    public List<EquipmentInspection> getInspectionList(EquipmentInspection inspection) {
        return equipmentInspectionMapper.selectList(inspection);
    }
    
    /**
     * 更新点检记录信息
     * @param inspection 点检记录信息
     * @return 影响行数
     */
    public int updateInspection(EquipmentInspection inspection) {
        return equipmentInspectionMapper.updateById(inspection);
    }
    
    /**
     * 根据ID删除点检记录
     * @param id 点检记录ID
     * @return 影响行数
     */
    public int deleteInspectionById(Long id) {
        return equipmentInspectionMapper.deleteById(id);
    }
}