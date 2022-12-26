import axios from 'axios'
import { BASE_URL, BASE_URL_ORIGIN } from './baseApi'

/**
 * @version v1.1.0
 */
const fetchWithCred = axios.create({
  baseURL: BASE_URL,
  headers: { 'Content-Type': 'application/json' },
  withCredentials: true,
})

fetchWithCred.interceptors.request.use(
  config => {
    const allowedOrigins = [BASE_URL_ORIGIN as string]
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
