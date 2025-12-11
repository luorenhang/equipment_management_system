package edu.ngd.order.equipment.maintenance.service;

import edu.ngd.order.equipment.maintenance.entity.EquipmentMaintenance;
import edu.ngd.order.equipment.maintenance.entity.MaintenanceItem;
import edu.ngd.order.equipment.maintenance.mapper.EquipmentMaintenanceMapper;
import edu.ngd.order.equipment.maintenance.mapper.MaintenanceItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * 设备保养Service类
 */
@Service
public class EquipmentMaintenanceService {
    
    @Autowired
    private EquipmentMaintenanceMapper equipmentMaintenanceMapper;
    
    @Autowired
    private MaintenanceItemMapper maintenanceItemMapper;
    
    /**
     * 创建保养记录
     * @param maintenance 保养记录信息
     * @return 保养记录ID
     */
    @Transactional(rollbackFor = Exception.class)
    public Integer createMaintenance(EquipmentMaintenance maintenance) {
        System.out.println("=== 开始创建保养记录 ===");
        System.out.println("保养记录信息: " + maintenance);
        
        try {
            // 验证必填字段
            if (maintenance.getMaintainNumber() == null || maintenance.getMaintainNumber().isEmpty()) {
                System.out.println("保养编号不能为空");
                throw new IllegalArgumentException("保养编号不能为空");
            }
            if (maintenance.getEquipmentName() == null || maintenance.getEquipmentName().isEmpty()) {
                System.out.println("设备名称不能为空");
                throw new IllegalArgumentException("设备名称不能为空");
            }
            if (maintenance.getEquipmentNumber() == null || maintenance.getEquipmentNumber().isEmpty()) {
                System.out.println("设备编号不能为空");
                throw new IllegalArgumentException("设备编号不能为空");
            }
            if (maintenance.getDeviceType() == null || maintenance.getDeviceType().isEmpty()) {
                System.out.println("设备类型不能为空");
                throw new IllegalArgumentException("设备类型不能为空");
            }
            if (maintenance.getMaintainUser() == null || maintenance.getMaintainUser().isEmpty()) {
                System.out.println("保养人不能为空");
                throw new IllegalArgumentException("保养人不能为空");
            }
            if (maintenance.getStartTime() == null) {
                System.out.println("保养时间不能为空");
                throw new IllegalArgumentException("保养时间不能为空");
            }
            if (maintenance.getStatus() == null || maintenance.getStatus().isEmpty()) {
                System.out.println("状态不能为空");
                throw new IllegalArgumentException("状态不能为空");
            }
            
            // 创建主表记录
            System.out.println("=== 开始插入主表记录 ===");
            int insertResult = equipmentMaintenanceMapper.insert(maintenance);
            System.out.println("主表记录插入结果: " + insertResult);
            System.out.println("主表记录插入成功，ID: " + maintenance.getId());
            
            // 处理保养项目列表
            System.out.println("=== 开始处理保养项目列表 ===");
            List<MaintenanceItem> maintenanceItemList = maintenance.getMaintenanceItemList();
            System.out.println("保养项目列表: " + maintenanceItemList);
            
            if (maintenanceItemList != null && !maintenanceItemList.isEmpty()) {
                System.out.println("保养项目列表不为空，共有 " + maintenanceItemList.size() + " 个项目");
                for (MaintenanceItem item : maintenanceItemList) {
                    System.out.println("处理保养项目: " + item);
                    item.setMaintenanceId(maintenance.getId());
                    System.out.println("设置保养记录ID后的保养项目: " + item);
                    
                    // 验证保养项目必填字段
                    if (item.getMaintenanceItem() == null || item.getMaintenanceItem().isEmpty()) {
                        System.out.println("保养项目名称不能为空");
                        throw new IllegalArgumentException("保养项目名称不能为空");
                    }
                    if (item.getMaintenanceContent() == null || item.getMaintenanceContent().isEmpty()) {
                        System.out.println("保养项目内容不能为空");
                        throw new IllegalArgumentException("保养项目内容不能为空");
                    }
                    if (item.getMaintenanceResult() == null || item.getMaintenanceResult().isEmpty()) {
                        System.out.println("保养项目结果不能为空");
                        throw new IllegalArgumentException("保养项目结果不能为空");
                    }
                    
                    int itemInsertResult = maintenanceItemMapper.insert(item);
                    System.out.println("保养项目插入结果: " + itemInsertResult);
                    System.out.println("保养项目插入成功");
                }
            } else {
                System.out.println("保养项目列表为空");
            }
            
            System.out.println("=== 保养记录创建成功 ===");
            return maintenance.getId();
        } catch (Exception e) {
            System.out.println("=== 创建设备保养记录失败 ===");
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * 根据ID查询保养记录
     * @param id 保养记录ID
     * @return 保养记录信息
     */
    public EquipmentMaintenance getMaintenanceById(Integer id) {
        EquipmentMaintenance maintenance = equipmentMaintenanceMapper.selectById(id);
        if (maintenance != null) {
            // 查询保养项目列表
            List<MaintenanceItem> maintenanceItemList = maintenanceItemMapper.selectByMaintenanceId(id);
            maintenance.setMaintenanceItemList(maintenanceItemList);
        }
        return maintenance;
    }
    
    /**
     * 查询保养记录列表
     * @param maintenance 保养记录信息（用于条件查询）
     * @return 保养记录列表
     */
    public List<EquipmentMaintenance> getMaintenanceList(EquipmentMaintenance maintenance) {
        List<EquipmentMaintenance> maintenanceList = equipmentMaintenanceMapper.selectList(maintenance);
        // 查询每个保养记录的保养项目列表
        for (EquipmentMaintenance item : maintenanceList) {
            List<MaintenanceItem> maintenanceItemList = maintenanceItemMapper.selectByMaintenanceId(item.getId());
            item.setMaintenanceItemList(maintenanceItemList);
        }
        return maintenanceList;
    }
    
    /**
     * 更新保养记录信息
     * @param maintenance 保养记录信息
     * @return 影响行数
     */
    @Transactional(rollbackFor = Exception.class)
    public int updateMaintenance(EquipmentMaintenance maintenance) {
        // 更新主表记录
        int result = equipmentMaintenanceMapper.updateById(maintenance);
        
        // 删除旧的保养项目
        maintenanceItemMapper.deleteByMaintenanceId(maintenance.getId());
        
        // 插入新的保养项目列表
        if (maintenance.getMaintenanceItemList() != null && !maintenance.getMaintenanceItemList().isEmpty()) {
            for (MaintenanceItem item : maintenance.getMaintenanceItemList()) {
                item.setMaintenanceId(maintenance.getId());
                maintenanceItemMapper.insert(item);
            }
        }
        
        return result;
    }
    
    /**
     * 根据ID删除保养记录
     * @param id 保养记录ID
     * @return 影响行数
     */
    @Transactional(rollbackFor = Exception.class)
    public int deleteMaintenanceById(Integer id) {
        // 删除保养项目
        maintenanceItemMapper.deleteByMaintenanceId(id);
        // 删除主表记录
        return equipmentMaintenanceMapper.deleteById(id);
    }
}