import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      redirect: '/equipment'
    },
    // 设备管理模块
    {
      path: '/equipment',
      name: 'equipment-list',
      component: () => import('../views/EquipmentList.vue')
    },
    {
      path: '/equipment/:id',
      name: 'equipment-detail',
      component: () => import('../views/EquipmentDetail.vue')
    },
    {
      path: '/equipment/edit/:id',
      name: 'equipment-edit',
      component: () => import('../views/EquipmentEdit.vue')
    },
    {
      path: '/equipment/create',
      name: 'equipment-create',
      component: () => import('../views/EquipmentEdit.vue')
    },
    // 故障代码模块
    {
      path: '/fault-code',
      name: 'fault-code-list',
      component: () => import('../views/FaultCodeList.vue')
    },
    {
      path: '/fault-code/create',
      name: 'fault-code-create',
      component: () => import('../views/FaultCodeEdit.vue')
    },
    {
      path: '/fault-code/edit/:id',
      name: 'fault-code-edit',
      component: () => import('../views/FaultCodeEdit.vue')
    },
    // 设备点检模块
    {
      path: '/inspection',
      name: 'inspection-list',
      component: () => import('../views/InspectionList.vue')
    },
    {
      path: '/inspection/create',
      name: 'inspection-create',
      component: () => import('../views/InspectionEdit.vue')
    },
    {
      path: '/inspection/edit/:id',
      name: 'inspection-edit',
      component: () => import('../views/InspectionEdit.vue')
    },
    {
      path: '/inspection/:id',
      name: 'inspection-detail',
      component: () => import('../views/InspectionDetail.vue')
    },
    // 设备保养模块
    {
      path: '/maintenance',
      name: 'maintenance-list',
      component: () => import('../views/MaintenanceList.vue')
    },
    {
      path: '/maintenance/create',
      name: 'maintenance-create',
      component: () => import('../views/MaintenanceEdit.vue')
    },
    {
      path: '/maintenance/edit/:id',
      name: 'maintenance-edit',
      component: () => import('../views/MaintenanceEdit.vue')
    },
    {
      path: '/maintenance/:id',
      name: 'maintenance-detail',
      component: () => import('../views/MaintenanceDetail.vue')
    },
    // 设备维修模块
    {
      path: '/repair',
      name: 'repair-list',
      component: () => import('../views/RepairList.vue')
    },
    {
      path: '/repair/create',
      name: 'repair-create',
      component: () => import('../views/RepairEdit.vue')
    },
    {
      path: '/repair/edit/:id',
      name: 'repair-edit',
      component: () => import('../views/RepairEdit.vue')
    },
    {
      path: '/repair/:id',
      name: 'repair-detail',
      component: () => import('../views/RepairDetail.vue')
    }
  ]
})

export default router