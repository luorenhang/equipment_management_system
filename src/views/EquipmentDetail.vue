<template>
  <div class="equipment-detail-page">
    <div class="page-header">
      <h1>设备详情</h1>
      <div>
        <el-button @click="handleEdit">编辑</el-button>
        <el-button @click="handleBack">返回</el-button>
      </div>
    </div>
    
    <el-card v-loading="loading" class="detail-card">
      <el-descriptions :column="1" border>
        <el-descriptions-item label="设备名称">{{ equipmentDetail?.equipmentName }}</el-descriptions-item>
        <el-descriptions-item label="设备编号">{{ equipmentDetail?.equipmentNumber }}</el-descriptions-item>
        <el-descriptions-item label="存放地点">{{ equipmentDetail?.storageLocation }}</el-descriptions-item>
        <el-descriptions-item label="产线">{{ equipmentDetail?.productionLine }}</el-descriptions-item>
        <el-descriptions-item label="设备类型">{{ equipmentDetail?.deviceType }}</el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag
            :type="
              equipmentDetail?.status === '正常' ? 'success' :
              equipmentDetail?.status === '维修中' ? 'warning' : 'danger'
            "
          >
            {{ equipmentDetail?.status }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="设备状态">
          <el-tag
            :type="
              equipmentDetail?.equipmentStatus === '运行中' ? 'success' :
              equipmentDetail?.equipmentStatus === '待机' ? 'info' : 'danger'
            "
          >
            {{ equipmentDetail?.equipmentStatus }}
          </el-tag>
        </el-descriptions-item>
      </el-descriptions>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useRoute, useRouter } from 'vue-router'
import { equipmentApi } from '../api/equipment'
import type { Equipment } from '../types/equipment'

const route = useRoute()
const router = useRouter()

const loading = ref(false)
const equipmentDetail = ref<Equipment | null>(null)
const equipmentId = ref<number>(Number(route.params.id))

// 初始化数据
onMounted(() => {
  fetchEquipmentDetail()
})

// 获取设备详情
  const fetchEquipmentDetail = async () => {
    loading.value = true
    try {
      const response = await equipmentApi.getEquipmentById(equipmentId.value)
      equipmentDetail.value = response // 直接赋值，axios拦截器已经处理了响应数据
    } catch (error: any) {
      ElMessage.error('获取设备详情失败：' + (error.message || '未知错误'))
      router.push('/equipment')
    } finally {
      loading.value = false
    }
  }


// 编辑设备
const handleEdit = () => {
  router.push(`/equipment/edit/${equipmentId.value}`)
}

// 返回列表
const handleBack = () => {
  router.push('/equipment')
}
</script>

<style scoped>
.equipment-detail-page {
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

.responsible-section {
  margin-top: 30px;
}

.responsible-section h3 {
  margin-bottom: 15px;
  font-size: 18px;
  color: #333;
}
</style>