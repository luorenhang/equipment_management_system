<template>
  <el-form ref="formRef" :model="formData" :rules="rules" label-width="120px">
    <el-row :gutter="20">
      <el-col :span="12">
        <el-form-item label="设备名称" prop="equipmentName">
          <el-input v-model="formData.equipmentName" placeholder="请输入设备名称" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="设备编号" prop="equipmentNumber">
          <el-input v-model="formData.equipmentNumber" placeholder="请输入设备编号" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="存放地点" prop="storageLocation">
          <el-input v-model="formData.storageLocation" placeholder="请输入存放地点" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="产线" prop="productionLine">
          <el-input v-model="formData.productionLine" placeholder="请输入产线" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="设备类型" prop="deviceType">
          <el-input v-model="formData.deviceType" placeholder="请输入设备类型" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="状态" prop="status">
          <el-select v-model="formData.status" placeholder="请选择状态">
            <el-option label="正常" value="正常" />
            <el-option label="维修中" value="维修中" />
            <el-option label="停用" value="停用" />
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="设备状态" prop="equipmentStatus">
          <el-select v-model="formData.equipmentStatus" placeholder="请选择设备状态">
            <el-option label="运行中" value="运行中" />
            <el-option label="待机" value="待机" />
            <el-option label="故障" value="故障" />
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>
    
    <!-- 责任人信息 -->
    <el-divider content-position="left">责任人信息</el-divider>
    <div v-for="(responsible, index) in formData.responsibleList" :key="index" class="responsible-item">
      <el-row :gutter="20">
        <el-col :span="7">
          <el-form-item :label="`姓名 ${index + 1}`" :prop="`responsibleList.${index}.name`" :rules="responsibleRules.name">
            <el-input v-model="responsible.name" placeholder="请输入姓名" />
          </el-form-item>
        </el-col>
        <el-col :span="7">
          <el-form-item :label="`职位 ${index + 1}`" :prop="`responsibleList.${index}.position`" :rules="responsibleRules.position">
            <el-input v-model="responsible.position" placeholder="请输入职位" />
          </el-form-item>
        </el-col>
        <el-col :span="7">
          <el-form-item :label="`联系方式 ${index + 1}`" :prop="`responsibleList.${index}.contact`" :rules="responsibleRules.contact">
            <el-input v-model="responsible.contact" placeholder="请输入联系方式" />
          </el-form-item>
        </el-col>
        <el-col :span="3" class="responsible-actions">
          <el-button 
            type="danger" 
            icon="Delete" 
            circle 
            @click="removeResponsible(index)"
            v-if="formData.responsibleList && formData.responsibleList.length > 1"

          />
        </el-col>
      </el-row>
    </div>
    <el-form-item>
        <el-button type="primary" @click="addResponsible">
          <el-icon><Plus /></el-icon>添加责任人
        </el-button>
      </el-form-item>
    
      <el-form-item>
        <el-button type="primary" @click="handleSubmit">保存</el-button>
        <el-button @click="handleReset">重置</el-button>
      </el-form-item>
  </el-form>
</template>

<script setup lang="ts">
import { ref, reactive, watch } from 'vue'
import { Plus } from '@element-plus/icons-vue'
import type { FormInstance, FormRules } from 'element-plus'
import type { Equipment } from '../types/equipment'

const props = defineProps<{
  modelValue?: Equipment
}>()

const emit = defineEmits<{
  'update:modelValue': [value: Equipment]
  'submit': [value: Equipment]
}>()

const formRef = ref<FormInstance>()

// 初始化formData
const initFormData = () => {
  const initialData: Equipment = {
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
  }
  
  // 如果有初始数据，合并初始数据
  if (props.modelValue) {
    Object.assign(initialData, props.modelValue)
    // 确保责任人列表至少有一个
    if (!initialData.responsibleList || initialData.responsibleList.length === 0) {
      initialData.responsibleList = [
        {
          name: '',
          position: '',
          contact: '',
          responsibleType: '',
          notes: '',
          equipmentLedger: 0
        }
      ]
    }
  }
  
  return initialData
}

const formData = reactive<Equipment>(initFormData())

const rules = reactive<FormRules>({
  equipmentName: [
    { required: true, message: '请输入设备名称', trigger: 'blur' },
    { min: 0, max: 100, message: '设备名称长度不能超过 100 个字符', trigger: 'blur' },
    { pattern: /^[\u4e00-\u9fa5a-zA-Z0-9\s\-_]*$/, message: '设备名称只能包含中文、英文、数字、空格、- 和 _', trigger: 'blur' }
  ],
  equipmentNumber: [
    { required: true, message: '请输入设备编号', trigger: 'blur' },
    { min: 0, max: 100, message: '设备编号长度不能超过 100 个字符', trigger: 'blur' }
  ],
  storageLocation: [
    { required: true, message: '请输入存放地点', trigger: 'blur' },
    { min: 0, max: 100, message: '存放地点长度不能超过 100 个字符', trigger: 'blur' }
  ],
  deviceType: [
    { required: true, message: '请输入设备类型', trigger: 'blur' },
    { min: 0, max: 100, message: '设备类型长度不能超过 100 个字符', trigger: 'blur' }
  ],
  productionLine: [
    { required: true, message: '请输入产线', trigger: 'blur' },
    { min: 0, max: 100, message: '产线长度不能超过 100 个字符', trigger: 'blur' }
  ],
  status: [
    { required: true, message: '请选择设备状态', trigger: 'change' }
  ],
  equipmentStatus: [
    { required: true, message: '请选择设备运行状态', trigger: 'change' }
  ]
})

const responsibleRules = reactive<FormRules>({
  name: [
    { required: true, message: '请输入姓名', trigger: 'blur' },
    { min: 1, max: 50, message: '姓名长度应在 1 到 50 个字符之间', trigger: 'blur' }
  ],
  position: [
    { required: true, message: '请输入职位', trigger: 'blur' },
    { min: 1, max: 50, message: '职位长度应在 1 到 50 个字符之间', trigger: 'blur' }
  ],
  contact: [
    { required: true, message: '请输入联系方式', trigger: 'blur' },
    { min: 1, max: 50, message: '联系方式长度应在 1 到 50 个字符之间', trigger: 'blur' }
  ]
})

// 初始化formData
if (props.modelValue) {
  Object.assign(formData, props.modelValue)
}

// 监听formData变化，同步到父组件
watch(
  formData,
  (newValue) => {
    emit('update:modelValue', newValue)
  },
  { deep: true }
)

// 添加责任人
const addResponsible = () => {
  if (!formData.responsibleList) {
    formData.responsibleList = []
  }
  formData.responsibleList.push({
    name: '',
    position: '',
    contact: '',
    responsibleType: '',
    notes: '',
    equipmentLedger: 0
  })
}
// 删除责任人
const removeResponsible = (index: number) => {
  if (formData.responsibleList) {
    formData.responsibleList.splice(index, 1)
  }
}

// 表单提交
const handleSubmit = async () => {
  if (!formRef.value) return false
  
  try {
    const valid = await new Promise<boolean>((resolve) => {
      formRef.value!.validate((valid) => {
        resolve(valid)
      })
    })
    
    if (valid) {
      emit('update:modelValue', formData)
      emit('submit', formData)
      return true
    } else {
      return false
    }
  } catch (error) {
    console.error('表单验证失败:', error)
    return false
  }
}


// 表单重置
const handleReset = () => {
  if (!formRef.value) return
  formRef.value.resetFields()
}

// 暴露方法给父组件
defineExpose({
  handleSubmit
})
</script>

<style scoped>
.responsible-item {
  border: 1px solid #ebeef5;
  border-radius: 4px;
  padding: 15px;
  margin-bottom: 15px;
  background-color: #fafafa;
}

.responsible-actions {
  display: flex;
  align-items: flex-end;
  padding-bottom: 22px;
}
</style>