import axios from 'axios'

const axiosInstance = axios.create({
  baseURL: '/api',
  timeout: 10000,
  withCredentials: true
})

// 请求拦截器
axiosInstance.interceptors.request.use(
  config => {
    // 添加Authorization头
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    
    console.log('API请求详情：')
    console.log('请求地址:', config.url)
    console.log('请求方法:', config.method)
    console.log('请求参数:', config.params || config.data)
    console.log('请求头:', config.headers)
    return config
  },
  error => {
    console.error('API请求配置错误:', error)
    return Promise.reject(error)
  }
)

// 响应拦截器
axiosInstance.interceptors.response.use(
  response => {
    console.log('API响应详情：')
    console.log('请求地址:', response.config?.url)
    console.log('响应状态:', response.status)
    console.log('响应头:', response.headers)
    console.log('响应数据:', response.data)
    
    const { data } = response
    // 检查返回数据格式，确保返回的是Result对象
    if (typeof data === 'object' && data !== null && 'code' in data && 'data' in data) {
      // 检查返回码
      if (data.code === 200) {
        return data.data
      } else {
        // 业务逻辑错误，抛出异常
        const errorMessage = data.message || '请求失败'
        
        const error = new Error(errorMessage)
        // @ts-ignore
        error.response = response
        return Promise.reject(error)
      }
    }
    return data
  },
  error => {
    let errorMessage = '请求失败，请稍后重试'
    
    if (error.response) {
      // HTTP错误
      const { status, data } = error.response
      console.error('API请求失败详情：')
      console.error('请求地址:', error.config?.url)
      console.error('请求参数:', error.config?.params || error.config?.data)
      console.error('响应状态:', status)
      console.error('响应数据:', data)
      
      // 处理认证错误
      if (status === 401 || status === 403) {
        // 清除本地存储的token和用户信息
        localStorage.removeItem('token')
        localStorage.removeItem('userInfo')
        
        // 跳转到登录页面（延迟执行，避免阻塞当前错误处理）
        setTimeout(() => {
          window.location.href = '/login'
        }, 100)
        
        errorMessage = '登录已过期，请重新登录'
      } else {
        // 尝试从响应数据中获取错误信息
        if (typeof data === 'object' && data !== null) {
          if ('message' in data) {
            errorMessage = data.message
          } else if ('msg' in data) {
            errorMessage = data.msg
          } else if ('error' in data) {
            errorMessage = data.error
          } else {
            errorMessage = `请求失败，HTTP状态码：${status}`
          }
        } else {
          errorMessage = `请求失败，HTTP状态码：${status}`
        }
      }
    } else if (error.request) {
      // 请求已发出，但没有收到响应
      errorMessage = '网络异常，未收到响应'
      console.error('API请求失败详情：')
      console.error('请求地址:', error.config?.url)
      console.error('请求参数:', error.config?.params || error.config?.data)
      console.error('请检查后端服务是否正在运行:', error.config?.baseURL)
    } else {
      // 请求配置出错
      errorMessage = error.message || '请求配置错误'
      console.error('API请求失败详情：')
      console.error('错误信息:', error.message)
    }
    
    // 给error对象添加message属性，方便后续处理
    error.message = errorMessage
    
    return Promise.reject(error)
  }
)

export default axiosInstance