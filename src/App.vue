<template>
  <div class="app-container">
    <el-container>
      <!-- 侧边栏导航 -->
      <el-aside width="200px" class="aside-container">
        <div class="logo-container">
          <h1 class="logo">设备管理系统</h1>
        </div>
        <el-menu
          default-active="/equipment"
          class="el-menu-vertical-demo"
          @select="handleMenuSelect"
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
                  <el-dropdown-item>个人中心</el-dropdown-item>
                  <el-dropdown-item divided>退出登录</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </div>
        </el-header>
        <el-main class="main-container">
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { 
  Management, 
  WarningFilled, 
  Calendar, 
  Tools, 
  User, 
  ArrowDown 
} from '@element-plus/icons-vue'

const router = useRouter()

// 菜单选择处理
const handleMenuSelect = (index: string) => {
  router.push(index)
}
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: Arial, sans-serif;
  background-color: #f5f7fa;
}

.app-container {
  width: 100%;
  height: 100vh;
  overflow: hidden;
}

.aside-container {
  background-color: #001529;
  height: 100vh;
  overflow-y: auto;
}

.logo-container {
  padding: 0 20px;
  height: 60px;
  line-height: 60px;
  background-color: #002140;
}

.logo {
  color: white;
  font-size: 18px;
  font-weight: bold;
  margin: 0;
}

.header-container {
  background-color: white;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding: 0 20px;
}

.main-container {
  padding: 10px;
  background-color: #f5f7fa;
  overflow-y: auto;
  min-height: calc(100vh - 60px);
  box-sizing: border-box;
}

/* 响应式设计 */
@media (max-width: 1200px) {
  .aside-container {
    width: 180px;
  }
  
  .main-container {
    padding: 15px;
  }
}

@media (max-width: 992px) {
  .aside-container {
    width: 160px;
  }
  
  .logo {
    font-size: 16px;
  }
  
  .main-container {
    padding: 10px;
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
  }
  
  .logo-container {
    height: 50px;
  }
  
  .el-menu-vertical-demo {
    display: flex;
    flex-direction: row;
    overflow-x: auto;
  }
  
  .el-menu-item {
    flex: 0 0 auto;
    min-width: 100px;
  }
  
  .main-container {
    padding: 8px;
    min-height: calc(100vh - 110px);
  }
}

@media (max-width: 576px) {
  .main-container {
    padding: 5px;
  }
  
  .el-menu-item {
    min-width: 80px;
    font-size: 12px;
  }
  
  .page-title {
    font-size: 16px;
  }
}

.user-info {
  display: flex;
  align-items: center;
  gap: 5px;
  cursor: pointer;
}

.el-menu-vertical-demo {
  background-color: transparent;
  border-right: none;
}

.el-menu-item {
  color: rgba(255, 255, 255, 0.85);
}

.el-menu-item.is-active {
  color: #409eff;
  background-color: rgba(64, 158, 255, 0.1);
}

.el-menu-item:hover {
  background-color: rgba(255, 255, 255, 0.1);
}
</style>