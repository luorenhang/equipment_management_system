import axios from '../utils/axios'
import type { EquipmentRepair } from '../types/equipment'

// 定义后端设备维修类型，只包含必要的字段
export interface BackendEquipmentRepair {
  id?: number
  workTime?: string | Date
  repairCode?: string
  faultDes?: string
  repairResult?: number
  status?: number
  equipmentNumber?: string
  repairExplain?: string
  repairUser?: string
  equipmentName?: string
}



export const repairApi = {
  // 创建设备维修
  createRepair: (repair: EquipmentRepair) => {
    // 确保所有必填字段都有值
    if (!repair.repairNumber || !repair.equipmentNumber) {
      throw new Error('维修编号和设备编号是必填项')
    }
    
    // 将前端字段映射到后端实体类字段，只发送必要的字段
    // 格式化日期为后端需要的格式
    const formatDate = (date: string | Date): string => {
      if (!date) return '';
      const d = new Date(date);
      return `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}-${String(d.getDate()).padStart(2, '0')} ${String(d.getHours()).padStart(2, '0')}:${String(d.getMinutes()).padStart(2, '0')}:${String(d.getSeconds()).padStart(2, '0')}`;
    };
    
    const backendRepair: BackendEquipmentRepair = {
      workTime: formatDate(repair.repairTime), // 派工时间映射为前端的维修时间，格式化日期
      repairCode: repair.repairNumber, // 故障代码映射为前端的维修编号
      faultDes: repair.faultDescription, // 故障描述映射为前端的故障描述
      repairResult: repair.repairResult === '已修复' ? 1 : 0, // 将字符串转换为数字
      status: repair.status === '待维修' ? 1 : repair.status === '维修中' ? 2 : repair.status === '已完成' ? 3 : 4, // 将状态字符串转换为数字
      equipmentNumber: repair.equipmentNumber,
      repairExplain: repair.repairContent, // 维修情况说明映射为前端的维修内容
      repairUser: repair.repairPerson, // 维修人员映射为前端的维修人
      equipmentName: repair.equipmentName
    }
    return axios.post('/repair', backendRepair) as Promise<number>
  },
  
  // 根据ID查询设备维修
  getRepairById: (id: number) => {
    return axios.get(`/repair/${id}`) as Promise<BackendEquipmentRepair>
  },
  
  // 查询设备维修列表
  getRepairList: (params?: any) => {
    return axios.get('/repair', { params }) as Promise<BackendEquipmentRepair[]>
  },
  
  // 更新设备维修信息
  updateRepair: (id: number, repair: EquipmentRepair) => {
    // 确保所有必填字段都有值
    if (!repair.repairNumber || !repair.equipmentNumber) {
      throw new Error('维修编号和设备编号是必填项')
    }
    
    // 将前端字段映射到后端实体类字段，只发送必要的字段
    // 格式化日期为后端需要的格式
    const formatDate = (date: string | Date): string => {
      if (!date) return '';
      const d = new Date(date);
      return `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}-${String(d.getDate()).padStart(2, '0')} ${String(d.getHours()).padStart(2, '0')}:${String(d.getMinutes()).padStart(2, '0')}:${String(d.getSeconds()).padStart(2, '0')}`;
    };
    
    const backendRepair: BackendEquipmentRepair = {
      workTime: formatDate(repair.repairTime), // 派工时间映射为前端的维修时间，格式化日期
      repairCode: repair.repairNumber, // 故障代码映射为前端的维修编号
      faultDes: repair.faultDescription, // 故障描述映射为前端的故障描述
      repairResult: repair.repairResult === '已修复' ? 1 : 0, // 将字符串转换为数字
      status: repair.status === '待维修' ? 1 : repair.status === '维修中' ? 2 : repair.status === '已完成' ? 3 : 4, // 将状态字符串转换为数字
      equipmentNumber: repair.equipmentNumber,
      repairExplain: repair.repairContent, // 维修情况说明映射为前端的维修内容
      repairUser: repair.repairPerson, // 维修人员映射为前端的维修人
      equipmentName: repair.equipmentName
    }
    return axios.put(`/repair/${id}`, backendRepair) as Promise<number>
  },
  
  // 删除设备维修
  deleteRepairById: (id: number) => {
    return axios.delete(`/repair/${id}`) as Promise<number>
  }
}
