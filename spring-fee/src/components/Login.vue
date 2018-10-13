<!--suppress ALL -->
<template>
  <div>
    <h3>{{loginWelcomeMessage}}</h3><br/>
    <input type="text" id="loginName" class="form-control" :placeholder="loginNameTip" maxlength="50"/>
    <br/>
    <input type="password" id="password" maxlength="50" class="form-control" :placeholder="loginPasswordTip"/>
    <br/>
    <button class="btn btn-primary btn-block">{{loginTip}}</button>
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

export default {
  name: 'Login',
  data () {
    return {
      registerPath: app.data().path.register,
      passwordResetPath: app.data().path.passwordReset,
      loginWelcomeMessage: '欢迎回来',
      loginNameTip: '手机号码',
      loginPasswordTip: '登录密码',
      loginTip: '登录',
      registerPathTip: '还没有账号？',
      passwordResetPathTip: '忘记密码？'
    }
  }
}
</script>

<style scoped>
  @import "../assets/css/style.css";
</style>
