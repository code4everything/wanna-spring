<!--suppress ALL -->
<template>
  <div>
    <h3>{{loginWelcomeMessage}}</h3><br/>
    <el-form>
      <el-form-item>
        <el-input type="text" v-model="loginName" :placeholder="loginNameTip" maxlength="50"/>
      </el-form-item>
      <el-form-item>
        <el-input type="password" v-model="password" maxlength="50" :placeholder="loginPasswordTip"/>
      </el-form-item>
      <el-form-item>
        <button class="btn btn-primary btn-block" @click="login">{{loginTip}}</button>
      </el-form-item>
      <div class="form-inline row">
        <div class="col-sm-6 col-6 text-left">
          <a class="btn btn-link text-success" :href="registerPath">{{registerPathTip}}</a>
        </div>
        <div class="col-sm-6 col-6 text-right">
          <a class="btn btn-link text-danger" :href="passwordResetPath">{{passwordResetPathTip}}</a>
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
        layer.load(1)
        requestLogin({loginName: this.loginName, password: this.password}).then(data => {
          layer.closeAll()
          if (data.code === 200) {
            cookie.set('token', data.data)
            window.location = app.data().path.index
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
