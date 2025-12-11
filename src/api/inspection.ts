import axios from '../utils/axios'
import type { EquipmentInspection, CheckProject } from '../types/equipment'

export const inspectionApi = {
  // 创建设备点检
  createInspection: (inspection: EquipmentInspection) => {
    return axios.post<number>('/inspection', inspection) as unknown as Promise<number>
  },
  
  // 根据ID查询设备点检
  getInspectionById: (id: number) => {
    return axios.get<EquipmentInspection>(`/inspection/${id}`) as unknown as Promise<EquipmentInspection>
  },
  
  // 查询设备点检列表
  getInspectionList: (params?: Partial<EquipmentInspection>) => {
    return axios.get<EquipmentInspection[]>('/inspection', { params }) as unknown as Promise<EquipmentInspection[]>
  },
  
  // 更新设备点检信息
  updateInspection: (id: number, inspection: EquipmentInspection) => {
    return axios.put<number>(`/inspection/${id}`, inspection) as unknown as Promise<number>
  },
  
  // 删除设备点检
  deleteInspectionById: (id: number) => {
    return axios.delete<number>(`/inspection/${id}`) as unknown as Promise<number>
  },
  
  // 创建点检项目
  createCheckProject: (project: CheckProject) => {
    return axios.post<number>('/check-project', project) as unknown as Promise<number>
  },
  
  // 更新点检项目
  updateCheckProject: (id: number, project: CheckProject) => {
    return axios.put<number>(`/check-project/${id}`, project) as unknown as Promise<number>
  },
  
  // 删除点检项目
  deleteCheckProjectById: (id: number) => {
    return axios.delete<number>(`/check-project/${id}`) as unknown as Promise<number>
  }
}
