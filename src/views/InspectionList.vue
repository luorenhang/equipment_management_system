<template>
  <div class="inspection-list-container">
    <el-card shadow="never" class="page-card">
      <template #header>
        <div class="card-header">
          <h2 class="page-title">设备点检管理</h2>
        </div>
      </template>
      
      <BaseTable
        :loading="loading"
        :dataList="inspectionList"
        :columns="columns"
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
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import BaseTable from '../components/BaseTable.vue'
import { inspectionApi } from '../api/inspection'
import { usePolling } from '../composables/usePolling'
import type { EquipmentInspection } from '../types/equipment'

const router = useRouter()
const loading = ref(false)
const searchQuery = ref('')
const inspectionList = ref<EquipmentInspection[]>([])
const pagination = reactive({
  currentPage: 1,
  pageSize: 10,
  total: 0
})

// 表格列配置
const columns = ref([
  {
    prop: 'checkNumber',
    label: '点检编号',
    minWidth: 150
  },
  {
    prop: 'equipmentName',
    label: '设备名称',
    minWidth: 150
  },
  {
    prop: 'equipmentNumber',
    label: '设备编号',
    minWidth: 150
  },
  {
    prop: 'deviceType',
    label: '设备类型',
    minWidth: 120
  },
  {
    prop: 'startTime',
    label: '计划开始时间',
    minWidth: 180
  },
  {
    prop: 'endTime',
    label: '计划结束时间',
    minWidth: 180
  },
  {
    prop: 'checkUser',
    label: '点检人',
    minWidth: 120
  },
  {
    prop: 'checkResult',
    label: '点检结果',
    minWidth: 120
  },
  {
    prop: 'status',
    label: '状态',
    minWidth: 100
  }
])

// 加载设备点检列表
const loadInspectionList = async () => {
  loading.value = true
  try {
    const params: any = {
      page: pagination.currentPage,
      pageSize: pagination.pageSize
    }
    if (searchQuery.value) {
      params.checkNumber = searchQuery.value
      params.equipmentName = searchQuery.value
      params.equipmentNumber = searchQuery.value
    }
    const result = await inspectionApi.getInspectionList(params) as any
    // 检查返回数据类型
    if (Array.isArray(result)) {
      inspectionList.value = result
      pagination.total = result.length
    } else {
      inspectionList.value = []
      pagination.total = 0
    }
  } catch (error) {
    const errorMsg = error instanceof Error ? error.message : '加载设备点检列表失败'
    ElMessage.error(errorMsg)
    console.error('加载设备点检列表失败:', error)
    // 确保错误时列表为空
    inspectionList.value = []
    pagination.total = 0
  } finally {
    loading.value = false
  }
}

// 使用定时轮询，每30秒更新一次数据
usePolling(loadInspectionList, 30000)

// 搜索
const handleSearch = (query: string) => {
  searchQuery.value = query
  pagination.currentPage = 1
  loadInspectionList()
}

// 新增
const handleCreate = () => {
  router.push('/inspection/create')
}

// 查看详情
const handleView = (id: number) => {
  router.push(`/inspection/${id}`)
}

// 编辑
const handleEdit = (id: number) => {
  router.push(`/inspection/edit/${id}`)
}

// 删除
const handleDelete = async (id: number) => {
  try {
    await inspectionApi.deleteInspectionById(id)
    ElMessage.success('删除成功')
    loadInspectionList()
  } catch (error) {
    ElMessage.error('删除失败')
    console.error('删除设备点检失败:', error)
  }
}

// 批量删除
const handleBatchDelete = async (ids: number[]) => {
  try {
    for (const id of ids) {
      await inspectionApi.deleteInspectionById(id)
    }
    ElMessage.success('批量删除成功')
    loadInspectionList()
  } catch (error) {
    ElMessage.error('批量删除失败')
    console.error('批量删除设备点检失败:', error)
  }
}

// 分页大小变化
const handleSizeChange = (size: number) => {
  pagination.pageSize = size
  loadInspectionList()
}

// 当前页变化
const handleCurrentChange = (page: number) => {
  pagination.currentPage = page
  loadInspectionList()
}
</script>

<style scoped>
.inspection-list-container {
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
