<template>
  <div class="repair-list-container">
    <el-card shadow="never" class="page-card">
      <template #header>
        <div class="card-header">
          <h2 class="page-title">设备维修管理</h2>
        </div>
      </template>
      
      <BaseTable
        :loading="loading"
        :dataList="repairList"
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
import { repairApi } from '../api/repair'
import { usePolling } from '../composables/usePolling'
import type { EquipmentRepair } from '../types/equipment'
// 从repair API导入后端设备维修类型定义
import type { BackendEquipmentRepair } from '../api/repair'

const router = useRouter()
const loading = ref(false)
const searchQuery = ref('')
const repairList = ref<EquipmentRepair[]>([])
const pagination = reactive({
  currentPage: 1,
  pageSize: 10,
  total: 0
})

// 表格列配置
const columns = ref([
  {
    prop: 'id',
    label: 'ID',
    minWidth: 80
  },
  {
    prop: 'repairNumber',
    label: '维修编号',
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
    prop: 'repairTime',
    label: '维修时间',
    minWidth: 180
  },
  {
    prop: 'faultDescription',
    label: '故障描述',
    minWidth: 200
  },
  {
    prop: 'repairPerson',
    label: '维修人员',
    minWidth: 120
  },
  {
    prop: 'status',
    label: '状态',
    minWidth: 100
  }
])

// 加载设备维修列表
const loadRepairList = async () => {
  loading.value = true
  try {
    // 初始化数据，避免之前的数据影响
    repairList.value = []
    pagination.total = 0
    
    // 简化API调用，后端不支持分页和搜索参数
    // 直接获取维修列表，后端返回的是数组格式
    const repairData = await repairApi.getRepairList() as BackendEquipmentRepair[]
    // axios响应拦截器已直接返回data，无需再访问result.data
    
    // 添加调试日志，查看API返回的具体数据结构
    console.log('API返回结果:', repairData)
    console.log('结果类型:', typeof repairData)
    console.log('是否为数组:', Array.isArray(repairData))
    console.log('数据长度:', Array.isArray(repairData) ? repairData.length : 0)
    
    // 确保repairData是数组
    const safeRepairData = Array.isArray(repairData) ? repairData : []
    
    // 总记录数通过过滤后的数据计算，不需要单独声明变量
    
    // 将后端返回的数据映射为前端表格需要的格式
    const mappedRepairs = safeRepairData.map((item, index) => {
      // 状态映射
      const statusMap: Record<number, string> = {
        1: '待维修',
        2: '维修中',
        3: '已完成',
        4: '已取消'
      }
      // 维修结果映射
      const repairResultMap: Record<number, string> = {
        0: '未修复',
        1: '已修复'
      }
      
      // 添加调试日志，查看每个原始数据项的字段值和所有属性
      console.log(`原始数据项 ${index}:`, item)
      console.log(`原始数据项 ${index} 的所有属性:`, Object.keys(item))
      
      // 确保item对象存在，避免访问null/undefined的属性
      const safeItem = item || {};
      
      // 安全获取字段值，处理可能的undefined/null情况以及大小写不一致问题
      const getSafeValue = (key: string, defaultValue: any = '') => {
        console.log(`尝试获取字段 ${key} 的值`);
        
        // 首先尝试直接获取字段值（驼峰式命名）
        let value = (safeItem as any)[key];
        console.log(`直接获取 ${key} 的值:`, value);
        
        // 如果直接获取失败，尝试不区分大小写匹配字段名
        if (value === undefined || value === null) {
          const lowercaseKey = key.toLowerCase();
          console.log(`直接获取失败，尝试不区分大小写匹配字段名 ${lowercaseKey}`);
          
          // 遍历safeItem的所有属性，不区分大小写匹配
          for (const prop in safeItem) {
            if (Object.prototype.hasOwnProperty.call(safeItem, prop)) {
              console.log(`遍历属性 ${prop}，转换为小写 ${prop.toLowerCase()}`);
              if (prop.toLowerCase() === lowercaseKey) {
                value = safeItem[prop as keyof typeof safeItem];
                console.log(`找到匹配的属性 ${prop}，值为 ${value}`);
                break;
              }
            }
          }
        }
        
        const result = value !== undefined && value !== null ? value : defaultValue;
        console.log(`字段 ${key} 的最终值:`, result);
        return result;
      }
      
      // 直接获取所有后端字段的值，用于调试
      console.log(`后端字段值 - 维修编号: ${safeItem.repairCode}, 设备名称: ${safeItem.equipmentName}, 设备编号: ${safeItem.equipmentNumber}, 故障描述: ${safeItem.faultDes}, 维修人员: ${safeItem.repairUser}, 维修时间: ${safeItem.workTime}`);
      
      const mappedItem = {
        id: getSafeValue('id', 0),
        repairNumber: getSafeValue('RepairCode', '无'), // 维修编号映射为后端的故障代码
        equipmentName: getSafeValue('EquipmentName', '无'),
        equipmentNumber: getSafeValue('EquipmentNumber', '无'),
        faultType: getSafeValue('RepairCode', '无'), // 故障类型映射为后端的故障代码
        faultDescription: getSafeValue('FaultDes', '无'), // 故障描述映射为后端的故障描述
        repairPerson: getSafeValue('RepairUser', '无'), // 维修人员映射为后端的维修人员
        repairTime: getSafeValue('WorkTime', '无'), // 维修时间映射为后端的派工时间
        status: statusMap[getSafeValue('Status', 1)] || '待维修', // 状态映射为中文
        repairContent: getSafeValue('RepairExplain', '无'), // 维修内容映射为后端的维修情况说明
        repairResult: repairResultMap[getSafeValue('RepairResult', 1)] || '已修复' // 维修结果映射为中文
      }
      
      // 添加调试日志，查看每个映射后的数据项
      console.log(`映射后的数据项 ${index}:`, mappedItem)
      return mappedItem
    })
    
    // 添加调试日志，查看映射后的数据
    console.log('映射后的数据:', mappedRepairs)
    
    // 前端搜索过滤
    let filteredRepairs = mappedRepairs
    if (searchQuery.value) {
      const query = searchQuery.value.toLowerCase()
      filteredRepairs = mappedRepairs.filter(repair => {
        return (
          repair.repairNumber.toLowerCase().includes(query) ||
          repair.equipmentName.toLowerCase().includes(query) ||
          repair.equipmentNumber.toLowerCase().includes(query)
        )
      })
      console.log('搜索过滤后的数据:', filteredRepairs)
    }
    
    // 前端分页处理
    const startIndex = (pagination.currentPage - 1) * pagination.pageSize
    const endIndex = startIndex + pagination.pageSize
    const paginatedRepairs = filteredRepairs.slice(startIndex, endIndex)
    console.log('分页后的数据:', paginatedRepairs)
    
    // 设置总记录数为过滤后的总数
    pagination.total = filteredRepairs.length
    console.log('过滤后的总记录数:', pagination.total)
    
    // 直接设置维修列表，无需获取设备类型
    repairList.value = paginatedRepairs
    
    // 添加调试日志，查看最终显示的数据
    console.log('最终显示的数据:', repairList.value)
    console.log('最终总记录数:', pagination.total)
    
    // 数据加载成功提示
    if (repairList.value.length > 0) {
      console.log('设备维修列表加载成功')
    } else {
      console.log('设备维修列表为空')
    }
  } catch (error) {
    const errorMsg = error instanceof Error ? error.message : '加载设备维修列表失败'
    ElMessage.error(errorMsg)
    console.error('加载设备维修列表失败:', error)
    // 确保错误时列表为空
    repairList.value = []
    pagination.total = 0
  } finally {
    loading.value = false
  }
}

// 使用定时轮询，每30秒更新一次数据
usePolling(loadRepairList, 30000)

// 搜索
const handleSearch = (query: string) => {
  searchQuery.value = query
  pagination.currentPage = 1
  // 直接重新加载数据，前端会进行过滤
  loadRepairList()
}

// 新增
const handleCreate = () => {
  router.push('/repair/create')
}

// 查看详情
const handleView = (id: number) => {
  router.push(`/repair/${id}`)
}

// 编辑
const handleEdit = (id: number) => {
  router.push(`/repair/edit/${id}`)
}

// 删除
const handleDelete = async (id: number) => {
  try {
    await repairApi.deleteRepairById(id)
    ElMessage.success('删除成功')
    loadRepairList()
  } catch (error) {
    ElMessage.error('删除失败')
    console.error('删除设备维修失败:', error)
  }
}

// 批量删除
const handleBatchDelete = async (ids: number[]) => {
  try {
    for (const id of ids) {
      await repairApi.deleteRepairById(id)
    }
    ElMessage.success('批量删除成功')
    loadRepairList()
  } catch (error) {
    ElMessage.error('批量删除失败')
    console.error('批量删除设备维修失败:', error)
  }
}

// 分页大小变化
const handleSizeChange = (size: number) => {
  pagination.pageSize = size
  loadRepairList()
}

// 当前页变化
const handleCurrentChange = (page: number) => {
  pagination.currentPage = page
  loadRepairList()
}
</script>

<style scoped>
.repair-list-container {
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
