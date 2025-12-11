<template>
  <div class="repair-edit-container">
    <el-card shadow="never" class="page-card">
      <template #header>
        <div class="card-header">
          <h2 class="page-title">{{ isEdit ? '编辑设备维修' : '创建设备维修' }}</h2>
        </div>
      </template>
      
      <el-form
        ref="repairFormRef"
        :model="repairForm"
        :rules="rules"
        label-position="right"
        label-width="120px"
        class="repair-form"
      >
        <!-- 基本信息 -->
        <el-divider content-position="left">基本信息</el-divider>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="维修编号" prop="repairNumber">
              <el-input
                v-model="repairForm.repairNumber"
                placeholder="请输入维修编号"
                clearable
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="设备名称" prop="equipmentName">
              <el-input
                v-model="repairForm.equipmentName"
                placeholder="请输入设备名称"
                clearable
              />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="设备编号" prop="equipmentNumber">
              <el-input
                v-model="repairForm.equipmentNumber"
                placeholder="请输入设备编号"
                clearable
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="故障类型" prop="faultType">
              <el-input
                v-model="repairForm.faultType"
                placeholder="请输入故障类型"
                clearable
              />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="故障描述" prop="faultDescription">
              <el-input
                v-model="repairForm.faultDescription"
                placeholder="请输入故障描述"
                clearable
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="维修人" prop="repairPerson">
              <el-input
                v-model="repairForm.repairPerson"
                placeholder="请输入维修人"
                clearable
              />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="维修时间" prop="repairTime">
              <el-date-picker
                v-model="repairForm.repairTime"
                type="datetime"
                placeholder="请选择维修时间"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-select
                v-model="repairForm.status"
                placeholder="请选择状态"
              >
                <el-option label="待维修" value="待维修" />
                <el-option label="维修中" value="维修中" />
                <el-option label="已完成" value="已完成" />
                <el-option label="已取消" value="已取消" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        
        <!-- 维修内容 -->
        <el-divider content-position="left">维修内容</el-divider>
        
        <el-form-item label="维修内容" prop="repairContent">
          <el-input
            v-model="repairForm.repairContent"
            placeholder="请输入维修内容"
            type="textarea"
            rows="4"
          />
        </el-form-item>
        
        <el-form-item label="维修结果" prop="repairResult">
          <el-select
            v-model="repairForm.repairResult"
            placeholder="请选择维修结果"
          >
            <el-option label="已修复" value="已修复" />
            <el-option label="未修复" value="未修复" />
          </el-select>
        </el-form-item>
        
        <el-form-item class="form-actions">
          <el-button type="primary" @click="handleSubmit" :loading="loading">
            {{ isEdit ? '保存修改' : '创建设备维修' }}
          </el-button>
          <el-button @click="handleCancel">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage, type FormInstance } from 'element-plus'
import { repairApi } from '../api/repair'
import { equipmentApi } from '../api/equipment'
import type { EquipmentRepair } from '../types/equipment'

const router = useRouter()
const route = useRoute()
const loading = ref(false)
const repairFormRef = ref<FormInstance>()
const id = computed(() => route.params.id as string)
const isEdit = computed(() => !!id.value)

// 表单数据
const repairForm = reactive<EquipmentRepair>({
  repairNumber: '',
  equipmentName: '',
  equipmentNumber: '',
  faultType: '',
  faultDescription: '',
  repairPerson: '',
  repairTime: '',
  status: '待维修',
  repairContent: '',
  repairResult: '已修复'
})

// 表单验证规则
const rules = reactive({
  repairNumber: [
    { required: true, message: '请输入维修编号', trigger: 'blur' }
  ],
  equipmentName: [
    { required: true, message: '请输入设备名称', trigger: 'blur' }
  ],
  equipmentNumber: [
    { required: true, message: '请输入设备编号', trigger: 'blur' }
  ],
  faultType: [
    { required: true, message: '请输入故障类型', trigger: 'blur' }
  ],
  faultDescription: [
    { required: true, message: '请输入故障描述', trigger: 'blur' }
  ],
  repairPerson: [
    { required: true, message: '请输入维修人', trigger: 'blur' }
  ],
  repairTime: [
    { required: true, message: '请选择维修时间', trigger: 'change' }
  ],
  status: [
    { required: true, message: '请选择状态', trigger: 'change' }
  ],
  repairContent: [
    { required: true, message: '请输入维修内容', trigger: 'blur' }
  ],
  repairResult: [
    { required: true, message: '请选择维修结果', trigger: 'change' }
  ]
})

// 加载设备维修详情
const loadRepairDetail = async () => {
  if (!isEdit.value) {
    return
  }
  
  loading.value = true
  try {
    const result = await repairApi.getRepairById(Number(id.value))
    // axios响应拦截器已直接返回data，无需再访问result.data
    // 将后端返回的数据映射为前端表单需要的格式
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
    Object.assign(repairForm, {
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
    })
  } catch (error) {
    ElMessage.error('加载设备维修详情失败')
    console.error('加载设备维修详情失败:', error)
    router.push('/repair')
  } finally {
    loading.value = false
  }
}

// 提交表单
const handleSubmit = async () => {
  if (!repairFormRef.value) return
  
  loading.value = true
  try {
    // 表单验证
    await repairFormRef.value.validate()
    
    if (isEdit.value) {
      await repairApi.updateRepair(Number(id.value), repairForm)
      ElMessage.success('修改成功')
    } else {
      await repairApi.createRepair(repairForm)
      ElMessage.success('创建成功')
    }
    router.push('/repair')
  } catch (error: any) {
    // 检查是否为表单验证错误
    if (error instanceof Error && error.name === 'ValidationError') {
      return
    }
    
    // 处理API返回的错误信息
    let errorMsg = ''
    if (error.response) {
      // 服务器返回了错误响应
      const { status, data } = error.response
      console.error('API错误响应:', error.response)
      
      if (status === 400) {
        // 400错误，参数验证失败
        if (data.message) {
          errorMsg = data.message
        } else if (data.code === 400) {
          errorMsg = data.data || '请求参数错误，请检查输入'
        } else {
          errorMsg = '请求参数错误，请检查输入格式是否正确'
        }
      } else if (status === 500) {
        // 500错误，服务器内部错误
        errorMsg = '服务器内部错误，请稍后重试'
      } else {
        // 其他错误
        errorMsg = `请求失败，状态码：${status}`
      }
    } else if (error.request) {
      // 请求已发出，但没有收到响应
      errorMsg = '网络异常，未收到服务器响应'
    } else {
      // 请求配置出错
      errorMsg = error.message || (isEdit.value ? '修改失败' : '创建失败')
    }
    
    ElMessage.error(errorMsg)
    console.error(isEdit.value ? '修改设备维修失败:' : '创建设备维修失败:', error)
  } finally {
    loading.value = false
  }
}

// 监听设备编号变化，自动获取设备名称
watch(
  () => repairForm.equipmentNumber,
  async (newValue) => {
    if (newValue && !isEdit.value) { // 只有在创建模式下才自动填充
      try {
        // 通过设备编号查询设备列表，找到对应的设备
        const equipmentList = await equipmentApi.getEquipmentList({ equipmentNumber: newValue }) as any[]
        if (equipmentList && equipmentList.length > 0) {
          const equipment = equipmentList[0]
          repairForm.equipmentName = equipment.equipmentName || ''
        }
      } catch (error) {
        console.error(`获取设备详情失败，设备编号：${newValue}`, error)
      }
    }
  }
)

// 取消操作
const handleCancel = () => {
  router.push('/repair')
}

// 初始化加载
onMounted(() => {
  loadRepairDetail()
})
</script>

<style scoped>
.repair-edit-container {
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

.repair-form {
  margin-top: 20px;
  background-color: white;
  padding: 20px;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.form-actions {
  display: flex;
  justify-content: center;
  gap: 10px;
  margin-top: 30px;
}
</style>