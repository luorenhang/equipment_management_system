import { defineStore } from 'pinia'
import { UserInfo } from '../api/user'
import { getUserInfo as fetchUserInfo } from '../api/user'

// 定义用户状态接口
interface UserState {
  token: string | null
  userInfo: UserInfo | null
  isLoggedIn: boolean
}

/**
 * 用户状态管理
 */
export const useUserStore = defineStore('user', {
  // 状态定义
  state: (): UserState => ({
    token: localStorage.getItem('token'),
    userInfo: JSON.parse(localStorage.getItem('userInfo') || 'null'),
    isLoggedIn: !!localStorage.getItem('token')
  }),

  // Getters
  getters: {
    // 获取用户名
    getUsername: (state) => state.userInfo?.username || '',
    // 获取用户角色
    getUserRole: (state) => state.userInfo?.role || '',
    // 获取用户邮箱
    getUserEmail: (state) => state.userInfo?.email || ''
  },

  // Actions
  actions: {
    /**
     * 登录成功，保存token和用户信息
     * @param token 登录令牌
     * @param userInfo 用户信息
     */
    login(token: string, userInfo: UserInfo) {
      this.token = token
      this.userInfo = userInfo
      this.isLoggedIn = true
      
      // 保存到localStorage
      localStorage.setItem('token', token)
      localStorage.setItem('userInfo', JSON.stringify(userInfo))
    },

    /**
     * 登出，清除token和用户信息
     */
    logout() {
      this.token = null
      this.userInfo = null
      this.isLoggedIn = false
      
      // 清除localStorage
      localStorage.removeItem('token')
      localStorage.removeItem('userInfo')
    },

    /**
     * 刷新用户信息
     */
    async refreshUserInfo() {
      if (!this.token) return false
      
      try {
        const userInfo = await fetchUserInfo()
        // axios拦截器已经处理了response，直接返回了data
        this.userInfo = userInfo as unknown as UserInfo
        localStorage.setItem('userInfo', JSON.stringify(userInfo))
        return true
      } catch (error) {
        console.error('刷新用户信息失败:', error)
        this.logout()
        return false
      }
    }
  }
})
