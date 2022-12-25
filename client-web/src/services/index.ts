import authService from './auth.service'

export * from './auth.service'
export { default as authService } from './auth.service'

const service = {
  auth: authService,
}
export default service
