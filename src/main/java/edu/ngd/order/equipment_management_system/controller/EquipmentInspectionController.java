package edu.ngd.order.equipment_management_system.controller;

import edu.ngd.order.equipment_management_system.common.result.Result;
import edu.ngd.order.equipment_management_system.common.result.ResultCode;
import edu.ngd.order.equipment_management_system.entity.EquipmentInspection;
import edu.ngd.order.equipment_management_system.service.EquipmentInspectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 设备点检Controller类
 */
@RestController
@RequestMapping("/api/inspection")
public class EquipmentInspectionController {
    
    @Autowired
    private EquipmentInspectionService equipmentInspectionService;
    
    /**
     * 创建点检记录
     * @param inspection 点检记录信息
     * @return 点检记录ID
     */
    @PostMapping
    public Result<Long> createInspection(@RequestBody EquipmentInspection inspection) {
        Long inspectionId = equipmentInspectionService.createInspection(inspection);
        return Result.success(inspectionId);
    }
    
    /**
     * 根据ID查询点检记录
     * @param id 点检记录ID
     * @return 点检记录信息
     */
    @GetMapping("/{id}")
    public Result<EquipmentInspection> getInspectionById(@PathVariable Long id) {
        EquipmentInspection inspection = equipmentInspectionService.getInspectionById(id);
        if (inspection == null) {
            return Result.fail(ResultCode.INSPECTION_NOT_EXIST);
        }
        return Result.success(inspection);
    }
    
    /**
     * 查询点检记录列表
     * @param inspection 点检记录信息（用于条件查询）
     * @return 点检记录列表
     */
    @GetMapping
    public Result<List<EquipmentInspection>> getInspectionList(EquipmentInspection inspection) {
        List<EquipmentInspection> inspectionList = equipmentInspectionService.getInspectionList(inspection);
        return Result.success(inspectionList);
    }
    
    /**
     * 更新点检记录信息
     * @param id 点检记录ID
     * @param inspection 点检记录信息
     * @return 更新结果
     */
    @PutMapping("/{id}")
    public Result<Integer> updateInspection(@PathVariable Long id, @RequestBody EquipmentInspection inspection) {
        inspection.setId(id);
        int result = equipmentInspectionService.updateInspection(inspection);
        return Result.success(result);
    }
    
    /**
     * 根据ID删除点检记录
     * @param id 点检记录ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Integer> deleteInspectionById(@PathVariable Long id) {
        int result = equipmentInspectionService.deleteInspectionById(id);
        return Result.success(result);
    }
}