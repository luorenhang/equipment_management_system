import axios from '../utils/axios'
import type { Equipment } from '../types/equipment'

export const equipmentApi = {
  // 创建设备
  createEquipment: (equipment: Equipment) => {
    // 直接发送所有设备数据到后端
    return axios.post<Equipment>('/equipment', equipment) as unknown as Promise<Equipment>
  },
  
  // 根据ID查询设备
  getEquipmentById: (id: number) => {
    return axios.get<Equipment>(`/equipment/${id}`) as unknown as Promise<Equipment>
  },
  
  // 查询设备列表
  getEquipmentList: (params?: Partial<Equipment>) => {
    return axios.get<Equipment[]>('/equipment', { params }) as unknown as Promise<Equipment[]>
  },
  
  // 更新设备信息
  updateEquipment: (id: number, equipment: Equipment) => {
    return axios.put<Equipment>(`/equipment/${id}`, equipment) as unknown as Promise<Equipment>
  },
  
  // 删除设备
  deleteEquipmentById: (id: number) => {
    return axios.delete<Equipment>(`/equipment/${id}`) as unknown as Promise<Equipment>
  }
}