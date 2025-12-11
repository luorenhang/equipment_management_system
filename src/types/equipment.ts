// 责任人类型
export interface Responsible {
  id?: number
  name: string
  position: string
  contact: string
  responsibleType: string
  notes: string
  equipmentLedger: number
}

// 设备类型
export interface Equipment {
  id?: number
  storageLocation: string
  equipmentName: string
  equipmentNumber: string
  productionLine: string
  status: string
  deviceType: string
  equipmentStatus: string
  responsibleList?: Responsible[]
}

// 故障代码类型
export interface EquipmentFaultCode {
  id?: number
  codeDescribe: string
  status: number
  code: string
  codeRemark: string
}

// 点检项目类型
export interface CheckProject {
  id?: number
  checkId: number
  reason: string
  remark: string
  status: number
  checkProject: string
  checkContent: string
  checkResult: string
  checkStandard: string
}

// 设备点检类型
export interface EquipmentInspection {
  id?: number
  checkNumber: string
  endTime: string | Date
  startTime: string | Date
  operateStartTime: string | Date
  operateEndTime: string | Date
  equipmentName: string
  equipmentNumber: string
  deviceType: string
  checkResult: string
  checkUser: string
  enableStatus: string
  barCode: string
  status: string
  checkProjectList: CheckProject[]
}

// 保养项目类型
export interface MaintenanceItem {
  id?: number
  maintenanceItem: string
  maintenanceContent: string
  maintenanceResult: string
  remark: string
  status: number
}

// 设备保养类型
export interface EquipmentMaintenance {
  id?: number
  maintainNumber?: string
  maintenanceNumber?: string
  equipmentName: string
  equipmentNumber: string
  deviceType: string
  maintainUser?: string
  maintenancePerson?: string
  startTime?: string | Date
  maintenanceTime?: string | Date
  endTime?: string | Date
  status: string
  maintenanceCycle?: number
  enableStatus?: string
  operateStartTime?: string | Date
  operateEndTime?: string | Date
  barCode?: string
  maintainResult?: string
  maintainDept?: string
  maintenanceItemList: MaintenanceItem[]
}

// 设备维修类型
export interface EquipmentRepair {
  id?: number
  repairNumber: string
  equipmentName: string
  equipmentNumber: string
  faultType: string
  faultDescription: string
  repairPerson: string
  repairTime: string | Date
  status: string
  repairContent: string
  repairResult: string
}

// 通用结果类型
export interface Result<T> {
  code: number
  message: string
  data: T
}

// 分页请求参数
export interface PaginationParams {
  currentPage: number
  pageSize: number
}

// 分页结果类型
export interface PaginationResult<T> {
  list: T[]
  total: number
  currentPage: number
  pageSize: number
}