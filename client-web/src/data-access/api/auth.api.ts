import type { APIRespOne, APIRespOneReturn } from './base'
import BaseAPI from './base'

type Gender = 'male' | 'female'

export interface LoginResp {
  user: {
    id: number
    employee_id: string
    is_active: boolean
    deleted_at: Date | null
    created_at: Date
    updated_at: Date
    employee: {
      id: string
      fullname: string
      email: string
      phone: string
      address: string
      gender: Gender
      deleted_at: Date | null
      created_at: Date
      updated_at: Date
    }
  }
  token: string
  type: 'bearer'
}

export interface LoginBody {
  employee_id: string
  password: string
  remember_me: boolean
}

class AuthApi extends BaseAPI {
  public async login(body: LoginBody): Promise<APIRespOneReturn<LoginResp>> {
    try {
      const { data } = await this.fetch.post<APIRespOne<LoginResp>>('/auth', body)

      if (data.status === 'error') {
        return {
          message: data.message,
          payload: null,
        }
      }

      return {
        message: data.message,
        payload: data.payload,
      }
    } catch (error) {
      console.error(error)
      return {
        message: 'Something bad happens...',
        payload: null,
      }
    }
  }
}

export default new AuthApi()
