package edu.ngd.order.equipment.inspection.service;

import edu.ngd.order.equipment.inspection.entity.CheckProject;
import edu.ngd.order.equipment.inspection.entity.EquipmentInspection;
import edu.ngd.order.equipment.inspection.mapper.CheckProjectMapper;
import edu.ngd.order.equipment.inspection.mapper.EquipmentInspectionMapper;
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
    
    @Autowired
    private CheckProjectMapper checkProjectMapper;
    
    /**
     * 创建点检记录
     * @param inspection 点检记录信息
     * @return 点检记录ID
     */
    public Integer createInspection(EquipmentInspection inspection) {
        equipmentInspectionMapper.insert(inspection);
        return inspection.getId();
    }
    
    /**
     * 根据ID查询点检记录
     * @param id 点检记录ID
     * @return 点检记录信息
     */
    public EquipmentInspection getInspectionById(Integer id) {
        EquipmentInspection inspection = equipmentInspectionMapper.selectById(id);
        if (inspection != null) {
            // 查询点检记录对应的点检项目列表
            List<CheckProject> checkProjectList = checkProjectMapper.selectByCheckId(inspection.getId());
            inspection.setCheckProjectList(checkProjectList);
        }
        return inspection;
    }
    
    /**
     * 查询点检记录列表
     * @param inspection 点检记录信息（用于条件查询）
     * @return 点检记录列表
     */
    public List<EquipmentInspection> getInspectionList(EquipmentInspection inspection) {
        List<EquipmentInspection> inspectionList = equipmentInspectionMapper.selectList(inspection);
        // 为每个点检记录查询对应的点检项目列表
        for (EquipmentInspection eqInspection : inspectionList) {
            List<CheckProject> checkProjectList = checkProjectMapper.selectByCheckId(eqInspection.getId());
            eqInspection.setCheckProjectList(checkProjectList);
        }
        return inspectionList;
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
    public int deleteInspectionById(Integer id) {
        // 先删除点检记录对应的点检项目
        checkProjectMapper.deleteByCheckId(id);
        // 再删除点检记录
        return equipmentInspectionMapper.deleteById(id);
    }
}