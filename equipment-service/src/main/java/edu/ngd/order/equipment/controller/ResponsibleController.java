package edu.ngd.order.equipment.controller;

import edu.ngd.order.common.result.Result;
import edu.ngd.order.common.result.ResultCode;
import edu.ngd.order.equipment.entity.Responsible;
import edu.ngd.order.equipment.service.ResponsibleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 设备责任人Controller类
 */
@RestController
@RequestMapping("/api/responsible")
public class ResponsibleController {
    
    @Autowired
    private ResponsibleService responsibleService;
    
    /**
     * 创建责任人
     * @param responsible 责任人信息
     * @return 责任人ID
     */
    @PostMapping
    public Result<Integer> createResponsible(@RequestBody Responsible responsible) {
        Integer responsibleId = responsibleService.createResponsible(responsible);
        return Result.success(responsibleId);
    }
    
    /**
     * 根据ID查询责任人
     * @param id 责任人ID
     * @return 责任人信息
     */
    @GetMapping("/{id}")
    public Result<Responsible> getResponsibleById(@PathVariable Integer id) {
        Responsible responsible = responsibleService.getResponsibleById(id);
        if (responsible == null) {
            return Result.fail(ResultCode.RESPONSIBLE_NOT_EXIST);
        }
        return Result.success(responsible);
    }
    
    /**
     * 查询责任人列表
     * @param responsible 责任人信息（用于条件查询）
     * @return 责任人列表
     */
    @GetMapping
    public Result<List<Responsible>> getResponsibleList(Responsible responsible) {
        List<Responsible> responsibleList = responsibleService.getResponsibleList(responsible);
        return Result.success(responsibleList);
    }
    
    /**
     * 根据设备ID查询责任人列表
     * @param equipmentLedger 设备台账ID
     * @return 责任人列表
     */
    @GetMapping("/equipment/{equipmentLedger}")
    public Result<List<Responsible>> getResponsibleByEquipmentId(@PathVariable Integer equipmentLedger) {
        List<Responsible> responsibleList = responsibleService.getResponsibleByEquipmentId(equipmentLedger);
        return Result.success(responsibleList);
    }
    
    /**
     * 更新责任人信息
     * @param id 责任人ID
     * @param responsible 责任人信息
     * @return 更新结果
     */
    @PutMapping("/{id}")
    public Result<Integer> updateResponsible(@PathVariable Integer id, @RequestBody Responsible responsible) {
        responsible.setId(id);
        int result = responsibleService.updateResponsible(responsible);
        return Result.success(result);
    }
    
    /**
     * 根据ID删除责任人
     * @param id 责任人ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Integer> deleteResponsibleById(@PathVariable Integer id) {
        int result = responsibleService.deleteResponsibleById(id);
        return Result.success(result);
    }
}