import axios from 'axios'
import layer from '../../static/js/layer'
import cookie from 'js-cookie'

const host = 'http://localhost:8099'

axios.defaults.timeout = 10000

axios.interceptors.request.use(config => {
  config.url = host + config.url + (config.url.includes('?') ? '&' : '?') + 'timestamp=' + new Date().getTime()
  console.info(`request url -> ${config.url}`)
  config.headers = {
    'Content-Type': 'application/json',
    token: cookie.get('token')
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
    // alert(error.response.data.msg)
    console.error(error.response.data)
  }
  return Promise.resolve(error.response.data || error.response)
})

export const requestListTodoCount = (start, end) => {
  return axios.get(`/user/todo/count/list?start=${start}&end=${end}`)
}

export const requestListDaily = (start, end) => {
  return axios.get(`/user/daily/list?start=${start}&end=${end}`)
}

export const requestListIncomeYear = (startYear, endYear) => {
  return axios.get(`/user/asset/income/year/list?startYear=${startYear}&endYear=${endYear}`)
}

export const requestListIncomeMonth = (startMonth, endMonth) => {
  return axios.get(`/user/asset/income/month/list?startMonth=${startMonth}&endMonth=${endMonth}`)
}

export const requestListUndo = date => {
  return axios.get(`/user/todo/undo/list?date=${date}`)
}

export const requestRemoveTodo = id => {
  return axios.delete(`/user/todo/remove?todoId=${id}`)
}

export const requestUpdateTodo = (id, content) => {
  return axios.put(`/user/todo/update?todoId=${id}&content=${content}`)
}

export const requestToggleTodoStatus = (id, status) => {
  return axios.put(`/user/todo/status/toggle?todoId=${id}&status=${status}`)
}

export const requestListTodo = date => {
  return axios.get(`/user/todo/list?date=${date}`)
}

export const requestSaveTodo = (doingDate, content) => {
  return axios.post(`/user/todo/create?doingDate=${doingDate}&content=${content}`)
}

export const requestRemoveDailies = dailiesId => {
  return axios.delete(`/user/daily/detail/remove?dailiesId=${dailiesId}`)
}

export const requestListDailies = dailyId => {
  return axios.get(`/user/daily/detail/list?dailyId=${dailyId}`)
}

export const requestUpdateDailies = (dailiesId, params) => {
  return axios.put(`/user/daily/detail/${dailiesId}/update`, params)
}

export const requestSaveDailies = (dailyId, params) => {
  return axios.post(`/user/daily/detail/append/${dailyId}`, params)
}

export const requestGetDaily = date => {
  return axios.get(`/user/daily/get?date=${date}`)
}

export const requestUpdateDaily = (params) => {
  return axios.put(`/user/daily/${params.id}/update`, params)
}

export const requestSaveDaily = params => {
  return axios.post('/user/daily/create', params)
}

export const requestRemoveIncome = id => {
  return axios.delete(`/user/asset/income/remove?incomeId=${id}`)
}

export const requestAssetBalance = () => {
  return axios.get('/user/asset/balance')
}

export const requestListIncome = (category, start, end) => {
  return axios.get(`/user/asset/income/list?category=${category}&start=${start}&end=${end}`)
}

export const requestUpdateIncome = (id, params) => {
  return axios.put('/user/asset/income/' + id + '/update', params)
}

export const requestSaveIncome = params => {
  return axios.post('/user/asset/income/append', params)
}

export const requestListCategory = () => {
  return axios.get('/user/category/list')
}

export const requestSaveCategory = name => {
  return axios.post('/user/category/append?name=' + name)
}

export const requestLogin = params => {
  return axios.put('/user/login?loginName=' + params.loginName + '&password=' + params.password)
}

export const requestRegister = params => {
  return axios.post('/user/register', params)
}

export const requestVerifyCode = email => {
  return axios.post('/common/vcode/send?email=' + email)
}

export const requestValidateVerifyCode = params => {
  return axios.get('/common/vcode/verify?email=' + params.email + '&vcode=' + params.vcode)
}

export const requestResetPassword = params => {
  return axios.put('/user/password/reset', params)
}
