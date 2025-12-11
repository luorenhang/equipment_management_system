<template>
  <div class="maintenance-detail-page">
    <div class="page-header">
      <h1>设备保养详情</h1>
      <div>
        <el-button @click="handleEdit">编辑</el-button>
        <el-button @click="handleBack">返回</el-button>
      </div>
    </div>
    
    <el-card v-loading="loading" class="detail-card">
      <el-descriptions :column="1" border>
        <el-descriptions-item label="保养编号">{{ maintenanceDetail?.maintenanceNumber }}</el-descriptions-item>
        <el-descriptions-item label="设备名称">{{ maintenanceDetail?.equipmentName }}</el-descriptions-item>
        <el-descriptions-item label="设备编号">{{ maintenanceDetail?.equipmentNumber }}</el-descriptions-item>
        <el-descriptions-item label="设备类型">{{ maintenanceDetail?.deviceType }}</el-descriptions-item>
        <el-descriptions-item label="保养人">{{ maintenanceDetail?.maintenancePerson }}</el-descriptions-item>
        <el-descriptions-item label="保养时间">{{ maintenanceDetail?.maintenanceTime }}</el-descriptions-item>
        <el-descriptions-item label="保养周期">{{ maintenanceDetail?.maintenanceCycle }}天</el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag
            :type="
              maintenanceDetail?.status === '0' || maintenanceDetail?.status === '待执行' ? 'warning' :
              maintenanceDetail?.status === '1' || maintenanceDetail?.status === '执行中' ? 'info' :
              maintenanceDetail?.status === '2' || maintenanceDetail?.status === '已完成' ? 'success' : 'danger'
            "
          >
            {{ 
              maintenanceDetail?.status === '0' ? '待执行' :
              maintenanceDetail?.status === '1' ? '执行中' :
              maintenanceDetail?.status === '2' ? '已完成' :
              maintenanceDetail?.status === '3' ? '已取消' :
              maintenanceDetail?.status
            }}
          </el-tag>
        </el-descriptions-item>
      </el-descriptions>
      
      <!-- 保养项目 -->
      <div class="maintenance-item-section">
        <h3>保养项目</h3>
        <el-table :data="maintenanceDetail?.maintenanceItemList || []" stripe>
          <el-table-column prop="maintenanceItem" label="保养项目" />
          <el-table-column prop="maintenanceContent" label="保养内容" />
          <el-table-column prop="maintenanceResult" label="保养结果">
            <template #default="{ row }">
              <el-tag :type="row.maintenanceResult === '合格' ? 'success' : 'danger'">
                {{ row.maintenanceResult }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="remark" label="备注" />
        </el-table>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useRoute, useRouter } from 'vue-router'
import { maintenanceApi } from '../api/maintenance'
import type { EquipmentMaintenance } from '../types/equipment'

const route = useRoute()
const router = useRouter()

const loading = ref(false)
const maintenanceDetail = ref<EquipmentMaintenance | null>(null)
const maintenanceId = ref<number>(Number(route.params.id))

// 初始化数据
onMounted(() => {
  fetchMaintenanceDetail()
})

// 获取设备保养详情
const fetchMaintenanceDetail = async () => {
  loading.value = true
  try {
    const response = await maintenanceApi.getMaintenanceById(maintenanceId.value)
    
    // 将后端字段映射到前端字段
    const mappedDetail = {
      id: response.id,
      maintenanceNumber: response.maintainNumber,
      equipmentName: response.equipmentName,
      equipmentNumber: response.equipmentNumber,
      deviceType: response.deviceType,
      maintenancePerson: response.maintainUser,
      maintenanceTime: response.startTime,
      status: response.status,
      // 保留其他字段
      startTime: response.startTime,
      endTime: response.endTime,
      maintainUser: response.maintainUser,
      maintainNumber: response.maintainNumber,
      enableStatus: response.enableStatus,
      operateStartTime: response.operateStartTime,
      operateEndTime: response.operateEndTime,
      barCode: response.barCode,
      maintainResult: response.maintainResult,
      maintainDept: response.maintainDept,
      maintenanceItemList: response.maintenanceItemList || []
    }
    
    maintenanceDetail.value = mappedDetail as EquipmentMaintenance
  } catch (error: any) {
    ElMessage.error('获取设备保养详情失败：' + (error.message || '未知错误'))
    router.push('/maintenance')
  } finally {
    loading.value = false
  }
}

// 编辑设备保养
const handleEdit = () => {
  router.push(`/maintenance/edit/${maintenanceId.value}`)
}

// 返回列表
const handleBack = () => {
  router.push('/maintenance')
}
</script>

<style scoped>
.maintenance-detail-page {
  background-color: white;
  border-radius: 4px;
  padding: 20px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.page-header h1 {
  margin: 0;
  font-size: 24px;
  color: #333;
}

.detail-card {
  margin-bottom: 20px;
}

.maintenance-item-section {
  margin-top: 30px;
}

.maintenance-item-section h3 {
  margin-bottom: 15px;
  font-size: 18px;
  color: #333;
}
</style>