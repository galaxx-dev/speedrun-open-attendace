import axios from 'axios'
import Union from '../../utils/type/Union'

export type BaseFetcher = BaseAPI['_fetch']

class BaseAPI {
  protected _fetch = axios.create({ baseURL: 'https://open-attendance.atma-dev.com/api/' })
}

export default BaseAPI

type APIResponseBase<T> = {
  status_code: number
  message: string
  payload: T | null
}

type APIResponseSuccess<T> = APIResponseBase<T> & { status: 'success' }

type APIResponseError<T> = APIResponseBase<T> & { status: 'error' }

export type APIResponse<T> = Union.StrictUnion<APIResponseSuccess<T> | APIResponseError<T>>

export type APIResponseReturn<T> = Pick<APIResponse<T>, 'message' | 'payload'>
