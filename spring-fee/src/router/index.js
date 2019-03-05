import Vue from 'vue'
import Router from 'vue-router'
import Index from '../components/Index'
import SigninContainer from '../components/container/SigninContainer'
import Login from '../components/Login'
import Register from '../components/Register'
import PasswordReset from '../components/PasswordReset'
import Income from '../components/index/Income'
import ScheduleContainer from '../components/container/ScheduleContainer'
import Daily from '../components/index/Daily'
import Todo from '../components/index/Todo'
import Job from '../components/index/Job'

// noinspection JSUnresolvedFunction
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      component: Index,
      children: [{
        path: '/income',
        name: 'Income',
        component: Income
      }, {
        path: '',
        name: 'ScheduleContainer',
        component: ScheduleContainer,
        children: [{
          path: '/daily',
          name: 'Daily',
          component: Daily
        }, {
          path: '/todo',
          name: 'Todo',
          component: Todo
        }]
      }, {
        path: '/job',
        name: 'Job',
        component: Job
      }]
    }, {
      path: '',
      name: 'SigninContainer',
      component: SigninContainer,
      children: [{
        path: '/login',
        name: 'Login',
        component: Login
      }, {
        path: '/register',
        name: 'Register',
        component: Register
      }, {
        path: '/password/reset',
        name: 'PasswordReset',
        component: PasswordReset
      }]
    }
  ]
})
