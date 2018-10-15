<!--suppress ALL -->
<template>
  <div>
    <h3>{{loginWelcomeMessage}}</h3><br/>
    <input type="text" id="login-name" class="form-control" :placeholder="loginNameTip" maxlength="50"/>
    <br/>
    <input type="password" id="password" maxlength="50" class="form-control" :placeholder="loginPasswordTip"/>
    <br/>
    <button class="btn btn-primary btn-block" @click="login">{{loginTip}}</button>
    <br/>
    <div class="form-inline row">
      <div class="col-sm-6 col-6 text-left">
        <a class="btn btn-link text-success" :href="registerPath">{{registerPathTip}}</a>
      </div>
      <div class="col-sm-6 col-6 text-right">
        <a class="btn btn-link text-danger" :href="passwordResetPath">{{passwordResetPathTip}}</a>
      </div>
    </div>
  </div>
</template>

<script>/* eslint-disable */
import app from '../App'
import validator from '../../static/js/validator.min'
import {requestLogin} from "../api/api";
import utils from '../assets/js/utils'

export default {
  name: 'Login',
  data () {
    return {
      registerPath: app.data().path.register,
      passwordResetPath: app.data().path.passwordReset,
      loginWelcomeMessage: '欢迎回来',
      loginNameTip: '用户名或邮箱',
      loginPasswordTip: '登录密码',
      loginTip: '登录',
      registerPathTip: '还没有账号？',
      passwordResetPathTip: '忘记密码？'
    }
  },
  methods: {
    login: function () {
      let loginName = $('#login-name').val()
      let password = $('#password').val()
      if (validator.isEmpty(password)) {
        layer.alert('密码不能为空')
      } else {
        layer.load(1)
        requestLogin({loginName: loginName, password: password}).then(data => {
          layer.closeAll()
          console.info(data)
          if (data.code === 200) {
            utils.setCookie('token', data.data)
            window.location = app.data().path.index
          } else {
            layer.alert(data.message)
          }
        })
      }
    }
  }
}
</script>

<style scoped>
  @import "../assets/css/style.css";
</style>
