<template>
  <div class="equipment-table-container">
    <!-- 表格头部 -->
    <div class="table-header">
      <div class="header-left">
        <el-input
          v-model="searchQuery"
          placeholder="请输入设备名称或编号"
          clearable
          :prefix-icon="Search"
          style="width: 300px"
          @keyup.enter="handleSearch"
        />
        <el-button type="primary" @click="handleSearch">
          <el-icon><Search /></el-icon>搜索
        </el-button>
        <el-button type="primary" @click="handleCreate">
          <el-icon><Plus /></el-icon>新增
        </el-button>
      </div>
      <div class="header-right">
        <el-dropdown v-if="selectedIds.length > 0">
          <el-button type="warning">
            <el-icon><More /></el-icon>批量操作 <el-icon class="el-icon--right"><ArrowDown /></el-icon>
          </el-button>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="handleBatchDelete">批量删除</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>
    
    <!-- 表格主体 -->
    <el-table
      v-loading="loading"
      :data="equipmentList"
      stripe
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="equipmentName" label="设备名称" min-width="150" />
      <el-table-column prop="equipmentNumber" label="设备编号" min-width="150" />
      <el-table-column prop="storageLocation" label="存放地点" min-width="120" />
      <el-table-column prop="productionLine" label="产线" min-width="120" />
      <el-table-column prop="deviceType" label="设备类型" min-width="120" />
      <el-table-column prop="equipmentStatus" label="设备状态" min-width="100">
        <template #default="scope">
          <el-tag
            :type="
              scope.row.equipmentStatus === '运行中' ? 'success' :
              scope.row.equipmentStatus === '待机' ? 'info' : 'danger'
            "
          >
            {{ scope.row.equipmentStatus }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态" min-width="100">
        <template #default="scope">
          <el-tag
            :type="
              scope.row.status === '正常' ? 'success' :
              scope.row.status === '维修中' ? 'warning' : 'danger'
            "
          >
            {{ scope.row.status }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200" fixed="right">
        <template #default="scope">
          <el-button
            type="primary"
            size="small"
            @click="handleView(scope.row.id)"
          >
            <el-icon><View /></el-icon>查看
          </el-button>
          <el-button
            type="warning"
            size="small"
            @click="handleEdit(scope.row.id)"
          >
            <el-icon><Edit /></el-icon>编辑
          </el-button>
          <el-button
            type="danger"
            size="small"
            @click="handleDelete(scope.row.id)"
          >
            <el-icon><Delete /></el-icon>删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <!-- 分页 -->
    <div class="table-pagination">
      <el-pagination
        v-model:current-page="pagination.currentPage"
        v-model:page-size="pagination.pageSize"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="pagination.total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { Search, ArrowDown, Plus, More, View, Edit, Delete } from '@element-plus/icons-vue'
import type { Equipment } from '../types/equipment'

defineProps<{
  loading?: boolean
  equipmentList: Equipment[]
  pagination: {
    currentPage: number
    pageSize: number
    total: number
  }
}>()

const emit = defineEmits<{
  'search': [query: string]
  'create': []
  'view': [id: number]
  'edit': [id: number]
  'delete': [id: number]
  'batch-delete': [ids: number[]]
  'size-change': [size: number]
  'current-change': [page: number]
}>()

const searchQuery = ref('')
const selectedIds = ref<number[]>([])

// 搜索
const handleSearch = () => {
  emit('search', searchQuery.value)
}

// 新增
const handleCreate = () => {
  emit('create')
}

// 查看详情
const handleView = (id: number) => {
  emit('view', id)
}

// 编辑
const handleEdit = (id: number) => {
  emit('edit', id)
}

// 删除
const handleDelete = (id: number) => {
  emit('delete', id)
}

// 批量删除
const handleBatchDelete = () => {
  emit('batch-delete', selectedIds.value)
}

// 选择变化
const handleSelectionChange = (selection: Equipment[]) => {
  selectedIds.value = selection.map(item => item.id!)
}

// 分页大小变化
const handleSizeChange = (size: number) => {
  emit('size-change', size)
}

// 当前页变化
const handleCurrentChange = (page: number) => {
  emit('current-change', page)
}
</script>

<style scoped>
.equipment-table-container {
  background-color: white;
  border-radius: 4px;
  padding: 20px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.table-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.header-left {
  display: flex;
  gap: 10px;
  align-items: center;
}

.header-right {
  display: flex;
  gap: 10px;
}

.table-pagination {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>