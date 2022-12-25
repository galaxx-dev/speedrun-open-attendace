import authDataMapper from './auth.dataMapper'

export * from './auth.dataMapper'
export { default as authDataMapper } from './auth.dataMapper'

const dataMapper = {
  auth: authDataMapper,
}
export default dataMapper
