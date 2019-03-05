import axios from 'axios'
import cookie from 'js-cookie'
import {Loading} from 'element-ui'

const host = 'http://localhost:8099'

let loading

axios.defaults.timeout = 10000

axios.interceptors.request.use(config => {
  loading = Loading.service({})
  // 加时间戳，防止缓存
  config.url = host + config.url + (config.url.includes('?') ? '&' : '?') + 'timestamp=' + new Date().getTime()
  config.headers = {
    'Content-Type': 'application/json',
    token: cookie.get('token')
  }
  return config
}, error => {
  return Promise.resolve(error)
})

axios.interceptors.response.use(response => {
  loading.close()
  return response.data
}, error => {
  loading.close()
  if (error.response !== undefined) {
    console.error(error.response.data)
  }
  return Promise.resolve(error.response)
})

export const requestCompanies = () => {
  return axios.get('/user/job/companies')
}

export const requestFinishWork = (jobId, workWay, company) => {
  return axios.put(`/user/job/${jobId}/finish?workWay=${workWay}&company=${company}`)
}

export const requestStartWorking = (workWay, company) => {
  return axios.post(`/user/job/start?workWay=${workWay}&company=${company}`)
}

export const requestJobOfToday = () => {
  return axios.get('/user/job/today')
}

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

export const requestSaveTodo = (doingDate, content, offset, repeat) => {
  return axios.post(`/user/todo/create`, {doingDate, content, offset, repeat})
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
  return axios.put(`/user/asset/income/${id}/update`, params)
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

export const requestLogin = (loginName, password) => {
  return axios.put(`/user/login?loginName=${loginName}&password=${password}`)
}

export const requestRegister = params => {
  return axios.post('/user/register', params)
}

export const requestVerifyCode = email => {
  return axios.post('/common/vcode/send?email=' + email)
}

export const requestValidateVerifyCode = (email, vcode) => {
  return axios.get(`/common/vcode/verify?email=${email}&vcode=${vcode}`)
}

export const requestResetPassword = params => {
  return axios.put('/user/password/reset', params)
}
