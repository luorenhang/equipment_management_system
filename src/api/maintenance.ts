import axios from '../utils/axios'
import type { EquipmentMaintenance } from '../types/equipment'

export const maintenanceApi = {
  // 创建设备保养
  createMaintenance: (maintenance: EquipmentMaintenance) => {
    // 将前端字段映射到后端实体类字段
    const maintenanceTimeValue = maintenance.startTime || maintenance.maintenanceTime;
    const backendMaintenance = {
      maintainNumber: maintenance.maintainNumber || maintenance.maintenanceNumber,
      equipmentName: maintenance.equipmentName,
      equipmentNumber: maintenance.equipmentNumber,
      deviceType: maintenance.deviceType,
      maintainUser: maintenance.maintainUser || maintenance.maintenancePerson,
      startTime: maintenanceTimeValue ? new Date(maintenanceTimeValue as string | Date).toISOString() : undefined,
      endTime: maintenance.endTime ? new Date(maintenance.endTime as string | Date).toISOString() : undefined,
      status: maintenance.status,
      enableStatus: maintenance.enableStatus || '1',
      operateStartTime: maintenance.operateStartTime ? new Date(maintenance.operateStartTime as string | Date).toISOString() : undefined,
      operateEndTime: maintenance.operateEndTime ? new Date(maintenance.operateEndTime as string | Date).toISOString() : undefined,
      barCode: maintenance.barCode || '',
      maintainResult: maintenance.maintainResult || '',
      maintainDept: maintenance.maintainDept || '',
      maintenanceItemList: maintenance.maintenanceItemList || []
    }
    return axios.post<number>('/maintenance', backendMaintenance) as unknown as Promise<number>
  },
  
  // 根据ID查询设备保养
  getMaintenanceById: (id: number) => {
    return axios.get<EquipmentMaintenance>(`/maintenance/${id}`) as unknown as Promise<EquipmentMaintenance>
  },
  
  // 查询设备保养列表
  getMaintenanceList: (params?: Partial<EquipmentMaintenance>) => {
    return axios.get<EquipmentMaintenance[]>('/maintenance', { params }) as unknown as Promise<EquipmentMaintenance[]>
  },
  
  // 更新设备保养信息
  updateMaintenance: (id: number, maintenance: EquipmentMaintenance) => {
    // 将前端字段映射到后端实体类字段
    const maintenanceTimeValue = maintenance.startTime || maintenance.maintenanceTime;
    const backendMaintenance = {
      maintainNumber: maintenance.maintainNumber || maintenance.maintenanceNumber,
      equipmentName: maintenance.equipmentName,
      equipmentNumber: maintenance.equipmentNumber,
      deviceType: maintenance.deviceType,
      maintainUser: maintenance.maintainUser || maintenance.maintenancePerson,
      startTime: maintenanceTimeValue ? new Date(maintenanceTimeValue as string | Date).toISOString() : undefined,
      endTime: maintenance.endTime ? new Date(maintenance.endTime as string | Date).toISOString() : undefined,
      status: maintenance.status,
      enableStatus: maintenance.enableStatus || '1',
      operateStartTime: maintenance.operateStartTime ? new Date(maintenance.operateStartTime as string | Date).toISOString() : undefined,
      operateEndTime: maintenance.operateEndTime ? new Date(maintenance.operateEndTime as string | Date).toISOString() : undefined,
      barCode: maintenance.barCode || '',
      maintainResult: maintenance.maintainResult || '',
      maintainDept: maintenance.maintainDept || '',
      maintenanceItemList: maintenance.maintenanceItemList || []
    }
    return axios.put<number>(`/maintenance/${id}`, backendMaintenance) as unknown as Promise<number>
  },
  
  // 删除设备保养
  deleteMaintenanceById: (id: number) => {
    return axios.delete<number>(`/maintenance/${id}`) as unknown as Promise<number>
  }
}
