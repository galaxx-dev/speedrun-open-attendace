import axios from 'axios'
import CONSTS from '~config/consts'

/**
 * @version v1.1.0
 */
const fetchWithCred = axios.create({
  baseURL: CONSTS.URL.BASE_API,
  headers: { 'Content-Type': 'application/json' },
  withCredentials: true,
})

fetchWithCred.interceptors.request.use(
  config => {
    const allowedOrigins = [CONSTS.URL.BASE_API_ORIGIN as string]
    const { origin } = new URL(config.url || '')

    if (config.headers && allowedOrigins.includes(origin)) {
      const accessToken = localStorage.getItem('accessToken')
      config.headers.authorization = `Bearer ${accessToken}`
    }

    return config
  },
  error => Promise.reject(error),
)

export default fetchWithCred
