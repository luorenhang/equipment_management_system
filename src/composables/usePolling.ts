import { ref, onMounted, onUnmounted } from 'vue'

/**
 * 定时轮询钩子函数
 * @param fetchData 数据获取函数
 * @param interval 轮询间隔，默认30秒
 * @param immediate 是否立即执行，默认true
 * @returns {Object} { startPolling, stopPolling, isPolling }
 */
export const usePolling = (
  fetchData: () => Promise<any>,
  interval: number = 30000,
  immediate: boolean = true
) => {
  const isPolling = ref(false)
  let timer: number | null = null

  // 开始轮询
  const startPolling = () => {
    if (isPolling.value) return
    
    isPolling.value = true
    
    // 立即执行一次
    if (immediate) {
      fetchData()
    }
    
    // 设置定时器
    timer = setInterval(() => {
      fetchData()
    }, interval) as unknown as number
  }

  // 停止轮询
  const stopPolling = () => {
    if (!isPolling.value) return
    
    isPolling.value = false
    
    if (timer !== null) {
      clearInterval(timer)
      timer = null
    }
  }

  // 组件挂载时开始轮询
  onMounted(() => {
    startPolling()
  })

  // 组件卸载时停止轮询
  onUnmounted(() => {
    stopPolling()
  })

  return {
    isPolling,
    startPolling,
    stopPolling
  }
}
