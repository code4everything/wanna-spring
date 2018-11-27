<!--suppress ALL -->
<template>
  <div>
    <h3>{{loginWelcomeMessage}}</h3><br/>
    <el-form>
      <el-form-item>
        <el-input :placeholder="loginNameTip" maxlength="50" type="text" v-model="loginName"/>
      </el-form-item>
      <el-form-item>
        <el-input :placeholder="loginPasswordTip" maxlength="50" type="password" v-model="password"/>
      </el-form-item>
      <el-form-item>
        <el-button @click="login" class="btn-block" type="primary">{{loginTip}}</el-button>
      </el-form-item>
      <div class="form-inline row">
        <div class="col-sm-6 col-6 text-left">
          <a :href="registerPath" class="btn btn-link text-success">{{registerPathTip}}</a>
        </div>
        <div class="col-sm-6 col-6 text-right">
          <a :href="passwordResetPath" class="btn btn-link text-danger">{{passwordResetPathTip}}</a>
        </div>
      </div>
    </el-form>
  </div>
</template>

<script>/* eslint-disable */
import app from '../App'
import validator from '../../static/js/validator.min'
import {requestLogin} from '../api/api'
import cookie from 'js-cookie'

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
      passwordResetPathTip: '忘记密码？',
      loginName: '',
      password: ''
    }
  },
  methods: {
    login: function () {
      if (validator.isEmpty(this.password)) {
        layer.alert('密码不能为空')
      } else {
        requestLogin(this.loginName, this.password).then(data => {
          if (data.code === 200) {
            cookie.set('token', data.data)
            window.location.href = app.data().path.income
          } else {
            layer.alert(data.msg)
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
