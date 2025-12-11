<template>
  <div class="inspection-detail-page">
    <div class="page-header">
      <h1>设备点检详情</h1>
      <div>
        <el-button @click="handleEdit">编辑</el-button>
        <el-button @click="handleBack">返回</el-button>
      </div>
    </div>
    
    <el-card v-loading="loading" class="detail-card">
      <el-descriptions :column="1" border>
        <el-descriptions-item label="点检编号">{{ inspectionDetail?.checkNumber }}</el-descriptions-item>
        <el-descriptions-item label="设备名称">{{ inspectionDetail?.equipmentName }}</el-descriptions-item>
        <el-descriptions-item label="设备编号">{{ inspectionDetail?.equipmentNumber }}</el-descriptions-item>
        <el-descriptions-item label="设备类型">{{ inspectionDetail?.deviceType }}</el-descriptions-item>
        <el-descriptions-item label="计划开始时间">{{ inspectionDetail?.startTime }}</el-descriptions-item>
        <el-descriptions-item label="计划结束时间">{{ inspectionDetail?.endTime }}</el-descriptions-item>
        <el-descriptions-item label="点检人">{{ inspectionDetail?.checkUser }}</el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag
            :type="
              inspectionDetail?.status === '待执行' ? 'warning' :
              inspectionDetail?.status === '执行中' ? 'info' :
              inspectionDetail?.status === '已完成' ? 'success' : 'danger'
            "
          >
            {{ inspectionDetail?.status }}
          </el-tag>
        </el-descriptions-item>
      </el-descriptions>
      
      <!-- 点检项目 -->
      <div class="check-project-section">
        <h3>点检项目</h3>
        <el-table :data="inspectionDetail?.checkProjectList || []" stripe>
          <el-table-column prop="checkProject" label="点检项目" />
          <el-table-column prop="checkContent" label="点检内容" />
          <el-table-column prop="checkStandard" label="点检标准" />
          <el-table-column prop="checkResult" label="点检结果">
            <template #default="{ row }">
              <el-tag :type="row.checkResult === '合格' ? 'success' : 'danger'">
                {{ row.checkResult }}
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
import { inspectionApi } from '../api/inspection'
import type { EquipmentInspection } from '../types/equipment'

const route = useRoute()
const router = useRouter()

const loading = ref(false)
const inspectionDetail = ref<EquipmentInspection | null>(null)
const inspectionId = ref<number>(Number(route.params.id))

// 初始化数据
onMounted(() => {
  fetchInspectionDetail()
})

// 获取设备点检详情
const fetchInspectionDetail = async () => {
  loading.value = true
  try {
    const response = await inspectionApi.getInspectionById(inspectionId.value)
    inspectionDetail.value = response
  } catch (error: any) {
    ElMessage.error('获取设备点检详情失败：' + (error.message || '未知错误'))
    router.push('/inspection')
  } finally {
    loading.value = false
  }
}

// 编辑设备点检
const handleEdit = () => {
  router.push(`/inspection/edit/${inspectionId.value}`)
}

// 返回列表
const handleBack = () => {
  router.push('/inspection')
}
</script>

<style scoped>
.inspection-detail-page {
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

.check-project-section {
  margin-top: 30px;
}

.check-project-section h3 {
  margin-bottom: 15px;
  font-size: 18px;
  color: #333;
}
</style>