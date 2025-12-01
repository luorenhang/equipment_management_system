package edu.ngd.order.equipment_management_system.controller;

import edu.ngd.order.equipment_management_system.common.result.Result;
import edu.ngd.order.equipment_management_system.common.result.ResultCode;
import edu.ngd.order.equipment_management_system.entity.Equipment;
import edu.ngd.order.equipment_management_system.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 设备台账Controller类
 */
@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {
    
    @Autowired
    private EquipmentService equipmentService;
    
    /**
     * 创建设备
     * @param equipment 设备信息
     * @return 设备ID
     */
    @PostMapping
    public Result<Long> createEquipment(@RequestBody Equipment equipment) {
        Long equipmentId = equipmentService.createEquipment(equipment);
        return Result.success(equipmentId);
    }
    
    /**
     * 根据ID查询设备
     * @param id 设备ID
     * @return 设备信息
     */
    @GetMapping("/{id}")
    public Result<Equipment> getEquipmentById(@PathVariable Long id) {
        Equipment equipment = equipmentService.getEquipmentById(id);
        if (equipment == null) {
            return Result.fail(ResultCode.EQUIPMENT_NOT_EXIST);
        }
        return Result.success(equipment);
    }
    
    /**
     * 查询设备列表
     * @param equipment 设备信息（用于条件查询）
     * @return 设备列表
     */
    @GetMapping
    public Result<List<Equipment>> getEquipmentList(Equipment equipment) {
        List<Equipment> equipmentList = equipmentService.getEquipmentList(equipment);
        return Result.success(equipmentList);
    }
    
    /**
     * 更新设备信息
     * @param id 设备ID
     * @param equipment 设备信息
     * @return 更新结果
     */
    @PutMapping("/{id}")
    public Result<Integer> updateEquipment(@PathVariable Long id, @RequestBody Equipment equipment) {
        equipment.setId(id);
        int result = equipmentService.updateEquipment(equipment);
        return Result.success(result);
    }
    
    /**
     * 根据ID删除设备
     * @param id 设备ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Integer> deleteEquipmentById(@PathVariable Long id) {
        int result = equipmentService.deleteEquipmentById(id);
        return Result.success(result);
    }
}