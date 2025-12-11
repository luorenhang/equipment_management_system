<template>
  <div class="equipment-list-page">
    <h1>设备管理</h1>
    <equipment-table
      :loading="loading"
      :equipment-list="equipmentList"
      :pagination="pagination"
      @search="handleSearch"
      @create="handleCreate"
      @view="handleView"
      @edit="handleEdit"
      @delete="handleDelete"
      @batch-delete="handleBatchDelete"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useRouter } from 'vue-router'
import EquipmentTable from '../components/EquipmentTable.vue'
import { equipmentApi } from '../api/equipment'
import { usePolling } from '../composables/usePolling'
import type { Equipment } from '../types/equipment'

const router = useRouter()

const loading = ref(false)
const equipmentList = ref<Equipment[]>([])
const pagination = reactive({
  currentPage: 1,
  pageSize: 10,
  total: 0
})
const searchQuery = ref('')

// 获取设备列表
const fetchEquipmentList = async () => {
  loading.value = true
  try {
    // 移除分页参数，后端不支持分页
    const params: any = {}
    
    if (searchQuery.value) {
      // 模拟搜索条件，实际应根据后端API调整
      Object.assign(params, {
        equipmentName: searchQuery.value,
        equipmentNumber: searchQuery.value
      })
    }
    
    console.log('请求设备列表，参数：', params)
    const response = await equipmentApi.getEquipmentList(params)
    console.log('设备列表响应：', response)
    
    // 处理API响应，确保数据类型安全
    let equipmentData: Equipment[] = []
    
    // 检查响应类型，如果是数组直接使用
    if (Array.isArray(response)) {
      equipmentData = response
    }
    
    equipmentList.value = equipmentData
    // 设置总数为返回列表的长度
    pagination.total = equipmentData.length
  } catch (error: any) {
    console.error('获取设备列表失败：', error)
    ElMessage.error('获取设备列表失败：' + (error.message || '未知错误'))
  } finally {
    loading.value = false
  }
}

// 页面加载时立即获取设备列表
fetchEquipmentList()

// 使用定时轮询，每30秒更新一次数据
usePolling(fetchEquipmentList, 30000)

// 搜索
const handleSearch = (query: string) => {
  searchQuery.value = query
  pagination.currentPage = 1
  fetchEquipmentList()
}

// 新增设备
const handleCreate = () => {
  router.push('/equipment/create')
}

// 查看设备详情
const handleView = (id: number) => {
  router.push(`/equipment/${id}`)
}

// 编辑设备
const handleEdit = (id: number) => {
  router.push(`/equipment/edit/${id}`)
}

// 删除设备
const handleDelete = async (id: number) => {
  try {
    await ElMessageBox.confirm('确定要删除该设备吗？', '警告', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    await equipmentApi.deleteEquipmentById(id)
    ElMessage.success('删除成功')
    fetchEquipmentList()
  } catch (error: any) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败：' + (error.message || '未知错误'))
    }
  }
}

// 批量删除
const handleBatchDelete = async (ids: number[]) => {
  if (ids.length === 0) {
    ElMessage.warning('请选择要删除的设备')
    return
  }
  
  try {
    await ElMessageBox.confirm(`确定要删除选中的${ids.length}条设备吗？`, '警告', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    // 模拟批量删除，实际应调用后端API
    for (const id of ids) {
      await equipmentApi.deleteEquipmentById(id)
    }
    
    ElMessage.success('批量删除成功')
    fetchEquipmentList()
  } catch (error: any) {
    if (error !== 'cancel') {
      ElMessage.error('批量删除失败：' + (error.message || '未知错误'))
    }
  }
}

// 分页大小变化
const handleSizeChange = (size: number) => {
  pagination.pageSize = size
  fetchEquipmentList()
}

// 当前页变化
const handleCurrentChange = (page: number) => {
  pagination.currentPage = page
  fetchEquipmentList()
}
</script>

<style scoped>
.equipment-list-page {
  background-color: white;
  border-radius: 4px;
  padding: 20px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

h1 {
  margin-bottom: 20px;
  font-size: 24px;
  color: #333;
}
</style>