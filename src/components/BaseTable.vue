<template>
  <div class="base-table-container">
    <!-- 表格头部 -->
    <div class="table-header">
      <div class="header-left">
        <el-input
          v-model="searchQuery"
          placeholder="请输入关键词搜索"
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
      :data="dataList"
      stripe
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column prop="id" label="ID" width="80" />
      <template v-for="column in columns" :key="column.prop">
        <el-table-column
          :prop="column.prop"
          :label="column.label"
          :min-width="column.minWidth || 120"
          :fixed="column.fixed"
        >
          <template v-if="column.template" #default="scope">
            <component :is="column.template" :row="scope.row" />
          </template>
          <template v-else-if="column.slotName" #default="scope">
            <slot :name="column.slotName" :row="scope.row" />
          </template>
        </el-table-column>
      </template>
      <el-table-column label="操作" width="200" fixed="right">
        <template #default="scope">
          <el-button
            v-if="hasPermission('view')"
            type="primary"
            size="small"
            @click="handleView(scope.row.id)"
          >
            <el-icon><View /></el-icon>查看
          </el-button>
          <el-button
            v-if="hasPermission('edit')"
            type="warning"
            size="small"
            @click="handleEdit(scope.row.id)"
          >
            <el-icon><Edit /></el-icon>编辑
          </el-button>
          <el-button
            v-if="hasPermission('delete')"
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

interface Column {
  prop: string
  label: string
  minWidth?: number
  fixed?: string
  template?: any
  slotName?: string
}

interface Pagination {
  currentPage: number
  pageSize: number
  total: number
}

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

// 权限检查
const props = defineProps<{
  loading?: boolean
  dataList: any[]
  columns: Column[]
  pagination: Pagination
  permissions?: string[]
}>()

const hasPermission = (permission: string) => {
  if (!props.permissions) return true
  return props.permissions.includes(permission)
}

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
const handleSelectionChange = (selection: any[]) => {
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
.base-table-container {
  background-color: white;
  border-radius: 4px;
  padding: 15px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  overflow-x: auto;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .base-table-container {
    padding: 10px;
  }
  
  .table-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 10px;
  }
  
  .header-left {
    flex-wrap: wrap;
    gap: 8px;
  }
  
  .el-input {
    width: 100% !important;
  }
  
  .table-pagination {
    justify-content: center;
  }
  
  .el-pagination {
    font-size: 12px;
  }
  
  .el-table {
    font-size: 12px;
  }
  
  .el-table-column {
    min-width: 100px;
  }
  
  .el-button {
    padding: 4px 8px;
    font-size: 12px;
  }
  
  .el-icon {
    font-size: 12px;
  }
}

@media (max-width: 576px) {
  .base-table-container {
    padding: 5px;
  }
  
  .table-header {
    gap: 5px;
  }
  
  .header-left {
    gap: 5px;
  }
  
  .el-table {
    font-size: 11px;
  }
  
  .el-table-column {
    min-width: 80px;
  }
  
  .el-button {
    padding: 2px 6px;
    font-size: 11px;
  }
  
  .el-button--small {
    padding: 2px 6px;
  }
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
