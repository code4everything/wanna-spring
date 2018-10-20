import axios from 'axios'
import layer from '../../static/js/layer'
import cookie from 'js-cookie'

const host = 'http://localhost:8099'

let token = cookie.get('token')

axios.defaults.timeout = 10000

axios.interceptors.request.use(config => {
  config.url = host + config.url
  config.headers = {
    'Content-Type': 'application/json',
    token: token
  }
  return config
}, error => {
  alert('连接超时')
  return Promise.resolve(error)
})

axios.interceptors.response.use(response => {
  const data = response.data
  console.info(data)
  return data
}, error => {
  layer.closeAll()
  if (error.response !== undefined) {
    alert(error.response.data.message)
    console.error(error.response.data)
  }
  return Promise.resolve(error.response)
})

export const requestLogin = params => {
  return axios.put('/user/login?loginName=' + params.loginName + '&password=' + params.password).then(res => res.data)
}

export const requestRegister = params => {
  return axios.post('/user/register', params).then(res => res.data)
}

export const requestVerifyCode = email => {
  return axios.post('/common/vcode/send?email=' + email).then(res => res.data)
}

export const requestValidateVerifyCode = params => {
  return axios.get('/common/vcode/verify?email=' + params.email + '&vcode=' + params.vcode).then(res => res.data)
}

export const requestResetPassword = params => {
  return axios.put('/user/password/reset', params).then(res => res.data)
}
