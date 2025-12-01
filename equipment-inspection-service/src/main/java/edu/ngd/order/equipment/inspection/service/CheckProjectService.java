package edu.ngd.order.equipment.inspection.service;

import edu.ngd.order.equipment.inspection.entity.CheckProject;
import edu.ngd.order.equipment.inspection.mapper.CheckProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 设备点检项目Service类
 */
@Service
public class CheckProjectService {
    
    @Autowired
    private CheckProjectMapper checkProjectMapper;
    
    /**
     * 创建点检项目
     * @param checkProject 点检项目信息
     * @return 点检项目ID
     */
    public Integer createCheckProject(CheckProject checkProject) {
        checkProjectMapper.insert(checkProject);
        return checkProject.getId();
    }
    
    /**
     * 根据ID查询点检项目
     * @param id 点检项目ID
     * @return 点检项目信息
     */
    public CheckProject getCheckProjectById(Integer id) {
        return checkProjectMapper.selectById(id);
    }
    
    /**
     * 查询点检项目列表
     * @param checkProject 点检项目信息（用于条件查询）
     * @return 点检项目列表
     */
    public List<CheckProject> getCheckProjectList(CheckProject checkProject) {
        return checkProjectMapper.selectList(checkProject);
    }
    
    /**
     * 根据点检ID查询点检项目列表
     * @param checkId 点检ID
     * @return 点检项目列表
     */
    public List<CheckProject> getCheckProjectByCheckId(Integer checkId) {
        return checkProjectMapper.selectByCheckId(checkId);
    }
    
    /**
     * 更新点检项目信息
     * @param checkProject 点检项目信息
     * @return 影响行数
     */
    public int updateCheckProject(CheckProject checkProject) {
        return checkProjectMapper.updateById(checkProject);
    }
    
    /**
     * 根据ID删除点检项目
     * @param id 点检项目ID
     * @return 影响行数
     */
    public int deleteCheckProjectById(Integer id) {
        return checkProjectMapper.deleteById(id);
    }
    
    /**
     * 根据点检ID删除点检项目
     * @param checkId 点检ID
     * @return 影响行数
     */
    public int deleteCheckProjectByCheckId(Integer checkId) {
        return checkProjectMapper.deleteByCheckId(checkId);
    }
}