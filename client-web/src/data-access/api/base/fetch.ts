import axios from 'axios'
import { BASE_URL } from './baseApi'

/**
 * @version v1.1.0
 */
const fetch = axios.create({ baseURL: BASE_URL })

export default fetch
