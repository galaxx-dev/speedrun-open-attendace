interface Base<T> {
  payload: T
  status: 'success' | 'error'
  status_code: number
  message: string
}

export type APIRespOne<T> = Base<T | null>
export type APIRespOneReturn<T> = Pick<APIRespOne<T>, 'message' | 'payload'>

export type APIRespMany<T> = Base<T>
export type APIRespManyReturn<T> = Pick<APIRespMany<T>, 'message' | 'payload'>
