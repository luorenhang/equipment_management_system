<template>
  <div class="inspection-edit-container">
    <el-card shadow="never" class="page-card">
      <template #header>
        <div class="card-header">
          <h2 class="page-title">{{ isEdit ? '编辑设备点检' : '创建设备点检' }}</h2>
        </div>
      </template>
      
      <el-form
        ref="inspectionFormRef"
        :model="inspectionForm"
        :rules="rules"
        label-position="right"
        label-width="120px"
        class="inspection-form"
      >
        <!-- 基本信息 -->
        <el-divider content-position="left">基本信息</el-divider>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="点检编号" prop="checkNumber">
              <el-input
                v-model="inspectionForm.checkNumber"
                placeholder="请输入点检编号"
                clearable
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="设备名称" prop="equipmentName">
              <el-input
                v-model="inspectionForm.equipmentName"
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
                v-model="inspectionForm.equipmentNumber"
                placeholder="请输入设备编号"
                clearable
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="设备类型" prop="deviceType">
              <el-select
                v-model="inspectionForm.deviceType"
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
            <el-form-item label="计划开始时间" prop="startTime">
              <el-date-picker
                v-model="inspectionForm.startTime"
                type="datetime"
                placeholder="请选择计划开始时间"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="计划结束时间" prop="endTime">
              <el-date-picker
                v-model="inspectionForm.endTime"
                type="datetime"
                placeholder="请选择计划结束时间"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="点检人" prop="checkUser">
              <el-input
                v-model="inspectionForm.checkUser"
                placeholder="请输入点检人"
                clearable
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-select
                v-model="inspectionForm.status"
                placeholder="请选择状态"
              >
                <el-option label="待执行" value="待执行" />
                <el-option label="执行中" value="执行中" />
                <el-option label="已完成" value="已完成" />
                <el-option label="已取消" value="已取消" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        
        <!-- 点检项目 -->
        <el-divider content-position="left">点检项目</el-divider>
        
        <el-form-item label="点检项目">
          <div class="check-project-list">
            <div
              v-for="(project, index) in inspectionForm.checkProjectList"
              :key="index"
              class="check-project-item"
            >
              <el-row :gutter="20" class="project-row">
                <el-col :span="8">
                  <el-input
                    v-model="project.checkProject"
                    placeholder="请输入点检项目"
                    clearable
                  />
                </el-col>
                <el-col :span="8">
                  <el-input
                    v-model="project.checkContent"
                    placeholder="请输入点检内容"
                    clearable
                  />
                </el-col>
                <el-col :span="6">
                  <el-select
                    v-model="project.checkResult"
                    placeholder="请选择点检结果"
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
                    @click="removeCheckProject(index)"
                  />
                </el-col>
              </el-row>
              <el-row :gutter="20" class="project-row mt-10">
                <el-col :span="16">
                  <el-input
                    v-model="project.checkStandard"
                    placeholder="请输入点检标准"
                    clearable
                  />
                </el-col>
                <el-col :span="7">
                  <el-input
                    v-model="project.remark"
                    placeholder="请输入备注"
                    clearable
                  />
                </el-col>
              </el-row>
            </div>
          </div>
          
          <el-button
            type="primary"
            size="small"
            icon="Plus"
            @click="addCheckProject"
            class="add-project-btn"
          >
            添加点检项目
          </el-button>
        </el-form-item>
        
        <el-form-item class="form-actions">
          <el-button type="primary" @click="handleSubmit" :loading="loading">
            {{ isEdit ? '保存修改' : '创建设备点检' }}
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
import { inspectionApi } from '../api/inspection'
import type { EquipmentInspection, CheckProject } from '../types/equipment'

const router = useRouter()
const route = useRoute()
const loading = ref(false)
const inspectionFormRef = ref<FormInstance>()
const id = computed(() => route.params.id as string)
const isEdit = computed(() => !!id.value)

// 表单数据
const inspectionForm = reactive<EquipmentInspection>({
  checkNumber: '',
  equipmentName: '',
  equipmentNumber: '',
  deviceType: '',
  startTime: '',
  endTime: '',
  operateStartTime: '',
  operateEndTime: '',
  checkResult: '',
  checkUser: '',
  enableStatus: '',
  barCode: '',
  status: '待执行',
  checkProjectList: []
})

// 表单验证规则
const rules = reactive({
  checkNumber: [
    { required: true, message: '请输入点检编号', trigger: 'blur' }
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
  startTime: [
    { required: true, message: '请选择计划开始时间', trigger: 'change' }
  ],
  endTime: [
    { required: true, message: '请选择计划结束时间', trigger: 'change' }
  ],
  checkUser: [
    { required: true, message: '请输入点检人', trigger: 'blur' }
  ],
  status: [
    { required: true, message: '请选择状态', trigger: 'change' }
  ]
})

// 添加点检项目
const addCheckProject = () => {
  const newProject: CheckProject = {
    checkId: Number(id.value) || 0,
    checkProject: '',
    checkContent: '',
    checkResult: '合格',
    checkStandard: '',
    remark: '',
    reason: '',
    status: 1
  }
  inspectionForm.checkProjectList.push(newProject)
}

// 移除点检项目
const removeCheckProject = (index: number) => {
  if (inspectionForm.checkProjectList.length > 1) {
    inspectionForm.checkProjectList.splice(index, 1)
  } else {
    ElMessage.warning('至少需要保留一个点检项目')
  }
}

// 加载设备点检详情
const loadInspectionDetail = async () => {
  if (!isEdit.value) {
    // 初始化时添加一个点检项目
    addCheckProject()
    return
  }
  
  loading.value = true
  try {
    const result = await inspectionApi.getInspectionById(Number(id.value))
    Object.assign(inspectionForm, result)
    // 如果没有点检项目，添加一个
    if (inspectionForm.checkProjectList.length === 0) {
      addCheckProject()
    }
  } catch (error) {
    ElMessage.error('加载设备点检详情失败')
    console.error('加载设备点检详情失败:', error)
    router.push('/inspection')
  } finally {
    loading.value = false
  }
}

// 提交表单
const handleSubmit = async () => {
  if (!inspectionFormRef.value) return
  
  // 表单验证
  const valid = await inspectionFormRef.value.validate()
  if (!valid) return
  
  // 验证点检项目
  const hasValidProject = inspectionForm.checkProjectList.some(project => 
    project.checkProject && project.checkContent && project.checkStandard
  )
  if (!hasValidProject) {
    ElMessage.error('请至少填写一个完整的点检项目')
    return
  }
  
  loading.value = true
  try {
    if (isEdit.value) {
      await inspectionApi.updateInspection(Number(id.value), inspectionForm)
      ElMessage.success('修改成功')
    } else {
      await inspectionApi.createInspection(inspectionForm)
      ElMessage.success('创建成功')
    }
    router.push('/inspection')
  } catch (error) {
    ElMessage.error(isEdit.value ? '修改失败' : '创建失败')
    console.error(isEdit.value ? '修改设备点检失败:' : '创建设备点检失败:', error)
  } finally {
    loading.value = false
  }
}

// 取消操作
const handleCancel = () => {
  router.push('/inspection')
}

// 初始化加载
onMounted(() => {
  loadInspectionDetail()
})
</script>

<style scoped>
.inspection-edit-container {
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

.inspection-form {
  margin-top: 20px;
  background-color: white;
  padding: 20px;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.check-project-list {
  margin-bottom: 15px;
}

.check-project-item {
  background-color: #f5f7fa;
  padding: 15px;
  border-radius: 4px;
  margin-bottom: 15px;
  border: 1px solid #e4e7ed;
}

.project-row {
  align-items: flex-start;
}

.mt-10 {
  margin-top: 10px;
}

.add-project-btn {
  margin-bottom: 20px;
}

.form-actions {
  display: flex;
  justify-content: center;
  gap: 10px;
  margin-top: 30px;
}
</style>
