import axios from '../utils/axios'
import type { EquipmentFaultCode } from '../types/equipment'

export const faultCodeApi = {
  // 创建故障代码
  createFaultCode: (faultCode: EquipmentFaultCode) => {
    return axios.post<number>('/fault-code', faultCode) as unknown as Promise<number>
  },
  
  // 根据ID查询故障代码
  getFaultCodeById: (id: number) => {
    return axios.get<EquipmentFaultCode>(`/fault-code/${id}`) as unknown as Promise<EquipmentFaultCode>
  },
  
  // 查询故障代码列表
  getFaultCodeList: (params?: Partial<EquipmentFaultCode>) => {
    return axios.get<EquipmentFaultCode[]>('/fault-code', { params }) as unknown as Promise<EquipmentFaultCode[]>
  },
  
  // 更新故障代码信息
  updateFaultCode: (id: number, faultCode: EquipmentFaultCode) => {
    return axios.put<number>(`/fault-code/${id}`, faultCode) as unknown as Promise<number>
  },
  
  // 删除故障代码
  deleteFaultCodeById: (id: number) => {
    return axios.delete<number>(`/fault-code/${id}`) as unknown as Promise<number>
  }
}
