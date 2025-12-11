package edu.ngd.order.equipment.service;

import edu.ngd.order.equipment.entity.Responsible;
import edu.ngd.order.equipment.mapper.ResponsibleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 设备责任人Service类
 */
@Service
public class ResponsibleService {
    
    @Autowired
    private ResponsibleMapper responsibleMapper;
    
    /**
     * 创建责任人
     * @param responsible 责任人信息
     * @return 责任人ID
     */
    public Integer createResponsible(Responsible responsible) {
        responsibleMapper.insert(responsible);
        return responsible.getId();
    }
    
    /**
     * 根据ID查询责任人
     * @param id 责任人ID
     * @return 责任人信息
     */
    public Responsible getResponsibleById(Integer id) {
        return responsibleMapper.selectById(id);
    }
    
    /**
     * 查询责任人列表
     * @param responsible 责任人信息（用于条件查询）
     * @return 责任人列表
     */
    public List<Responsible> getResponsibleList(Responsible responsible) {
        return responsibleMapper.selectList(responsible);
    }
    
    /**
     * 根据设备ID查询责任人列表
     * @param equipmentLedger 设备台账ID
     * @return 责任人列表
     */
    public List<Responsible> getResponsibleByEquipmentId(Integer equipmentLedger) {
        return responsibleMapper.selectByEquipmentId(equipmentLedger);
    }
    
    /**
     * 更新责任人信息
     * @param responsible 责任人信息
     * @return 影响行数
     */
    public int updateResponsible(Responsible responsible) {
        return responsibleMapper.updateById(responsible);
    }
    
    /**
     * 根据ID删除责任人
     * @param id 责任人ID
     * @return 影响行数
     */
    public int deleteResponsibleById(Integer id) {
        return responsibleMapper.deleteById(id);
    }
    
    /**
     * 根据设备ID删除责任人
     * @param equipmentLedger 设备台账ID
     * @return 影响行数
     */
    public int deleteResponsibleByEquipmentId(Integer equipmentLedger) {
        return responsibleMapper.deleteByEquipmentId(equipmentLedger);
    }
}