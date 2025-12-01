package edu.ngd.order.equipment.inspection.controller;

import edu.ngd.order.common.result.Result;
import edu.ngd.order.common.result.ResultCode;
import edu.ngd.order.equipment.inspection.entity.CheckProject;
import edu.ngd.order.equipment.inspection.service.CheckProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 设备点检项目Controller类
 */
@RestController
@RequestMapping("/api/check-project")
public class CheckProjectController {
    
    @Autowired
    private CheckProjectService checkProjectService;
    
    /**
     * 创建点检项目
     * @param checkProject 点检项目信息
     * @return 点检项目ID
     */
    @PostMapping
    public Result<Integer> createCheckProject(@RequestBody CheckProject checkProject) {
        Integer checkProjectId = checkProjectService.createCheckProject(checkProject);
        return Result.success(checkProjectId);
    }
    
    /**
     * 根据ID查询点检项目
     * @param id 点检项目ID
     * @return 点检项目信息
     */
    @GetMapping("/{id}")
    public Result<CheckProject> getCheckProjectById(@PathVariable Integer id) {
        CheckProject checkProject = checkProjectService.getCheckProjectById(id);
        if (checkProject == null) {
            return Result.fail(ResultCode.CHECK_PROJECT_NOT_EXIST);
        }
        return Result.success(checkProject);
    }
    
    /**
     * 查询点检项目列表
     * @param checkProject 点检项目信息（用于条件查询）
     * @return 点检项目列表
     */
    @GetMapping
    public Result<List<CheckProject>> getCheckProjectList(CheckProject checkProject) {
        List<CheckProject> checkProjectList = checkProjectService.getCheckProjectList(checkProject);
        return Result.success(checkProjectList);
    }
    
    /**
     * 根据点检ID查询点检项目列表
     * @param checkId 点检ID
     * @return 点检项目列表
     */
    @GetMapping("/check/{checkId}")
    public Result<List<CheckProject>> getCheckProjectByCheckId(@PathVariable Integer checkId) {
        List<CheckProject> checkProjectList = checkProjectService.getCheckProjectByCheckId(checkId);
        return Result.success(checkProjectList);
    }
    
    /**
     * 更新点检项目信息
     * @param id 点检项目ID
     * @param checkProject 点检项目信息
     * @return 更新结果
     */
    @PutMapping("/{id}")
    public Result<Integer> updateCheckProject(@PathVariable Integer id, @RequestBody CheckProject checkProject) {
        checkProject.setId(id);
        int result = checkProjectService.updateCheckProject(checkProject);
        return Result.success(result);
    }
    
    /**
     * 根据ID删除点检项目
     * @param id 点检项目ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Integer> deleteCheckProjectById(@PathVariable Integer id) {
        int result = checkProjectService.deleteCheckProjectById(id);
        return Result.success(result);
    }
}