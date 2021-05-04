import axios from 'axios'
import authHeader from './auth-header'

const API_URL = 'http://localhost:8080/api/auth'

class UserService {
  getUserContent () {
    return axios.get(API_URL + '/user', {headers: authHeader()})
  }
  getUser () {
    return axios.get(API_URL + '/finduser', {headers: authHeader()})
  }
}

export default new UserService()
