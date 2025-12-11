package edu.ngd.order.equipment.mapper;

import edu.ngd.order.equipment.entity.Responsible;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 设备责任人Mapper接口
 */
@Mapper
public interface ResponsibleMapper {
    /**
     * 创建责任人
     * @param responsible 责任人信息
     * @return 影响行数
     */
    int insert(Responsible responsible);
    
    /**
     * 根据ID查询责任人
     * @param id 责任人ID
     * @return 责任人信息
     */
    Responsible selectById(Integer id);
    
    /**
     * 查询责任人列表
     * @param responsible 责任人信息（用于条件查询）
     * @return 责任人列表
     */
    List<Responsible> selectList(Responsible responsible);
    
    /**
     * 根据设备ID查询责任人列表
     * @param equipmentLedger 设备台账ID
     * @return 责任人列表
     */
    List<Responsible> selectByEquipmentId(Integer equipmentLedger);
    
    /**
     * 更新责任人信息
     * @param responsible 责任人信息
     * @return 影响行数
     */
    int updateById(Responsible responsible);
    
    /**
     * 根据ID删除责任人
     * @param id 责任人ID
     * @return 影响行数
     */
    int deleteById(Integer id);
    
    /**
     * 根据设备ID删除责任人
     * @param equipmentLedger 设备台账ID
     * @return 影响行数
     */
    int deleteByEquipmentId(Integer equipmentLedger);
}