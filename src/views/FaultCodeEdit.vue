<template>
  <div class="fault-code-edit-container">
    <el-card shadow="never" class="page-card">
      <template #header>
        <div class="card-header">
          <h2 class="page-title">{{ isEdit ? '编辑故障代码' : '创建故障代码' }}</h2>
        </div>
      </template>
      
      <el-form
        ref="faultCodeFormRef"
        :model="faultCodeForm"
        :rules="rules"
        label-position="right"
        label-width="120px"
        class="fault-code-form"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="故障代码" prop="code">
              <el-input
                v-model="faultCodeForm.code"
                placeholder="请输入故障代码"
                clearable
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="故障描述" prop="codeDescribe">
              <el-input
                v-model="faultCodeForm.codeDescribe"
                placeholder="请输入故障描述"
                clearable
              />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-select
                v-model="faultCodeForm.status"
                placeholder="请选择状态"
              >
                <el-option label="启用" :value="1" />
                <el-option label="禁用" :value="0" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注" prop="codeRemark">
              <el-input
                v-model="faultCodeForm.codeRemark"
                placeholder="请输入备注"
                type="textarea"
                :rows="4"
                resize="vertical"
              />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-form-item class="form-actions">
          <el-button type="primary" @click="handleSubmit" :loading="loading">
            {{ isEdit ? '保存修改' : '创建故障代码' }}
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
import { faultCodeApi } from '../api/fault-code'
import type { EquipmentFaultCode } from '../types/equipment'

const router = useRouter()
const route = useRoute()
const loading = ref(false)
const faultCodeFormRef = ref<FormInstance>()
const id = computed(() => route.params.id as string)
const isEdit = computed(() => !!id.value)

// 表单数据
const faultCodeForm = reactive<EquipmentFaultCode>({
  code: '',
  codeDescribe: '',
  status: 1,
  codeRemark: ''
})

// 表单验证规则
const rules = reactive({
  code: [
    { required: true, message: '请输入故障代码', trigger: 'blur' },
    { min: 1, max: 20, message: '故障代码长度在 1 到 20 个字符', trigger: 'blur' }
  ],
  codeDescribe: [
    { required: true, message: '请输入故障描述', trigger: 'blur' },
    { min: 1, max: 100, message: '故障描述长度在 1 到 100 个字符', trigger: 'blur' }
  ],
  status: [
    { required: true, message: '请选择状态', trigger: 'change' }
  ]
})

// 加载故障代码详情
const loadFaultCodeDetail = async () => {
  if (!isEdit.value) return
  
  loading.value = true
  try {
    const result = await faultCodeApi.getFaultCodeById(Number(id.value))
    Object.assign(faultCodeForm, result)
  } catch (error) {
    ElMessage.error('加载故障代码详情失败')
    console.error('加载故障代码详情失败:', error)
    router.push('/fault-code')
  } finally {
    loading.value = false
  }
}

// 提交表单
const handleSubmit = async () => {
  if (!faultCodeFormRef.value) return
  
  await faultCodeFormRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        if (isEdit.value) {
          await faultCodeApi.updateFaultCode(Number(id.value), faultCodeForm)
          ElMessage.success('修改成功')
        } else {
          await faultCodeApi.createFaultCode(faultCodeForm)
          ElMessage.success('创建成功')
        }
        router.push('/fault-code')
      } catch (error) {
        ElMessage.error(isEdit.value ? '修改失败' : '创建失败')
        console.error(isEdit.value ? '修改故障代码失败:' : '创建故障代码失败:', error)
      } finally {
        loading.value = false
      }
    }
  })
}

// 取消操作
const handleCancel = () => {
  router.push('/fault-code')
}

// 初始化加载
onMounted(() => {
  loadFaultCodeDetail()
})
</script>

<style scoped>
.fault-code-edit-container {
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

.fault-code-form {
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
