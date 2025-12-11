<template>
  <div class="fault-code-list-container">
    <el-card shadow="never" class="page-card">
      <template #header>
        <div class="card-header">
          <h2 class="page-title">故障代码管理</h2>
        </div>
      </template>
      
      <BaseTable
        :loading="loading"
        :dataList="faultCodeList"
        :columns="columns"
        :pagination="pagination"
        @search="handleSearch"
        @create="handleCreate"
        @edit="handleEdit"
        @delete="handleDelete"
        @batch-delete="handleBatchDelete"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :permissions="['edit', 'delete']"
      />
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import BaseTable from '../components/BaseTable.vue'
import { faultCodeApi } from '../api/fault-code'
import { usePolling } from '../composables/usePolling'
import type { EquipmentFaultCode } from '../types/equipment'

const router = useRouter()
const loading = ref(false)
const searchQuery = ref('')
const faultCodeList = ref<EquipmentFaultCode[]>([])
const pagination = reactive({
  currentPage: 1,
  pageSize: 10,
  total: 0
})

// 表格列配置
const columns = ref([
  {
    prop: 'code',
    label: '故障代码',
    minWidth: 150
  },
  {
    prop: 'codeDescribe',
    label: '故障描述',
    minWidth: 200
  },
  {
    prop: 'status',
    label: '状态',
    minWidth: 100
  },
  {
    prop: 'codeRemark',
    label: '备注',
    minWidth: 200
  }
])

// 加载故障代码列表
const loadFaultCodeList = async () => {
  loading.value = true
  try {
    const params: any = {
      page: pagination.currentPage,
      pageSize: pagination.pageSize
    }
    if (searchQuery.value) {
      params.code = searchQuery.value
      params.codeDescribe = searchQuery.value
    }
    const result = await faultCodeApi.getFaultCodeList(params) as any
    // 检查返回数据类型
    if (Array.isArray(result)) {
      faultCodeList.value = result
      pagination.total = result.length
    } else {
      faultCodeList.value = []
      pagination.total = 0
    }
  } catch (error) {
    const errorMsg = error instanceof Error ? error.message : '加载故障代码列表失败'
    ElMessage.error(errorMsg)
    console.error('加载故障代码列表失败:', error)
    // 确保错误时列表为空
    faultCodeList.value = []
    pagination.total = 0
  } finally {
    loading.value = false
  }
}

// 使用定时轮询，每30秒更新一次数据
usePolling(loadFaultCodeList, 30000)

// 搜索
const handleSearch = (query: string) => {
  searchQuery.value = query
  pagination.currentPage = 1
  loadFaultCodeList()
}

// 新增
const handleCreate = () => {
  router.push('/fault-code/create')
}

// 编辑
const handleEdit = (id: number) => {
  router.push(`/fault-code/edit/${id}`)
}

// 删除
const handleDelete = async (id: number) => {
  try {
    await faultCodeApi.deleteFaultCodeById(id)
    ElMessage.success('删除成功')
    loadFaultCodeList()
  } catch (error) {
    ElMessage.error('删除失败')
    console.error('删除故障代码失败:', error)
  }
}

// 批量删除
const handleBatchDelete = async (ids: number[]) => {
  try {
    for (const id of ids) {
      await faultCodeApi.deleteFaultCodeById(id)
    }
    ElMessage.success('批量删除成功')
    loadFaultCodeList()
  } catch (error) {
    ElMessage.error('批量删除失败')
    console.error('批量删除故障代码失败:', error)
  }
}

// 分页大小变化
const handleSizeChange = (size: number) => {
  pagination.pageSize = size
  loadFaultCodeList()
}

// 当前页变化
const handleCurrentChange = (page: number) => {
  pagination.currentPage = page
  loadFaultCodeList()
}
</script>

<style scoped>
.fault-code-list-container {
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
</style>
