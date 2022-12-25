import { authApi } from '~data-access/api'
import dataMapper from '~data-mapper/index'
import { FormProps } from '~pages/auth/login'

class AuthService {
  dataMapper = {
    auth: dataMapper.auth,
  }

  api = {
    auth: authApi,
  }

  async login(props: FormProps) {
    const body = this.dataMapper.auth.loginPropsToBody(props)

    return await this.api.auth.login(body)
  }
}

export default new AuthService()
