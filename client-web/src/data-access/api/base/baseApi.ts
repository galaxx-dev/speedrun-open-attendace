import fetch from './fetch'
import fetchWithCred from './fetchWithCred'

export type BaseFetcher = BaseAPI['fetch']

export const BASE_URL_ORIGIN = 'https://open-attendance.atma-dev.com' as const
export const BASE_URL = `${BASE_URL_ORIGIN}/api/` as const

/**
 * @version v1.1.0
 */
class BaseAPI {
  protected fetch = fetch

  protected fetchWithCred = fetchWithCred
}

export default BaseAPI
