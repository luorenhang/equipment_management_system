package edu.ngd.order.equipment_management_system.controller;

import edu.ngd.order.equipment_management_system.common.result.Result;
import edu.ngd.order.equipment_management_system.common.result.ResultCode;
import edu.ngd.order.equipment_management_system.entity.EquipmentRepair;
import edu.ngd.order.equipment_management_system.service.EquipmentRepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 设备维修Controller类
 */
@RestController
@RequestMapping("/api/repair")
public class EquipmentRepairController {
    
    @Autowired
    private EquipmentRepairService equipmentRepairService;
    
    /**
     * 创建维修记录
     * @param repair 维修记录信息
     * @return 维修记录ID
     */
    @PostMapping
    public Result<Long> createRepair(@RequestBody EquipmentRepair repair) {
        Long repairId = equipmentRepairService.createRepair(repair);
        return Result.success(repairId);
    }
    
    /**
     * 根据ID查询维修记录
     * @param id 维修记录ID
     * @return 维修记录信息
     */
    @GetMapping("/{id}")
    public Result<EquipmentRepair> getRepairById(@PathVariable Long id) {
        EquipmentRepair repair = equipmentRepairService.getRepairById(id);
        if (repair == null) {
            return Result.fail(ResultCode.REPAIR_NOT_EXIST);
        }
        return Result.success(repair);
    }
    
    /**
     * 查询维修记录列表
     * @param repair 维修记录信息（用于条件查询）
     * @return 维修记录列表
     */
    @GetMapping
    public Result<List<EquipmentRepair>> getRepairList(EquipmentRepair repair) {
        List<EquipmentRepair> repairList = equipmentRepairService.getRepairList(repair);
        return Result.success(repairList);
    }
    
    /**
     * 更新维修记录信息
     * @param id 维修记录ID
     * @param repair 维修记录信息
     * @return 更新结果
     */
    @PutMapping("/{id}")
    public Result<Integer> updateRepair(@PathVariable Long id, @RequestBody EquipmentRepair repair) {
        repair.setId(id);
        int result = equipmentRepairService.updateRepair(repair);
        return Result.success(result);
    }
    
    /**
     * 根据ID删除维修记录
     * @param id 维修记录ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Integer> deleteRepairById(@PathVariable Long id) {
        int result = equipmentRepairService.deleteRepairById(id);
        return Result.success(result);
    }
}