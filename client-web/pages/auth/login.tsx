import LoadingButton from '@atlaskit/button/loading-button'
import { Checkbox } from '@atlaskit/checkbox'
import Form, {
  CheckboxField,
  ErrorMessage,
  Field,
  FormFooter,
  FormHeader,
  FormSection,
  ValidMessage,
} from '@atlaskit/form'
import TextField from '@atlaskit/textfield'
import { useAtom } from 'jotai'
import { atomWithStorage } from 'jotai/utils'
import { useRouter } from 'next/router'
import { LoginResp } from '~data-access/api'
import service from '~services/index'

export interface FormProps {
  NIK: string
  password: string
  rememberMe: boolean
}

interface Props {}

const loggedInUserAtom = atomWithStorage<LoginResp['user'] | null>('user', null)
const loggedInAccessTokenAtom = atomWithStorage<string | null>('accessToken', '')

export default function PageAuthLogin(_: Props) {
  const [user, userSet] = useAtom(loggedInUserAtom)
  const [accessToken, accessTokenSet] = useAtom(loggedInAccessTokenAtom)

  const r = useRouter()
  if (user && accessToken) r.push('/dashboard')

  const handleOnSubmit = async (data: FormProps) => {
    console.log('form data', data)

    const { payload } = await service.auth.login(data)

    if (!payload) return

    userSet(payload.user)
    accessTokenSet(`${payload.type} ${payload.token}`)

    r.push('/dashboard')
  }

  return (
    <div className="bg-gray-800 justify-center flex items-center min-h-screen">
      <div className="bg-white rounded-lg px-8 py-10 shadow">
        <div className="flex w-[400px] max-w-full my-0 mx-auto flex-col">
          <Form<FormProps> onSubmit={handleOnSubmit}>
            {({ formProps, submitting }) => (
              <form {...formProps}>
                <FormHeader title="Login" description="Open Attendance" />

                <FormSection>
                  <Field
                    aria-required={true}
                    name="NIK"
                    label="NIK"
                    isRequired
                    defaultValue="10016237"
                  >
                    {({ fieldProps, error }) => (
                      <>
                        <TextField autoComplete="off" {...fieldProps} />
                        {error && (
                          <ErrorMessage>
                            NIK or password is/are wrong, try another one.
                          </ErrorMessage>
                        )}
                      </>
                    )}
                  </Field>

                  <Field
                    aria-required={true}
                    name="password"
                    label="Password"
                    isRequired
                    defaultValue="admin"
                    validate={value => (value && value.length < 5 ? 'TOO_SHORT' : undefined)}
                  >
                    {({ fieldProps, error, valid, meta }) => {
                      return (
                        <>
                          <TextField type="password" {...fieldProps} />
                          {error && (
                            <ErrorMessage>
                              Password needs to be more than 5 characters.
                            </ErrorMessage>
                          )}
                          {valid && meta.dirty && <ValidMessage>Awesome password!</ValidMessage>}
                        </>
                      )
                    }}
                  </Field>

                  <CheckboxField name="rememberMe" label="Remember me" defaultIsChecked>
                    {({ fieldProps }) => (
                      <Checkbox {...fieldProps} label="Always sign in on this device" />
                    )}
                  </CheckboxField>
                </FormSection>

                <FormFooter>
                  <LoadingButton
                    className="mt-4"
                    shouldFitContainer
                    type="submit"
                    appearance="primary"
                    isLoading={submitting}
                  >
                    Login
                  </LoadingButton>
                </FormFooter>
              </form>
            )}
          </Form>
        </div>
      </div>
    </div>
  )
}
