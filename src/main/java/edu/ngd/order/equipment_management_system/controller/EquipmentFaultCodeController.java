package edu.ngd.order.equipment_management_system.controller;

import edu.ngd.order.equipment_management_system.common.result.Result;
import edu.ngd.order.equipment_management_system.common.result.ResultCode;
import edu.ngd.order.equipment_management_system.entity.EquipmentFaultCode;
import edu.ngd.order.equipment_management_system.service.EquipmentFaultCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 设备故障代码Controller类
 */
@RestController
@RequestMapping("/api/fault-code")
public class EquipmentFaultCodeController {
    
    @Autowired
    private EquipmentFaultCodeService equipmentFaultCodeService;
    
    /**
     * 创建故障代码
     * @param faultCode 故障代码信息
     * @return 故障代码ID
     */
    @PostMapping
    public Result<Long> createFaultCode(@RequestBody EquipmentFaultCode faultCode) {
        Long faultCodeId = equipmentFaultCodeService.createFaultCode(faultCode);
        return Result.success(faultCodeId);
    }
    
    /**
     * 根据ID查询故障代码
     * @param id 故障代码ID
     * @return 故障代码信息
     */
    @GetMapping("/{id}")
    public Result<EquipmentFaultCode> getFaultCodeById(@PathVariable Long id) {
        EquipmentFaultCode faultCode = equipmentFaultCodeService.getFaultCodeById(id);
        if (faultCode == null) {
            return Result.fail(ResultCode.FAULT_CODE_NOT_EXIST);
        }
        return Result.success(faultCode);
    }
    
    /**
     * 查询故障代码列表
     * @param faultCode 故障代码信息（用于条件查询）
     * @return 故障代码列表
     */
    @GetMapping
    public Result<List<EquipmentFaultCode>> getFaultCodeList(EquipmentFaultCode faultCode) {
        List<EquipmentFaultCode> faultCodeList = equipmentFaultCodeService.getFaultCodeList(faultCode);
        return Result.success(faultCodeList);
    }
    
    /**
     * 更新故障代码信息
     * @param id 故障代码ID
     * @param faultCode 故障代码信息
     * @return 更新结果
     */
    @PutMapping("/{id}")
    public Result<Integer> updateFaultCode(@PathVariable Long id, @RequestBody EquipmentFaultCode faultCode) {
        faultCode.setId(id);
        int result = equipmentFaultCodeService.updateFaultCode(faultCode);
        return Result.success(result);
    }
    
    /**
     * 根据ID删除故障代码
     * @param id 故障代码ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Integer> deleteFaultCodeById(@PathVariable Long id) {
        int result = equipmentFaultCodeService.deleteFaultCodeById(id);
        return Result.success(result);
    }
}