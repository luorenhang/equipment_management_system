<template>
  <div class="repair-detail-page">
    <div class="page-header">
      <h1>设备维修详情</h1>
      <div>
        <el-button @click="handleEdit">编辑</el-button>
        <el-button @click="handleBack">返回</el-button>
      </div>
    </div>
    
    <el-card v-loading="loading" class="detail-card">
      <el-descriptions :column="1" border>
        <el-descriptions-item label="维修编号">{{ repairDetail?.repairNumber }}</el-descriptions-item>
        <el-descriptions-item label="设备名称">{{ repairDetail?.equipmentName }}</el-descriptions-item>
        <el-descriptions-item label="设备编号">{{ repairDetail?.equipmentNumber }}</el-descriptions-item>
        <el-descriptions-item label="故障类型">{{ repairDetail?.faultType }}</el-descriptions-item>
        <el-descriptions-item label="故障描述">{{ repairDetail?.faultDescription }}</el-descriptions-item>
        <el-descriptions-item label="维修人">{{ repairDetail?.repairPerson }}</el-descriptions-item>
        <el-descriptions-item label="维修时间">{{ repairDetail?.repairTime }}</el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag
            :type="
              repairDetail?.status === '待维修' ? 'warning' :
              repairDetail?.status === '维修中' ? 'info' :
              repairDetail?.status === '已完成' ? 'success' : 'danger'
            "
          >
            {{ repairDetail?.status }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="维修结果">
          <el-tag :type="repairDetail?.repairResult === '已修复' ? 'success' : 'danger'">
            {{ repairDetail?.repairResult }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="维修内容">{{ repairDetail?.repairContent }}</el-descriptions-item>
      </el-descriptions>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useRoute, useRouter } from 'vue-router'
import { repairApi } from '../api/repair'
import type { EquipmentRepair } from '../types/equipment'

const route = useRoute()
const router = useRouter()

const loading = ref(false)
const repairDetail = ref<EquipmentRepair | null>(null)
const repairId = ref<number>(Number(route.params.id))

// 初始化数据
onMounted(() => {
  fetchRepairDetail()
})

// 获取设备维修详情
const fetchRepairDetail = async () => {
  loading.value = true
  try {
    const result = await repairApi.getRepairById(repairId.value)
    // 添加调试日志，查看后端返回的数据
    console.log('维修详情API返回结果:', result)
    
    // 状态映射
    const statusMap: Record<number, string> = {
      1: '待维修',
      2: '维修中',
      3: '已完成',
      4: '已取消'
    }
    // 维修结果映射
    const repairResultMap: Record<number, string> = {
      0: '未修复',
      1: '已修复'
    }
    
    // 创建维修详情对象
    repairDetail.value = {
      id: result.id || 0,
      repairNumber: result.repairCode || '', // 维修编号映射为后端的故障代码
      equipmentName: result.equipmentName || '',
      equipmentNumber: result.equipmentNumber || '',
      faultType: result.repairCode || '', // 故障类型映射为后端的故障代码
      faultDescription: result.faultDes || '', // 故障描述映射为后端的故障描述
      repairPerson: result.repairUser || '', // 维修人员映射为后端的维修人员
      repairTime: result.workTime || '', // 维修时间映射为后端的派工时间
      status: statusMap[result.status || 1] || '待维修', // 状态映射为中文
      repairContent: result.repairExplain || '', // 维修内容映射为后端的维修情况说明
      repairResult: repairResultMap[result.repairResult || 1] || '已修复' // 维修结果映射为中文
    }
    
    // 添加调试日志，查看映射后的数据
    console.log('映射后的维修详情:', repairDetail.value)
  } catch (error: any) {
    console.error('获取设备维修详情失败:', error)
    ElMessage.error('获取设备维修详情失败：' + (error.message || '未知错误'))
    router.push('/repair')
  } finally {
    loading.value = false
  }
}

// 编辑设备维修
const handleEdit = () => {
  router.push(`/repair/edit/${repairId.value}`)
}

// 返回列表
const handleBack = () => {
  router.push('/repair')
}
</script>

<style scoped>
.repair-detail-page {
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
</style>