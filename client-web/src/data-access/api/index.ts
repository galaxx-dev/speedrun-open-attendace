import authApi from './auth.api'

export * from './auth.api'
export { default as authApi } from './auth.api'

const api = { auth: authApi }
export default api
