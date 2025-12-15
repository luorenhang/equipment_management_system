import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import { useUserStore } from '../stores/user'

// 定义路由配置
const routes: RouteRecordRaw[] = [
  {
    path: '/',
    redirect: '/equipment'
  },
  // 登录注册模块
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/Login.vue'),
    meta: {
      requiresAuth: false
    }
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('../views/Register.vue'),
    meta: {
      requiresAuth: false
    }
  },
  // 设备管理模块
  {
    path: '/equipment',
    name: 'equipment-list',
    component: () => import('../views/EquipmentList.vue'),
    meta: {
      requiresAuth: true
    }
  },
  {
    path: '/equipment/:id',
    name: 'equipment-detail',
    component: () => import('../views/EquipmentDetail.vue'),
    meta: {
      requiresAuth: true
    }
  },
  {
    path: '/equipment/edit/:id',
    name: 'equipment-edit',
    component: () => import('../views/EquipmentEdit.vue'),
    meta: {
      requiresAuth: true
    }
  },
  {
    path: '/equipment/create',
    name: 'equipment-create',
    component: () => import('../views/EquipmentEdit.vue'),
    meta: {
      requiresAuth: true
    }
  },
  // 故障代码模块
  {
    path: '/fault-code',
    name: 'fault-code-list',
    component: () => import('../views/FaultCodeList.vue'),
    meta: {
      requiresAuth: true
    }
  },
  {
    path: '/fault-code/create',
    name: 'fault-code-create',
    component: () => import('../views/FaultCodeEdit.vue'),
    meta: {
      requiresAuth: true
    }
  },
  {
    path: '/fault-code/edit/:id',
    name: 'fault-code-edit',
    component: () => import('../views/FaultCodeEdit.vue'),
    meta: {
      requiresAuth: true
    }
  },
  // 设备点检模块
  {
    path: '/inspection',
    name: 'inspection-list',
    component: () => import('../views/InspectionList.vue'),
    meta: {
      requiresAuth: true
    }
  },
  {
    path: '/inspection/create',
    name: 'inspection-create',
    component: () => import('../views/InspectionEdit.vue'),
    meta: {
      requiresAuth: true
    }
  },
  {
    path: '/inspection/edit/:id',
    name: 'inspection-edit',
    component: () => import('../views/InspectionEdit.vue'),
    meta: {
      requiresAuth: true
    }
  },
  {
    path: '/inspection/:id',
    name: 'inspection-detail',
    component: () => import('../views/InspectionDetail.vue'),
    meta: {
      requiresAuth: true
    }
  },
  // 设备保养模块
  {
    path: '/maintenance',
    name: 'maintenance-list',
    component: () => import('../views/MaintenanceList.vue'),
    meta: {
      requiresAuth: true
    }
  },
  {
    path: '/maintenance/create',
    name: 'maintenance-create',
    component: () => import('../views/MaintenanceEdit.vue'),
    meta: {
      requiresAuth: true
    }
  },
  {
    path: '/maintenance/edit/:id',
    name: 'maintenance-edit',
    component: () => import('../views/MaintenanceEdit.vue'),
    meta: {
      requiresAuth: true
    }
  },
  {
    path: '/maintenance/:id',
    name: 'maintenance-detail',
    component: () => import('../views/MaintenanceDetail.vue'),
    meta: {
      requiresAuth: true
    }
  },
  // 设备维修模块
  {
    path: '/repair',
    name: 'repair-list',
    component: () => import('../views/RepairList.vue'),
    meta: {
      requiresAuth: true
    }
  },
  {
    path: '/repair/create',
    name: 'repair-create',
    component: () => import('../views/RepairEdit.vue'),
    meta: {
      requiresAuth: true
    }
  },
  {
    path: '/repair/edit/:id',
    name: 'repair-edit',
    component: () => import('../views/RepairEdit.vue'),
    meta: {
      requiresAuth: true
    }
  },
  {
    path: '/repair/:id',
    name: 'repair-detail',
    component: () => import('../views/RepairDetail.vue'),
    meta: {
      requiresAuth: true
    }
  }
]

// 创建路由实例
const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, _from, next) => {
  const userStore = useUserStore()
  const isLoggedIn = userStore.isLoggedIn
  
  // 需要认证的页面
  if (to.meta.requiresAuth) {
    if (isLoggedIn) {
      next()
    } else {
      // 未登录，跳转到登录页
      next('/login')
    }
  } else {
    // 不需要认证的页面（登录、注册）
    if (isLoggedIn && (to.path === '/login' || to.path === '/register')) {
      // 已登录，跳转到首页
      next('/')
    } else {
      next()
    }
  }
})

export default router