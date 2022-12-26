import axios from 'axios'
import CONSTS from '~config/consts'

/**
 * @version v1.1.0
 */
const fetch = axios.create({ baseURL: CONSTS.URL.BASE_API })

export default fetch
