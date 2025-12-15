<template>
  <!-- 根据当前路由决定是否显示完整布局 -->
  <div v-if="isAuthPage" class="auth-page-container">
    <router-view v-slot="{ Component }">
      <transition name="fade" mode="out-in">
        <component :is="Component" />
      </transition>
    </router-view>
  </div>
  <div v-else class="app-container">
    <el-container class="main-layout">
      <!-- 侧边栏导航 -->
      <el-aside width="220px" class="aside-container">
        <div class="logo-container">
          <h1 class="logo">设备管理系统</h1>
        </div>
        <el-menu
          default-active="/equipment"
          class="el-menu-vertical-demo"
          @select="handleMenuSelect"
          :collapse-transition="false"
        >
          <el-menu-item index="/equipment">
            <el-icon><Management /></el-icon>
            <span>设备管理</span>
          </el-menu-item>
          <el-menu-item index="/fault-code">
            <el-icon><WarningFilled /></el-icon>
            <span>故障代码</span>
          </el-menu-item>
          <el-menu-item index="/inspection">
            <el-icon><Calendar /></el-icon>
            <span>设备点检</span>
          </el-menu-item>
          <el-menu-item index="/maintenance">
            <el-icon><Tools /></el-icon>
            <span>设备保养</span>
          </el-menu-item>
          <el-menu-item index="/repair">
            <el-icon><Tools /></el-icon>
            <span>设备维修</span>
          </el-menu-item>
        </el-menu>
      </el-aside>
      <!-- 主内容区域 -->
      <el-container>
        <el-header class="header-container">
          <div class="header-right">
            <el-dropdown>
              <span class="user-info">
                <el-icon><User /></el-icon>
                管理员
                <el-icon class="el-icon--right"><ArrowDown /></el-icon>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item @click="handleLogout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </el-header>
        <el-main class="main-container">
          <div class="content-wrapper">
            <router-view v-slot="{ Component }">
              <transition name="slide-fade" mode="out-in">
                <component :is="Component" />
              </transition>
            </router-view>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import { useRouter, useRoute } from 'vue-router'
import { computed } from 'vue'
import { 
  Management, 
  WarningFilled, 
  Calendar, 
  Tools, 
  User, 
  ArrowDown 
} from '@element-plus/icons-vue'
import { useUserStore } from './stores/user'
import { logout as logoutApi } from './api/user'
import { ElMessage } from 'element-plus'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

// 判断当前是否为认证页面（登录/注册）
const isAuthPage = computed(() => {
  const { path } = route
  return path === '/login' || path === '/register'
})

// 菜单选择处理
const handleMenuSelect = (index: string) => {
  router.push(index)
}

// 退出登录处理
const handleLogout = async () => {
  try {
    // 调用后端登出API
    await logoutApi()
    // 清除本地状态
    userStore.logout()
    // 跳转到登录页面
    router.push('/login')
    ElMessage.success('退出登录成功')
  } catch (error: any) {
    // 即使API调用失败，也要清除本地状态
    userStore.logout()
    router.push('/login')
    ElMessage.error('退出登录成功')
  }
}
</script>

<style>
/* 主题色配置 - CSS变量 */
:root {
  /* 主色调 */
  --primary-color: #1890ff;
  --primary-color-light: #66b1ff;
  --primary-color-dark: #096dd9;
  --primary-gradient: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  
  /* 辅助色 */
  --success-color: #67c23a;
  --warning-color: #e6a23c;
  --danger-color: #f56c6c;
  --info-color: #909399;
  
  /* 背景色 */
  --bg-primary: #ffffff;
  --bg-secondary: #f0f2f5;
  --bg-tertiary: #f5f7fa;
  --bg-gradient: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  
  /* 文本色 */
  --text-primary: #303133;
  --text-secondary: #606266;
  --text-tertiary: #909399;
  --text-placeholder: #c0c4cc;
  
  /* 边框色 */
  --border-color: #dcdfe6;
  --border-light: #e9ecef;
  --border-dark: #dee2e6;
  
  /* 阴影 */
  --shadow-sm: 0 2px 8px rgba(0, 0, 0, 0.05);
  --shadow-md: 0 4px 12px rgba(0, 0, 0, 0.08);
  --shadow-lg: 0 6px 20px rgba(0, 0, 0, 0.12);
  
  /* 圆角 */
  --border-radius-sm: 4px;
  --border-radius-md: 6px;
  --border-radius-lg: 8px;
  --border-radius-xl: 16px;
  
  /* 过渡动画 */
  --transition-fast: 0.2s ease;
  --transition-normal: 0.3s ease;
  --transition-slow: 0.4s ease;
}

/* 全局样式重置 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
  background-color: var(--bg-secondary);
  color: var(--text-primary);
}

/* 认证页面容器 */
.auth-page-container {
  width: 100%;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: var(--bg-gradient);
  overflow: hidden;
}

/* 主应用容器 */
.app-container {
  width: 100%;
  height: 100vh;
  overflow: hidden;
}

.main-layout {
  height: 100%;
  background-color: var(--bg-secondary);
}

/* 侧边栏容器 */
.aside-container {
  background: linear-gradient(180deg, var(--primary-color) 0%, var(--primary-color-dark) 100%);
  height: 100vh;
  overflow-y: auto;
  box-shadow: 2px 0 8px rgba(0, 0, 0, 0.1);
  transition: all var(--transition-normal);
}

/* 侧边栏悬停效果 */
.aside-container:hover {
  box-shadow: 4px 0 12px rgba(0, 0, 0, 0.15);
}

/* Logo容器 */
.logo-container {
  padding: 0 20px;
  height: 64px;
  line-height: 64px;
  background-color: rgba(255, 255, 255, 0.1);
  border-bottom: 1px solid rgba(255, 255, 255, 0.2);
}

/* Logo样式 */
.logo {
  color: white;
  font-size: 20px;
  font-weight: bold;
  margin: 0;
  text-align: center;
  letter-spacing: 1px;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

/* 头部容器 */
.header-container {
  background-color: var(--bg-primary);
  box-shadow: var(--shadow-sm);
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding: 0 24px;
  height: 64px;
  z-index: 100;
}

/* 主内容区域 */
.main-container {
  padding: 20px;
  background-color: var(--bg-secondary);
  overflow-y: auto;
  min-height: calc(100vh - 64px);
  box-sizing: border-box;
}

/* 内容包装器 */
.content-wrapper {
  background-color: var(--bg-primary);
  border-radius: var(--border-radius-lg);
  box-shadow: var(--shadow-sm);
  padding: 20px;
  transition: all var(--transition-normal);
}

/* 内容包装器悬停效果 */
.content-wrapper:hover {
  box-shadow: var(--shadow-md);
}

/* 用户信息样式 */
.user-info {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
  padding: 8px 12px;
  border-radius: var(--border-radius-md);
  transition: all var(--transition-normal);
  color: var(--text-secondary);
  font-weight: 500;
}

/* 用户信息悬停效果 */
.user-info:hover {
  background-color: var(--bg-tertiary);
  color: var(--primary-color);
  transform: translateY(-1px);
  box-shadow: var(--shadow-sm);
}

/* 侧边栏菜单样式 */
.el-menu-vertical-demo {
  background-color: transparent;
  border-right: none;
  height: calc(100vh - 64px);
}

/* 菜单项样式 */
.el-menu-item {
  color: rgba(255, 255, 255, 0.9);
  height: 50px;
  line-height: 50px;
  font-size: 14px;
  margin: 4px 8px;
  border-radius: var(--border-radius-md);
  transition: all var(--transition-normal);
}

/* 菜单项悬停效果 */
.el-menu-item:hover {
  background-color: rgba(255, 255, 255, 0.2);
  color: white;
  transform: translateX(5px);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
}

/* 激活菜单项样式 */
.el-menu-item.is-active {
  color: white;
  background-color: rgba(255, 255, 255, 0.3);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
  transform: translateX(5px);
}

/* 菜单图标样式 */
.el-menu-item .el-icon {
  font-size: 18px;
  margin-right: 12px;
  transition: all var(--transition-normal);
}

.el-menu-item:hover .el-icon {
  transform: scale(1.1);
}

/* 路由过渡动画 */
/* 淡入动画（用于登录注册页） */
.fade-enter-active,
.fade-leave-active {
  transition: opacity var(--transition-normal);
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

/* 滑动淡入动画（用于主内容区） */
.slide-fade-enter-active {
  transition: all var(--transition-slow);
}

.slide-fade-leave-active {
  transition: all var(--transition-normal) cubic-bezier(1, 0.5, 0.8, 1);
}

.slide-fade-enter-from,
.slide-fade-leave-to {
  transform: translateY(20px);
  opacity: 0;
}

/* 淡入动画 */
.fade-in {
  animation: fadeIn var(--transition-normal);
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* 页面标题样式 */
.page-title {
  font-size: 24px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 20px;
  padding-bottom: 12px;
  border-bottom: 2px solid var(--primary-color);
  display: flex;
  align-items: center;
  gap: 12px;
  animation: slideInLeft var(--transition-slow);
}

/* 从左侧滑入动画 */
@keyframes slideInLeft {
  from {
    opacity: 0;
    transform: translateX(-20px);
  }
  to {
    opacity: 1;
    transform: translateX(0);
  }
}

/* 卡片样式优化 */
.el-card {
  border-radius: var(--border-radius-lg);
  box-shadow: var(--shadow-sm);
  transition: all var(--transition-normal);
}

.el-card:hover {
  box-shadow: var(--shadow-lg);
  transform: translateY(-3px);
}

/* 按钮样式优化 */
.el-button {
  border-radius: var(--border-radius-md);
  transition: all var(--transition-normal);
  font-weight: 500;
  position: relative;
  overflow: hidden;
}

/* 按钮点击波纹效果 */
.el-button::after {
  content: '';
  position: absolute;
  top: 50%;
  left: 50%;
  width: 0;
  height: 0;
  border-radius: 50%;
  background-color: rgba(255, 255, 255, 0.3);
  transform: translate(-50%, -50%);
  transition: width 0.6s, height 0.6s;
}

.el-button:active::after {
  width: 300px;
  height: 300px;
}

/* 输入框样式优化 */
.el-input__wrapper {
  border-radius: var(--border-radius-md);
  transition: all var(--transition-normal);
}

.el-input__wrapper:focus-within {
  box-shadow: 0 0 0 2px rgba(24, 144, 255, 0.2);
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(24, 144, 255, 0.2);
}

.el-input__wrapper:hover {
  transform: translateY(-1px);
  box-shadow: var(--shadow-sm);
}

/* 标签样式优化 */
.el-tag {
  transition: all var(--transition-normal);
  font-weight: 500;
}

.el-tag:hover {
  transform: scale(1.05);
  box-shadow: var(--shadow-sm);
}

/* 表格样式优化 */
.el-table {
  transition: all var(--transition-normal);
}

.el-table__body tr:hover > td {
  background-color: #f0f5ff !important;
  transition: all var(--transition-normal);
}

/* 分页样式优化 */
.el-pagination {
  transition: all var(--transition-normal);
}

.el-pagination__item:hover {
  transform: translateY(-1px);
}

/* 响应式设计 */
@media (max-width: 1200px) {
  .aside-container {
    width: 200px;
  }
  
  .main-container {
    padding: 16px;
  }
  
  .content-wrapper {
    padding: 16px;
  }
}

@media (max-width: 992px) {
  .aside-container {
    width: 180px;
  }
  
  .logo {
    font-size: 18px;
  }
  
  .main-container {
    padding: 12px;
  }
  
  .content-wrapper {
    padding: 12px;
  }
}

@media (max-width: 768px) {
  .app-container {
    flex-direction: column;
  }
  
  .aside-container {
    width: 100%;
    height: auto;
    position: relative;
    box-shadow: var(--shadow-sm);
  }
  
  .logo-container {
    height: 56px;
    line-height: 56px;
  }
  
  .el-menu-vertical-demo {
    display: flex;
    flex-direction: row;
    overflow-x: auto;
    height: auto;
    background-color: rgba(255, 255, 255, 0.1);
  }
  
  .el-menu-item {
    flex: 0 0 auto;
    min-width: 100px;
    margin: 0;
    border-radius: 0;
    transform: none !important;
  }
  
  .main-container {
    padding: 10px;
    min-height: calc(100vh - 112px);
  }
  
  .content-wrapper {
    padding: 10px;
  }
}

@media (max-width: 576px) {
  .main-container {
    padding: 8px;
  }
  
  .content-wrapper {
    padding: 8px;
  }
  
  .el-menu-item {
    min-width: 80px;
    font-size: 12px;
  }
  
  .logo {
    font-size: 16px;
  }
  
  .page-title {
    font-size: 20px;
  }
}

/* 滚动条样式优化 */
::-webkit-scrollbar {
  width: 8px;
  height: 8px;
}

::-webkit-scrollbar-track {
  background: var(--bg-tertiary);
  border-radius: var(--border-radius-sm);
}

::-webkit-scrollbar-thumb {
  background: var(--text-tertiary);
  border-radius: var(--border-radius-sm);
  transition: all var(--transition-normal);
}

::-webkit-scrollbar-thumb:hover {
  background: var(--text-secondary);
  transform: scale(1.1);
}
</style>