import axios from 'axios'

const host = 'http://localhost:8099'

export const requestLogin = params => {
  return axios.put(host + '/user/login', params).then(res => res.data)
}

export const requestRegister = params => {
  return axios.post(host + '/user/register', params).then(res => res.data)
}

export const requestVerifyCode = email => {
  return axios.post(host + '/common/vcode/send?email=' + email).then(res => res.data)
}
