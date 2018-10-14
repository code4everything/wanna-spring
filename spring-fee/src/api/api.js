import axios from 'axios'

const host = 'http://localhost:8099'

export const requestLogin = params => {
  return axios.put(host + '/user/login?loginName=' + params.loginName + '&password=' + params.password).then(res => res.data)
}

export const requestRegister = params => {
  return axios.post(host + '/user/register', params).then(res => res.data)
}

export const requestVerifyCode = email => {
  return axios.post(host + '/common/vcode/send?email=' + email).then(res => res.data)
}

export const requestValidateVerifyCode = params => {
  return axios.get(host + '/common/vcode/verify?email=' + params.email + '&vcode=' + params.vcode).then(res => res.data)
}

export const requestResetPassword = params => {
  return axios.put(host + '/user/password/reset', params).then(res => res.data)
}
