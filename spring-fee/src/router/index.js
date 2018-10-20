import Vue from 'vue'
import Router from 'vue-router'
import Index from '../components/Index'
import SigninContainer from '../components/container/SigninContainer'
import Login from '../components/Login'
import Register from '../components/Register'
import PasswordReset from '../components/PasswordReset'
import Income from '../components/index/Income'

// noinspection JSUnresolvedFunction
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Index',
      component: Index,
      children: [{
        path: '/income',
        name: 'Income',
        component: Income
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
