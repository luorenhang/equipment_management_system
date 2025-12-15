<template>
  <div class="equipment-table-container">
    <!-- 表格头部 -->
    <div class="table-header">
      <div class="header-left">
        <div class="search-box">
          <el-input
            v-model="searchQuery"
            placeholder="请输入设备名称或编号"
            clearable
            :prefix-icon="Search"
            @keyup.enter="handleSearch"
          />
          <el-button type="primary" @click="handleSearch" class="search-btn">
            <el-icon><Search /></el-icon>搜索
          </el-button>
        </div>
        <el-button type="primary" @click="handleCreate" class="create-btn">
          <el-icon><Plus /></el-icon>新增
        </el-button>
      </div>
      <div class="header-right">
        <el-dropdown v-if="selectedIds.length > 0">
          <el-button type="warning" class="batch-btn">
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
      class="equipment-table"
      :row-class-name="tableRowClassName"
      style="width: 100%"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="equipmentName" label="设备名称" min-width="180">
        <template #default="scope">
          <div class="equipment-name">{{ scope.row.equipmentName }}</div>
        </template>
      </el-table-column>
      <el-table-column prop="equipmentNumber" label="设备编号" min-width="180" />
      <el-table-column prop="storageLocation" label="存放地点" min-width="150" />
      <el-table-column prop="productionLine" label="产线" min-width="150" />
      <el-table-column prop="deviceType" label="设备类型" min-width="150" />
      <el-table-column prop="equipmentStatus" label="设备状态" min-width="120">
        <template #default="scope">
          <el-tag
            :type="
              scope.row.equipmentStatus === '运行中' ? 'success' :
              scope.row.equipmentStatus === '待机' ? 'info' : 'danger'
            "
            size="large"
          >
            {{ scope.row.equipmentStatus }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态" min-width="120">
        <template #default="scope">
          <el-tag
            :type="
              scope.row.status === '正常' ? 'success' :
              scope.row.status === '维修中' ? 'warning' : 'danger'
            "
            size="large"
          >
            {{ scope.row.status }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="240" fixed="right">
        <template #default="scope">
          <div class="operation-buttons">
            <el-button
              type="primary"
              size="small"
              @click="handleView(scope.row.id)"
              class="view-btn"
            >
              <el-icon><View /></el-icon>查看
            </el-button>
            <el-button
              type="warning"
              size="small"
              @click="handleEdit(scope.row.id)"
              class="edit-btn"
            >
              <el-icon><Edit /></el-icon>编辑
            </el-button>
            <el-button
              type="danger"
              size="small"
              @click="handleDelete(scope.row.id)"
              class="delete-btn"
            >
              <el-icon><Delete /></el-icon>删除
            </el-button>
          </div>
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
        background
        class="pagination"
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

// 表格行样式
const tableRowClassName = () => {
  return 'table-row'
}
</script>

<style scoped>
.equipment-table-container {
  background-color: white;
  border-radius: 8px;
  padding: 24px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
  transition: all 0.3s ease;
}

.equipment-table-container:hover {
  box-shadow: 0 4px 16px 0 rgba(0, 0, 0, 0.08);
}

/* 表格头部 */
.table-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
  padding-bottom: 16px;
  border-bottom: 2px solid #f0f2f5;
}

.header-left {
  display: flex;
  gap: 16px;
  align-items: center;
  flex-wrap: wrap;
}

.search-box {
  display: flex;
  gap: 10px;
  align-items: center;
}

.search-box .el-input {
  width: 320px;
}

.header-right {
  display: flex;
  gap: 16px;
  flex-wrap: wrap;
}

/* 按钮样式 */
.el-button {
  border-radius: 6px;
  transition: all 0.3s ease;
  font-weight: 500;
  padding: 8px 16px;
}

.search-btn {
  background-color: #409eff;
  border-color: #409eff;
}

.search-btn:hover {
  background-color: #66b1ff;
  border-color: #66b1ff;
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.4);
}

.create-btn {
  background-color: #67c23a;
  border-color: #67c23a;
}

.create-btn:hover {
  background-color: #85ce61;
  border-color: #85ce61;
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(103, 194, 58, 0.4);
}

.batch-btn {
  background-color: #e6a23c;
  border-color: #e6a23c;
}

.batch-btn:hover {
  background-color: #ebb563;
  border-color: #ebb563;
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(230, 162, 60, 0.4);
}

/* 表格样式 */
.equipment-table {
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  border: 1px solid #e9ecef;
}

.equipment-table::before {
  display: none;
}

.equipment-table .el-table__header-wrapper {
  background: linear-gradient(135deg, #f5f7fa 0%, #e9ecef 100%);
  border-bottom: 2px solid #dee2e6;
}

.equipment-table .el-table__header-wrapper .el-table__header {
  border: none;
}

.equipment-table .el-table__header-wrapper th {
  background: transparent;
  font-weight: 600;
  color: #303133;
  font-size: 14px;
  padding: 12px 0;
  border-bottom: none;
}

.table-row {
  transition: all 0.3s ease;
  cursor: pointer;
}

.table-row:hover {
  background-color: #f0f5ff !important;
}

.equipment-name {
  font-weight: 500;
  color: #303133;
}

/* 操作按钮 */
.operation-buttons {
  display: flex;
  gap: 8px;
}

.view-btn {
  background-color: #409eff;
  border-color: #409eff;
}

.view-btn:hover {
  background-color: #66b1ff;
  border-color: #66b1ff;
  transform: translateY(-1px);
  box-shadow: 0 2px 8px rgba(64, 158, 255, 0.4);
}

.edit-btn {
  background-color: #e6a23c;
  border-color: #e6a23c;
}

.edit-btn:hover {
  background-color: #ebb563;
  border-color: #ebb563;
  transform: translateY(-1px);
  box-shadow: 0 2px 8px rgba(230, 162, 60, 0.4);
}

.delete-btn {
  background-color: #f56c6c;
  border-color: #f56c6c;
}

.delete-btn:hover {
  background-color: #f78989;
  border-color: #f78989;
  transform: translateY(-1px);
  box-shadow: 0 2px 8px rgba(245, 108, 108, 0.4);
}

/* 标签样式 */
.el-tag {
  font-weight: 500;
  padding: 4px 12px;
  border-radius: 16px;
}

/* 分页样式 */
.table-pagination {
  margin-top: 24px;
  display: flex;
  justify-content: flex-end;
  padding-top: 16px;
  border-top: 2px solid #f0f2f5;
}

.pagination {
  --el-pagination-button-bg-color: #f5f7fa;
  --el-pagination-button-border-color: #dcdfe6;
  --el-pagination-button-text-color: #606266;
  --el-pagination-button-hover-bg-color: #ecf5ff;
  --el-pagination-button-hover-border-color: #c6e2ff;
  --el-pagination-button-hover-text-color: #409eff;
  --el-pagination-button-active-bg-color: #409eff;
  --el-pagination-button-active-border-color: #409eff;
  --el-pagination-button-active-text-color: #fff;
}

/* 响应式设计 */
@media (max-width: 1200px) {
  .search-box .el-input {
    width: 250px;
  }
  
  .table-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 16px;
  }
  
  .header-right {
    width: 100%;
    justify-content: flex-start;
  }
}

@media (max-width: 768px) {
  .equipment-table-container {
    padding: 16px;
  }
  
  .search-box .el-input {
    width: 200px;
  }
  
  .header-left {
    flex-direction: column;
    align-items: flex-start;
    gap: 12px;
  }
  
  .operation-buttons {
    flex-direction: column;
    gap: 6px;
  }
  
  .operation-buttons .el-button {
    width: 100%;
  }
}

@media (max-width: 576px) {
  .search-box {
    flex-direction: column;
    align-items: flex-start;
    gap: 8px;
  }
  
  .search-box .el-input {
    width: 100%;
  }
}
</style>