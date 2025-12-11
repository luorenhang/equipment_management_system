<template>
  <div class="equipment-edit-page">
    <div class="page-header">
      <h1>{{ isEditMode ? '编辑设备' : '新增设备' }}</h1>
      <el-button @click="handleBack">返回</el-button>
    </div>
    
    <el-card class="edit-card">
      <equipment-form
        ref="equipmentFormRef"
        v-model="equipmentForm"
        @submit="handleSubmit"
      />
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { useRoute, useRouter } from 'vue-router'
import EquipmentForm from '../components/EquipmentForm.vue'
import { equipmentApi } from '../api/equipment'
import type { Equipment } from '../types/equipment'

const route = useRoute()
const router = useRouter()

const equipmentFormRef = ref()
const equipmentId = ref<number>(Number(route.params.id))
const isEditMode = computed(() => !isNaN(equipmentId.value) && equipmentId.value > 0)
const equipmentForm = reactive<Equipment>({
  storageLocation: '',
  equipmentName: '',
  equipmentNumber: '',
  productionLine: '',
  status: '正常',
  deviceType: '',
  equipmentStatus: '运行中',
  responsibleList: [
    {
      name: '',
      position: '',
      contact: '',
      responsibleType: '',
      notes: '',
      equipmentLedger: 0
    }
  ]
})

// 初始化数据
onMounted(() => {
  if (isEditMode.value) {
    fetchEquipmentDetail()
  }
})

// 获取设备详情（编辑模式下）
const fetchEquipmentDetail = async () => {
  try {
    const response = await equipmentApi.getEquipmentById(equipmentId.value)
    Object.assign(equipmentForm, response)
  } catch (error: any) {
    ElMessage.error('获取设备详情失败：' + (error.message || '未知错误'))
    router.push('/equipment')
  }
}

// 提交表单
const handleSubmit = async (formData: Equipment) => {
  try {
    console.log('提交的数据:', formData)
    if (isEditMode.value) {
      // 编辑模式
      await equipmentApi.updateEquipment(equipmentId.value, formData)
      ElMessage.success('设备更新成功')
    } else {
      // 新增模式
      await equipmentApi.createEquipment(formData)
      ElMessage.success('设备创建成功')
    }
    router.push('/equipment')
  } catch (error: any) {
    console.error('设备操作失败详情:', error)
    // 直接使用error.message，它已经经过axios拦截器处理
    ElMessage.error((isEditMode.value ? '更新' : '创建') + '设备失败：' + error.message)
  }
}

// 返回列表
const handleBack = () => {
  router.push('/equipment')
}
</script>

<style scoped>
.equipment-edit-page {
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

.edit-card {
  margin-bottom: 20px;
}
</style>