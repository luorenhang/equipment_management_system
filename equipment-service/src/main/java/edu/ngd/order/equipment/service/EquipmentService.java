package edu.ngd.order.equipment.service;

import edu.ngd.order.equipment.entity.Equipment;
import edu.ngd.order.equipment.entity.Responsible;
import edu.ngd.order.equipment.mapper.EquipmentMapper;
import edu.ngd.order.equipment.mapper.ResponsibleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * 设备台账Service类
 */
@Service
public class EquipmentService {
    
    @Autowired
    private EquipmentMapper equipmentMapper;
    
    @Autowired
    private ResponsibleMapper responsibleMapper;
    
    /**
     * 创建设备
     * @param equipment 设备信息
     * @return 设备ID
     */
    @Transactional
    public Integer createEquipment(Equipment equipment) {
        // 检查设备基本信息
        if (equipment == null || equipment.getEquipmentName() == null || equipment.getEquipmentName().isEmpty()) {
            throw new IllegalArgumentException("设备名称不能为空");
        }
        
        // 处理设备状态值转换
        if (equipment.getStatus() != null) {
            // 将前端中文状态转换为后端状态码
            switch (equipment.getStatus()) {
                case "正常":
                    equipment.setStatus("01");
                    break;
                case "维修中":
                    equipment.setStatus("02");
                    break;
                case "停用":
                    equipment.setStatus("03");
                    break;
                default:
                    throw new IllegalArgumentException("无效的设备状态：" + equipment.getStatus());
            }
        }
        
        // 处理设备状态值转换
        if (equipment.getEquipmentStatus() != null) {
            // 将前端中文状态转换为后端状态码
            switch (equipment.getEquipmentStatus()) {
                case "运行中":
                    equipment.setEquipmentStatus("01");
                    break;
                case "待机":
                    equipment.setEquipmentStatus("02");
                    break;
                case "故障":
                    equipment.setEquipmentStatus("03");
                    break;
                default:
                    throw new IllegalArgumentException("无效的设备状态：" + equipment.getEquipmentStatus());
            }
        }
        
        // 执行设备插入
        int result = equipmentMapper.insert(equipment);
        if (result <= 0) {
            throw new RuntimeException("设备插入失败，影响行数为0");
        }
        
        // 验证设备ID是否生成
        if (equipment.getId() == null) {
            throw new RuntimeException("设备ID生成失败");
        }
        
        // 处理设备责任人信息
        if (equipment.getResponsibleList() != null && !equipment.getResponsibleList().isEmpty()) {
            for (Responsible responsible : equipment.getResponsibleList()) {
                responsible.setEquipmentLedger(equipment.getId());
                // 前端使用name，后端使用responsiblePerson
                if (responsible.getName() != null && !responsible.getName().isEmpty()) {
                    responsible.setResponsiblePerson(responsible.getName());
                } else {
                    // 如果前端没有提供姓名，设置默认值，避免数据库非空约束错误
                    responsible.setResponsiblePerson("无");
                }
                // 前端使用position，后端使用responsibleType
                if (responsible.getPosition() != null && !responsible.getPosition().isEmpty()) {
                    responsible.setResponsibleType(responsible.getPosition());
                } else {
                    // 如果前端没有提供职位，设置默认值，避免数据库非空约束错误
                    responsible.setResponsibleType("无");
                }
                // 前端使用contact，后端使用notes
                if (responsible.getContact() != null && !responsible.getContact().isEmpty()) {
                    responsible.setNotes(responsible.getContact());
                } else {
                    // 如果前端没有提供联系方式，设置默认值，避免数据库非空约束错误
                    responsible.setNotes("无");
                }
                // 确保责任人类型字段不为空
                if (responsible.getResponsibleType() == null || responsible.getResponsibleType().isEmpty()) {
                    responsible.setResponsibleType("设备责任人");
                }
                
                // 执行责任人插入
                int responsibleResult = responsibleMapper.insert(responsible);
                if (responsibleResult <= 0) {
                    throw new RuntimeException("责任人插入失败，影响行数为0");
                }
            }
        }
        
        return equipment.getId();
    }
    
    /**
     * 根据ID查询设备
     * @param id 设备ID
     * @return 设备信息
     */
    public Equipment getEquipmentById(Integer id) {
        Equipment equipment = equipmentMapper.selectById(id);
        if (equipment != null) {
            // 查询设备对应的责任人列表
            List<Responsible> responsibleList = responsibleMapper.selectByEquipmentId(equipment.getId());
            // 将数据库字段映射回前端字段
            for (Responsible responsible : responsibleList) {
                // 后端使用responsiblePerson，前端使用name
                responsible.setName(responsible.getResponsiblePerson() != null ? responsible.getResponsiblePerson() : "");
                // 后端使用responsibleType，前端使用position
                responsible.setPosition(responsible.getResponsibleType() != null ? responsible.getResponsibleType() : "");
                // 后端使用notes，前端使用contact
                responsible.setContact(responsible.getNotes() != null ? responsible.getNotes() : "");
            }
            equipment.setResponsibleList(responsibleList);
            
            // 处理设备状态显示
            if (equipment.getStatus() != null) {
                // 将状态码转换为状态名称
                switch (equipment.getStatus()) {
                    case "01":
                        equipment.setStatus("正常");
                        break;
                    case "02":
                        equipment.setStatus("维修中");
                        break;
                    case "03":
                        equipment.setStatus("停用");
                        break;
                    default:
                        break;
                }
            }
            
            // 处理设备状态显示
            if (equipment.getEquipmentStatus() != null) {
                // 将设备状态码转换为状态名称
                switch (equipment.getEquipmentStatus()) {
                    case "01":
                        equipment.setEquipmentStatus("运行中");
                        break;
                    case "02":
                        equipment.setEquipmentStatus("待机");
                        break;
                    case "03":
                        equipment.setEquipmentStatus("故障");
                        break;
                    default:
                        break;
                }
            }
        }
        return equipment;
    }
    
    /**
     * 查询设备列表
     * @param equipment 设备信息（用于条件查询）
     * @return 设备列表
     */
    public List<Equipment> getEquipmentList(Equipment equipment) {
        List<Equipment> equipmentList = equipmentMapper.selectList(equipment);
        // 为每个设备查询对应的责任人列表
        for (Equipment eq : equipmentList) {
            List<Responsible> responsibleList = responsibleMapper.selectByEquipmentId(eq.getId());
            // 将数据库字段映射回前端字段
            for (Responsible responsible : responsibleList) {
                // 后端使用responsiblePerson，前端使用name
                responsible.setName(responsible.getResponsiblePerson() != null ? responsible.getResponsiblePerson() : "");
                // 后端使用responsibleType，前端使用position
                responsible.setPosition(responsible.getResponsibleType() != null ? responsible.getResponsibleType() : "");
                // 后端使用notes，前端使用contact
                responsible.setContact(responsible.getNotes() != null ? responsible.getNotes() : "");
            }
            eq.setResponsibleList(responsibleList);
            
            // 处理设备状态显示
            if (eq.getStatus() != null) {
                // 将状态码转换为状态名称
                switch (eq.getStatus()) {
                    case "01":
                        eq.setStatus("正常");
                        break;
                    case "02":
                        eq.setStatus("维修中");
                        break;
                    case "03":
                        eq.setStatus("停用");
                        break;
                    default:
                        break;
                }
            }
            
            // 处理设备状态显示
            if (eq.getEquipmentStatus() != null) {
                // 将设备状态码转换为状态名称
                switch (eq.getEquipmentStatus()) {
                    case "01":
                        eq.setEquipmentStatus("运行中");
                        break;
                    case "02":
                        eq.setEquipmentStatus("待机");
                        break;
                    case "03":
                        eq.setEquipmentStatus("故障");
                        break;
                    default:
                        break;
                }
            }
        }
        return equipmentList;
    }
    
    /**
     * 更新设备信息
     * @param equipment 设备信息
     * @return 影响行数
     */
    @Transactional
    public int updateEquipment(Equipment equipment) {
        // 检查设备基本信息
        if (equipment == null || equipment.getId() == null) {
            throw new IllegalArgumentException("设备ID不能为空");
        }
        
        // 处理设备状态值转换
        if (equipment.getStatus() != null) {
            // 将前端中文状态转换为后端状态码
            switch (equipment.getStatus()) {
                case "正常":
                    equipment.setStatus("01");
                    break;
                case "维修中":
                    equipment.setStatus("02");
                    break;
                case "停用":
                    equipment.setStatus("03");
                    break;
                default:
                    throw new IllegalArgumentException("无效的设备状态：" + equipment.getStatus());
            }
        }
        
        // 处理设备状态值转换
        if (equipment.getEquipmentStatus() != null) {
            // 将前端中文状态转换为后端状态码
            switch (equipment.getEquipmentStatus()) {
                case "运行中":
                    equipment.setEquipmentStatus("01");
                    break;
                case "待机":
                    equipment.setEquipmentStatus("02");
                    break;
                case "故障":
                    equipment.setEquipmentStatus("03");
                    break;
                default:
                    throw new IllegalArgumentException("无效的设备状态：" + equipment.getEquipmentStatus());
            }
        }
        
        // 执行设备更新
        int result = equipmentMapper.updateById(equipment);
        if (result <= 0) {
            throw new RuntimeException("设备更新失败，影响行数为0");
        }
        
        // 处理设备责任人信息的更新
        if (equipment.getResponsibleList() != null) {
            // 先删除原有的责任人信息
            int deleteResult = responsibleMapper.deleteByEquipmentId(equipment.getId());
            if (deleteResult < 0) {
                throw new RuntimeException("删除责任人信息失败");
            }
            
            // 再插入新的责任人信息
            if (!equipment.getResponsibleList().isEmpty()) {
                for (Responsible responsible : equipment.getResponsibleList()) {
                    responsible.setEquipmentLedger(equipment.getId());
                    // 前端使用name，后端使用responsiblePerson
                    if (responsible.getName() != null && !responsible.getName().isEmpty()) {
                        responsible.setResponsiblePerson(responsible.getName());
                    } else {
                        responsible.setResponsiblePerson("无");
                    }
                    // 前端使用position，后端使用responsibleType
                    if (responsible.getPosition() != null && !responsible.getPosition().isEmpty()) {
                        responsible.setResponsibleType(responsible.getPosition());
                    } else {
                        responsible.setResponsibleType("无");
                    }
                    // 前端使用contact，后端使用notes
                    if (responsible.getContact() != null && !responsible.getContact().isEmpty()) {
                        responsible.setNotes(responsible.getContact());
                    } else {
                        responsible.setNotes("无");
                    }
                    // 确保责任人类型字段不为空
                    if (responsible.getResponsibleType() == null || responsible.getResponsibleType().isEmpty()) {
                        responsible.setResponsibleType("设备责任人");
                    }
                    
                    // 执行责任人插入
                    int responsibleResult = responsibleMapper.insert(responsible);
                    if (responsibleResult <= 0) {
                        throw new RuntimeException("责任人插入失败，影响行数为0");
                    }
                }
            }
        }
        
        return result;
    }
    
    /**
     * 根据ID删除设备
     * @param id 设备ID
     * @return 影响行数
     */
    @Transactional
    public int deleteEquipmentById(Integer id) {
        // 检查设备ID
        if (id == null) {
            throw new IllegalArgumentException("设备ID不能为空");
        }
        
        // 先删除设备对应的责任人
        int responsibleResult = responsibleMapper.deleteByEquipmentId(id);
        if (responsibleResult < 0) {
            throw new RuntimeException("删除责任人信息失败");
        }
        
        // 再删除设备
        int equipmentResult = equipmentMapper.deleteById(id);
        if (equipmentResult <= 0) {
            throw new RuntimeException("设备删除失败，影响行数为0");
        }
        
        return equipmentResult;
    }
}