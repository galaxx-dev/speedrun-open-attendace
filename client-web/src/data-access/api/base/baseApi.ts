import fetch from './fetch'
import fetchWithCred from './fetchWithCred'

export type BaseFetcher = BaseAPI['fetch']

/**
 * @version v1.1.0
 */
class BaseAPI {
  protected fetch = fetch

  protected fetchWithCred = fetchWithCred
}

export default BaseAPI
