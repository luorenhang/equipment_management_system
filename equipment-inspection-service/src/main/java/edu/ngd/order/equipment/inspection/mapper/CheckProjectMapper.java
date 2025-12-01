package edu.ngd.order.equipment.inspection.mapper;

import edu.ngd.order.equipment.inspection.entity.CheckProject;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 设备点检项目Mapper接口
 */
@Mapper
public interface CheckProjectMapper {
    /**
     * 创建点检项目
     * @param checkProject 点检项目信息
     * @return 影响行数
     */
    int insert(CheckProject checkProject);
    
    /**
     * 根据ID查询点检项目
     * @param id 点检项目ID
     * @return 点检项目信息
     */
    CheckProject selectById(Integer id);
    
    /**
     * 查询点检项目列表
     * @param checkProject 点检项目信息（用于条件查询）
     * @return 点检项目列表
     */
    List<CheckProject> selectList(CheckProject checkProject);
    
    /**
     * 根据点检ID查询点检项目列表
     * @param checkId 点检ID
     * @return 点检项目列表
     */
    List<CheckProject> selectByCheckId(Integer checkId);
    
    /**
     * 更新点检项目信息
     * @param checkProject 点检项目信息
     * @return 影响行数
     */
    int updateById(CheckProject checkProject);
    
    /**
     * 根据ID删除点检项目
     * @param id 点检项目ID
     * @return 影响行数
     */
    int deleteById(Integer id);
    
    /**
     * 根据点检ID删除点检项目
     * @param checkId 点检ID
     * @return 影响行数
     */
    int deleteByCheckId(Integer checkId);
}