<template>
  <div class="register-container">
    <div class="register-background">
      <!-- 背景装饰元素 -->
      <div class="background-shapes">
        <div class="shape shape-1"></div>
        <div class="shape shape-2"></div>
        <div class="shape shape-3"></div>
        <div class="shape shape-4"></div>
      </div>
    </div>
    <el-card class="register-card" shadow="hover">
      <template #header>
        <div class="register-title">
          <div class="logo-icon">
            <el-icon class="big-icon"><Management /></el-icon>
          </div>
          <h2>设备管理系统</h2>
          <p>创建您的账号</p>
        </div>
      </template>
      <el-form
        ref="registerFormRef"
        :model="registerForm"
        :rules="registerRules"
        class="register-form"
        label-position="top"
      >
        <el-form-item label="用户名" prop="username">
          <el-input
            v-model="registerForm.username"
            placeholder="请输入用户名"
            prefix-icon="User"
            clearable
            size="large"
          ></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input
            v-model="registerForm.email"
            type="email"
            placeholder="请输入邮箱"
            prefix-icon="Message"
            clearable
            size="large"
          ></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input
            v-model="registerForm.password"
            type="password"
            placeholder="请输入密码"
            prefix-icon="Lock"
            show-password
            clearable
            size="large"
          ></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input
            v-model="registerForm.confirmPassword"
            type="password"
            placeholder="请再次输入密码"
            prefix-icon="Lock"
            show-password
            clearable
            size="large"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            @click="handleRegister"
            :loading="loading"
            block
            size="large"
            class="register-button"
          >
            注册
          </el-button>
        </el-form-item>
        <div class="login-link">
          <span>已有账号？</span>
          <el-link type="primary" @click="$router.push('/login')" class="login-btn">立即登录</el-link>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { Management } from '@element-plus/icons-vue'
import { register as registerApi, RegisterResponse } from '../api/user'

const router = useRouter()
const registerFormRef = ref()
const loading = ref(false)

const registerForm = reactive({
  username: '',
  email: '',
  password: '',
  confirmPassword: ''
})

const validatePassword = (_rule: any, value: string, callback: any) => {
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } else if (value !== registerForm.password) {
    callback(new Error('两次输入密码不一致'))
  } else {
    callback()
  }
}

const registerRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '用户名长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入有效的邮箱地址', trigger: ['blur', 'change'] }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '密码长度在 6 到 20 个字符', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请再次输入密码', trigger: 'blur' },
    { validator: validatePassword, trigger: 'blur' }
  ]
}

const handleRegister = async () => {
  if (!registerFormRef.value) return
  
  const valid = await registerFormRef.value.validate()
  if (valid) {
    loading.value = true
    try {
      // 移除confirmPassword字段，后端不需要
      const { confirmPassword, ...registerData } = registerForm
      const response = await registerApi(registerData)
      // axios拦截器已经处理了response，直接返回了data
      const result = response as unknown as RegisterResponse
      // 检查后端返回的code字段来判断是否成功
      if (result.code === 200) {
        ElMessage.success('注册成功，请登录')
        router.push('/login')
      } else {
        ElMessage.error(result.message || '注册失败')
      }
    } catch (error: any) {
      ElMessage.error(error.message || '注册失败，请稍后重试')
    } finally {
      loading.value = false
    }
  }
}
</script>

<style scoped>
.register-container {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  overflow: hidden;
  position: relative;
}

/* 背景装饰 */
.register-background {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  overflow: hidden;
}

.background-shapes {
  position: relative;
  width: 100%;
  height: 100%;
}

.shape {
  position: absolute;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.1);
  animation: float 6s ease-in-out infinite;
}

.shape-1 {
  width: 200px;
  height: 200px;
  top: -50px;
  left: -50px;
  animation-delay: 0s;
}

.shape-2 {
  width: 150px;
  height: 150px;
  bottom: -30px;
  right: -30px;
  animation-delay: 2s;
}

.shape-3 {
  width: 100px;
  height: 100px;
  top: 50%;
  left: -50px;
  animation-delay: 4s;
}

.shape-4 {
  width: 80px;
  height: 80px;
  bottom: 50%;
  right: -40px;
  animation-delay: 6s;
}

@keyframes float {
  0%, 100% {
    transform: translateY(0) rotate(0deg);
  }
  50% {
    transform: translateY(-20px) rotate(180deg);
  }
}

/* 注册卡片 */
.register-card {
  width: 100%;
  max-width: 450px;
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.2);
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  border: none;
  transition: all 0.3s ease;
  z-index: 10;
  animation: slideUp 0.5s ease-out;
}

@keyframes slideUp {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* 注册卡片悬停效果 */
.register-card:hover {
  box-shadow: 0 15px 50px rgba(0, 0, 0, 0.3);
  transform: translateY(-5px);
}

/* 注册标题 */
.register-title {
  text-align: center;
  margin-bottom: 30px;
  padding: 30px 0 20px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
}

/* Logo图标 */
.logo-icon {
  margin-bottom: 15px;
}

.big-icon {
  font-size: 48px;
  color: white;
  animation: pulse 2s ease-in-out infinite;
}

@keyframes pulse {
  0%, 100% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.1);
  }
}

.register-title h2 {
  margin: 0 0 10px 0;
  color: white;
  font-size: 28px;
  font-weight: bold;
  letter-spacing: 1px;
}

.register-title p {
  margin: 0;
  color: rgba(255, 255, 255, 0.9);
  font-size: 14px;
  font-weight: 300;
}

/* 注册表单 */
.register-form {
  padding: 0 30px 30px;
}

/* 表单项样式 */
.el-form-item {
  margin-bottom: 20px;
}

.el-form-item__label {
  font-size: 14px;
  font-weight: 500;
  color: #303133;
  margin-bottom: 8px;
}

/* 输入框样式 */
.el-input {
  --el-input-bg-color: #f8f9fa;
  --el-input-border-color: #e9ecef;
  --el-input-text-color: #303133;
}

.el-input__wrapper {
  border-radius: 10px;
  transition: all 0.3s ease;
  border: 2px solid #e9ecef;
}

.el-input__wrapper:focus-within {
  box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.2);
  border-color: #667eea;
}

.el-input__wrapper:hover {
  border-color: #667eea;
}

/* 按钮样式 */
.register-button {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  border-radius: 10px;
  padding: 12px 0;
  font-size: 16px;
  font-weight: 600;
  letter-spacing: 1px;
  transition: all 0.3s ease;
  box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4);
}

.register-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(102, 126, 234, 0.6);
  background: linear-gradient(135deg, #5a6fd8 0%, #6a4190 100%);
}

.register-button:active {
  transform: translateY(0);
}

.register-button:disabled {
  transform: none;
  box-shadow: none;
  background: #ccc;
}

/* 登录链接 */
.login-link {
  text-align: center;
  margin-top: 20px;
  font-size: 14px;
  color: #606266;
}

.login-btn {
  font-weight: 600;
  color: #667eea;
  transition: all 0.3s ease;
}

.login-btn:hover {
  color: #764ba2;
  text-decoration: underline;
}

/* 响应式设计 */
@media (max-width: 576px) {
  .register-card {
    margin: 0 20px;
    max-width: none;
  }
  
  .register-form {
    padding: 0 20px 20px;
  }
  
  .register-title {
    padding: 20px 0 15px;
  }
  
  .register-title h2 {
    font-size: 24px;
  }
  
  .big-icon {
    font-size: 40px;
  }
}
</style>
