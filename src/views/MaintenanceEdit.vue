<template>
  <div class="maintenance-edit-container">
    <el-card shadow="never" class="page-card">
      <template #header>
        <div class="card-header">
          <h2 class="page-title">{{ isEdit ? '编辑设备保养' : '创建设备保养' }}</h2>
        </div>
      </template>
      
      <el-form
        ref="maintenanceFormRef"
        :model="maintenanceForm"
        :rules="rules"
        label-position="right"
        label-width="120px"
        class="maintenance-form"
      >
        <!-- 基本信息 -->
        <el-divider content-position="left">基本信息</el-divider>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="保养编号" prop="maintenanceNumber">
              <el-input
                v-model="maintenanceForm.maintenanceNumber"
                placeholder="请输入保养编号"
                clearable
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="设备名称" prop="equipmentName">
              <el-input
                v-model="maintenanceForm.equipmentName"
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
                v-model="maintenanceForm.equipmentNumber"
                placeholder="请输入设备编号"
                clearable
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="设备类型" prop="deviceType">
              <el-select
                v-model="maintenanceForm.deviceType"
                placeholder="请选择设备类型"
              >
                <el-option label="类型1" value="类型1" />
                <el-option label="类型2" value="类型2" />
                <el-option label="类型3" value="类型3" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="保养人" prop="maintenancePerson">
              <el-input
                v-model="maintenanceForm.maintenancePerson"
                placeholder="请输入保养人"
                clearable
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="保养时间" prop="maintenanceTime">
              <el-date-picker
                v-model="maintenanceForm.maintenanceTime"
                type="datetime"
                placeholder="请选择保养时间"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-select
              v-model="maintenanceForm.status"
              placeholder="请选择状态"
            >
              <el-option label="待执行" value="0" />
              <el-option label="执行中" value="1" />
              <el-option label="已完成" value="01" />
              <el-option label="已取消" value="02" />
            </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        
        <!-- 保养项目 -->
        <el-divider content-position="left">保养项目</el-divider>
        
        <el-form-item label="保养项目">
          <div class="maintenance-item-list">
            <div
              v-for="(item, index) in maintenanceForm.maintenanceItemList"
              :key="index"
              class="maintenance-item"
            >
              <el-row :gutter="20" class="item-row">
                <el-col :span="8">
                  <el-input
                    v-model="item.maintenanceItem"
                    placeholder="请输入保养项目"
                    clearable
                  />
                </el-col>
                <el-col :span="8">
                  <el-input
                    v-model="item.maintenanceContent"
                    placeholder="请输入保养内容"
                    clearable
                  />
                </el-col>
                <el-col :span="6">
                  <el-select
                    v-model="item.maintenanceResult"
                    placeholder="请选择保养结果"
                  >
                    <el-option label="合格" value="合格" />
                    <el-option label="不合格" value="不合格" />
                  </el-select>
                </el-col>
                <el-col :span="1">
                  <el-button
                    type="danger"
                    size="small"
                    icon="Delete"
                    @click="removeMaintenanceItem(index)"
                  />
                </el-col>
              </el-row>
              <el-row :gutter="20" class="item-row mt-10">
                <el-col :span="23">
                  <el-input
                    v-model="item.remark"
                    placeholder="请输入备注"
                    clearable
                    type="textarea"
                    rows="2"
                  />
                </el-col>
              </el-row>
            </div>
          </div>
          
          <el-button
            type="primary"
            size="small"
            icon="Plus"
            @click="addMaintenanceItem"
            class="add-item-btn"
          >
            添加保养项目
          </el-button>
        </el-form-item>
        
        <el-form-item class="form-actions">
          <el-button type="primary" @click="handleSubmit" :loading="loading">
            {{ isEdit ? '保存修改' : '创建设备保养' }}
          </el-button>
          <el-button @click="handleCancel">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage, type FormInstance } from 'element-plus'
import { maintenanceApi } from '../api/maintenance'
import type { EquipmentMaintenance, MaintenanceItem } from '../types/equipment'

const router = useRouter()
const route = useRoute()
const loading = ref(false)
const maintenanceFormRef = ref<FormInstance>()
const id = computed(() => route.params.id as string)
const isEdit = computed(() => !!id.value)

// 表单数据
const maintenanceForm = reactive<EquipmentMaintenance>({
  maintenanceNumber: '',
  equipmentName: '',
  equipmentNumber: '',
  deviceType: '',
  maintenancePerson: '',
  maintenanceTime: undefined,
  status: '0',
  maintenanceItemList: []
})

// 表单验证规则
const rules = reactive({
  maintenanceNumber: [
    { required: true, message: '请输入保养编号', trigger: 'blur' }
  ],
  equipmentName: [
    { required: true, message: '请输入设备名称', trigger: 'blur' }
  ],
  equipmentNumber: [
    { required: true, message: '请输入设备编号', trigger: 'blur' }
  ],
  deviceType: [
    { required: true, message: '请选择设备类型', trigger: 'change' }
  ],
  maintenancePerson: [
    { required: true, message: '请输入保养人', trigger: 'blur' }
  ],
  maintenanceTime: [
    { required: true, message: '请选择保养时间', trigger: 'change' }
  ],
  status: [
    { required: true, message: '请选择状态', trigger: 'change' }
  ]
})

// 添加保养项目
const addMaintenanceItem = () => {
  const newItem: MaintenanceItem = {
    maintenanceItem: '',
    maintenanceContent: '',
    maintenanceResult: '合格',
    remark: '',
    status: 1
  }
  maintenanceForm.maintenanceItemList.push(newItem)
}

// 移除保养项目
const removeMaintenanceItem = (index: number) => {
  if (maintenanceForm.maintenanceItemList.length > 1) {
    maintenanceForm.maintenanceItemList.splice(index, 1)
  } else {
    ElMessage.warning('至少需要保留一个保养项目')
  }
}

// 加载设备保养详情
const loadMaintenanceDetail = async () => {
  if (!isEdit.value) {
    // 初始化时添加一个保养项目
    addMaintenanceItem()
    return
  }
  
  loading.value = true
  try {
    const result = await maintenanceApi.getMaintenanceById(Number(id.value))
    Object.assign(maintenanceForm, result)
    // 如果没有保养项目，添加一个
    if (maintenanceForm.maintenanceItemList.length === 0) {
      addMaintenanceItem()
    }
  } catch (error) {
    ElMessage.error('加载设备保养详情失败')
    console.error('加载设备保养详情失败:', error)
    router.push('/maintenance')
  } finally {
    loading.value = false
  }
}

// 提交表单
const handleSubmit = async () => {
  if (!maintenanceFormRef.value) return
  
  // 表单验证
  const valid = await maintenanceFormRef.value.validate()
  if (!valid) return
  
  // 验证保养项目
  const allValidItems = maintenanceForm.maintenanceItemList.every(item => 
    item.maintenanceItem && item.maintenanceContent && item.maintenanceResult
  )
  
  if (maintenanceForm.maintenanceItemList.length === 0) {
    ElMessage.error('请至少添加一个保养项目')
    return
  }
  
  if (!allValidItems) {
    ElMessage.error('请确保所有保养项目都填写完整（项目名称、保养内容和保养结果）')
    return
  }
  
  console.log('开始提交表单...')
  console.log('表单数据:', maintenanceForm)
  
  loading.value = true
  try {
    let result
    if (isEdit.value) {
      console.log('执行修改操作...')
      result = await maintenanceApi.updateMaintenance(Number(id.value), maintenanceForm)
      console.log('修改操作返回结果:', result)
      ElMessage.success('修改成功')
    } else {
      console.log('执行创建操作...')
      result = await maintenanceApi.createMaintenance(maintenanceForm)
      console.log('创建操作返回结果:', result)
      ElMessage.success('创建成功')
    }
    router.push('/maintenance')
  } catch (error: any) {
    console.error('操作失败:', error)
    console.error('错误名称:', error.name)
    console.error('错误信息:', error.message)
    console.error('错误堆栈:', error.stack)
    console.error('响应数据:', error.response?.data)
    console.error('响应状态:', error.response?.status)
    console.error('响应头:', error.response?.headers)
    
    // 提取具体错误信息
    let errorMsg = isEdit.value ? '修改失败' : '创建失败'
    if (error.response?.data) {
      if (typeof error.response.data === 'string') {
        errorMsg = error.response.data
      } else if (error.response.data.message) {
        errorMsg = error.response.data.message
      } else if (error.response.data.msg) {
        errorMsg = error.response.data.msg
      } else if (error.response.data.data && typeof error.response.data.data === 'string') {
        errorMsg = error.response.data.data
      }
    }
    
    ElMessage.error(errorMsg)
  } finally {
    loading.value = false
  }
}

// 取消操作
const handleCancel = () => {
  router.push('/maintenance')
}

// 初始化加载
onMounted(() => {
  loadMaintenanceDetail()
})
</script>

<style scoped>
.maintenance-edit-container {
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

.maintenance-form {
  margin-top: 20px;
  background-color: white;
  padding: 20px;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.maintenance-item-list {
  margin-bottom: 15px;
}

.maintenance-item {
  background-color: #f5f7fa;
  padding: 15px;
  border-radius: 4px;
  margin-bottom: 15px;
  border: 1px solid #e4e7ed;
}

.item-row {
  align-items: flex-start;
}

.mt-10 {
  margin-top: 10px;
}

.add-item-btn {
  margin-bottom: 20px;
}

.form-actions {
  display: flex;
  justify-content: center;
  gap: 10px;
  margin-top: 30px;
}
</style>