import axios from '../utils/axios'

// 登录请求接口
export interface LoginRequest {
  username: string
  password: string
}

// 注册请求接口
export interface RegisterRequest {
  username: string
  password: string
  email: string
  confirmPassword?: string
}

// 用户信息接口
export interface UserInfo {
  id: number
  username: string
  email: string
  role: string
}

// 登录响应接口
export interface LoginResponse {
  code: number
  message: string
  data: {
    token: string
    userInfo: UserInfo
  }
}

// 注册响应接口
export interface RegisterResponse {
  code: number
  message: string
}

/**
 * 用户登录
 * @param data 登录信息
 * @returns 登录结果
 */
export const login = async (data: LoginRequest) => {
  return axios.post<LoginResponse>('/user/login', data)
}

/**
 * 用户注册
 * @param data 注册信息
 * @returns 注册结果
 */
export const register = async (data: RegisterRequest) => {
  return axios.post<RegisterResponse>('/user/register', data)
}

/**
 * 获取当前用户信息
 * @returns 用户信息
 */
export const getUserInfo = async () => {
  return axios.get<UserInfo>('/user/info')
}

/**
 * 用户登出
 * @returns 登出结果
 */
export const logout = async () => {
  return axios.post('/user/logout')
}
