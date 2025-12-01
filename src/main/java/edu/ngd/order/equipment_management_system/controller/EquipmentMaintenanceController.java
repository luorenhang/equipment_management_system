package edu.ngd.order.equipment_management_system.controller;

import edu.ngd.order.equipment_management_system.common.result.Result;
import edu.ngd.order.equipment_management_system.common.result.ResultCode;
import edu.ngd.order.equipment_management_system.entity.EquipmentMaintenance;
import edu.ngd.order.equipment_management_system.service.EquipmentMaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 设备保养Controller类
 */
@RestController
@RequestMapping("/api/maintenance")
public class EquipmentMaintenanceController {
    
    @Autowired
    private EquipmentMaintenanceService equipmentMaintenanceService;
    
    /**
     * 创建保养记录
     * @param maintenance 保养记录信息
     * @return 保养记录ID
     */
    @PostMapping
    public Result<Long> createMaintenance(@RequestBody EquipmentMaintenance maintenance) {
        Long maintenanceId = equipmentMaintenanceService.createMaintenance(maintenance);
        return Result.success(maintenanceId);
    }
    
    /**
     * 根据ID查询保养记录
     * @param id 保养记录ID
     * @return 保养记录信息
     */
    @GetMapping("/{id}")
    public Result<EquipmentMaintenance> getMaintenanceById(@PathVariable Long id) {
        EquipmentMaintenance maintenance = equipmentMaintenanceService.getMaintenanceById(id);
        if (maintenance == null) {
            return Result.fail(ResultCode.MAINTENANCE_NOT_EXIST);
        }
        return Result.success(maintenance);
    }
    
    /**
     * 查询保养记录列表
     * @param maintenance 保养记录信息（用于条件查询）
     * @return 保养记录列表
     */
    @GetMapping
    public Result<List<EquipmentMaintenance>> getMaintenanceList(EquipmentMaintenance maintenance) {
        List<EquipmentMaintenance> maintenanceList = equipmentMaintenanceService.getMaintenanceList(maintenance);
        return Result.success(maintenanceList);
    }
    
    /**
     * 更新保养记录信息
     * @param id 保养记录ID
     * @param maintenance 保养记录信息
     * @return 更新结果
     */
    @PutMapping("/{id}")
    public Result<Integer> updateMaintenance(@PathVariable Long id, @RequestBody EquipmentMaintenance maintenance) {
        maintenance.setId(id);
        int result = equipmentMaintenanceService.updateMaintenance(maintenance);
        return Result.success(result);
    }
    
    /**
     * 根据ID删除保养记录
     * @param id 保养记录ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Integer> deleteMaintenanceById(@PathVariable Long id) {
        int result = equipmentMaintenanceService.deleteMaintenanceById(id);
        return Result.success(result);
    }
}