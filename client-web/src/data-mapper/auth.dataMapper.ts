import { LoginBody } from '~data-access/api'
import { FormProps } from '~pages/auth/login'

const authDataMapper = {
  loginPropsToBody(props: FormProps): LoginBody {
    return {
      employee_id: props.NIK,
      password: props.password,
      remember_me: props.rememberMe,
    }
  },
}

export default authDataMapper
