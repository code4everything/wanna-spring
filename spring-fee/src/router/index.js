import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '../components/HelloWorld'
import SigninContainer from '../components/container/SigninContainer'
import Login from '../components/Login'
import Register from '../components/Register'
import PasswordReset from '../components/PasswordReset'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld
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
