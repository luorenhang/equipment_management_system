<template>
  <div class="maintenance-list-container">
    <el-card shadow="never" class="page-card">
      <template #header>
        <div class="card-header">
          <h2 class="page-title">设备保养管理</h2>
        </div>
      </template>
      
      <BaseTable
        :loading="loading"
        :dataList="maintenanceList"
        :columns="columns"
        :pagination="pagination"
        @search="handleSearch"
        @create="handleCreate"
        @view="handleView"
        @edit="handleEdit"
        @delete="handleDelete"
        @batch-delete="handleBatchDelete"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import BaseTable from '../components/BaseTable.vue'
import { maintenanceApi } from '../api/maintenance'
import { usePolling } from '../composables/usePolling'
import type { EquipmentMaintenance } from '../types/equipment'

const router = useRouter()
const loading = ref(false)
const searchQuery = ref('')
const maintenanceList = ref<EquipmentMaintenance[]>([])
const pagination = reactive({
  currentPage: 1,
  pageSize: 10,
  total: 0
})

// 表格列配置
const columns = ref([
  {
    prop: 'maintenanceNumber',
    label: '保养编号',
    minWidth: 150
  },
  {
    prop: 'equipmentName',
    label: '设备名称',
    minWidth: 150
  },
  {
    prop: 'equipmentNumber',
    label: '设备编号',
    minWidth: 150
  },
  {
    prop: 'deviceType',
    label: '设备类型',
    minWidth: 120
  },
  {
    prop: 'maintenanceTime',
    label: '保养时间',
    minWidth: 180
  },
  {
    prop: 'maintenancePerson',
    label: '保养人',
    minWidth: 120
  },
  {
    prop: 'status',
    label: '状态',
    minWidth: 100
  }
])

// 加载设备保养列表
const loadMaintenanceList = async () => {
  loading.value = true
  try {
    const params: any = {
      page: pagination.currentPage,
      pageSize: pagination.pageSize
    }
    if (searchQuery.value) {
      params.equipmentName = searchQuery.value
      params.equipmentNumber = searchQuery.value
    }
    const result = await maintenanceApi.getMaintenanceList(params) as any
    
    // 处理返回的数据，将后端字段映射到前端字段
    let maintenanceData: EquipmentMaintenance[] = []
    
    if (Array.isArray(result)) {
      maintenanceData = result.map((item: any) => ({
        id: item.id,
        maintenanceNumber: item.maintainNumber,
        equipmentName: item.equipmentName,
        equipmentNumber: item.equipmentNumber,
        deviceType: item.deviceType,
        maintenanceTime: item.startTime,
        maintenancePerson: item.maintainUser,
        status: item.status,
        // 保留其他字段
        startTime: item.startTime,
        endTime: item.endTime,
        maintainUser: item.maintainUser,
        maintainNumber: item.maintainNumber,
        enableStatus: item.enableStatus,
        operateStartTime: item.operateStartTime,
        operateEndTime: item.operateEndTime,
        barCode: item.barCode,
        maintainResult: item.maintainResult,
        maintainDept: item.maintainDept,
        maintenanceItemList: item.maintenanceItemList || []
      }))
      pagination.total = result.length
    }
    
    maintenanceList.value = maintenanceData
  } catch (error) {
    const errorMsg = error instanceof Error ? error.message : '加载设备保养列表失败'
    ElMessage.error(errorMsg)
    console.error('加载设备保养列表失败:', error)
    // 确保错误时列表为空
    maintenanceList.value = []
    pagination.total = 0
  } finally {
    loading.value = false
  }
}

// 使用定时轮询，每30秒更新一次数据
usePolling(loadMaintenanceList, 30000)

// 搜索
const handleSearch = (query: string) => {
  searchQuery.value = query
  pagination.currentPage = 1
  loadMaintenanceList()
}

// 新增
const handleCreate = () => {
  router.push('/maintenance/create')
}

// 查看详情
const handleView = (id: number) => {
  router.push(`/maintenance/${id}`)
}

// 编辑
const handleEdit = (id: number) => {
  router.push(`/maintenance/edit/${id}`)
}

// 删除
const handleDelete = async (id: number) => {
  try {
    await maintenanceApi.deleteMaintenanceById(id)
    ElMessage.success('删除成功')
    loadMaintenanceList()
  } catch (error) {
    ElMessage.error('删除失败')
    console.error('删除设备保养失败:', error)
  }
}

// 批量删除
const handleBatchDelete = async (ids: number[]) => {
  try {
    for (const id of ids) {
      await maintenanceApi.deleteMaintenanceById(id)
    }
    ElMessage.success('批量删除成功')
    loadMaintenanceList()
  } catch (error) {
    ElMessage.error('批量删除失败')
    console.error('批量删除设备保养失败:', error)
  }
}

// 分页大小变化
const handleSizeChange = (size: number) => {
  pagination.pageSize = size
  loadMaintenanceList()
}

// 当前页变化
const handleCurrentChange = (page: number) => {
  pagination.currentPage = page
  loadMaintenanceList()
}
</script>

<style scoped>
.maintenance-list-container {
  width: 100%;
}

.page-card {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.page-title {
  margin: 0;
  font-size: 20px;
  font-weight: bold;
  color: #333;
}
</style>