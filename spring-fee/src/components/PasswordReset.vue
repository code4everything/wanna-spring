<!--suppress ALL -->
<template>
  <div>
    <h3>{{passwordResetWelcomeMessage}}</h3><br/>
    <input type="email" class="form-control email" id="res-email" maxlength="100" :placeholder="passwordResetNameTip"/>
    <label class="form-check-label text-danger">{{passwordResetNameErrorTip}}</label>
    <br/>
    <div class="form-inline text-justify-all row">
      <div class="col-sm-7 col-md-7 col-6">
        <input type="number" id="res-email-verify" maxlength="6" class="w-100 form-control email-verify-code"
               :placeholder="verifyCodeTip"/>
      </div>
      <div class="col-sm-5 col-md-5 col-6 text-right">
        <button class="btn btn-outline-info sendVerifyCode btn-block">{{verifyCodeSendTip}}</button>
      </div>
    </div>
    <label class="form-check-label text-danger">{{verifyCodeErrorTip}}</label>
    <br/>
    <input type="password" id="res-password" maxlength="50" class="form-control password"
           :placeholder="newPasswordTip">
    <label class="form-check-label text-danger">{{newPasswordErrorTip}}</label>
    <br/>
    <input type="password" id="res-confirm-password" max="50" class="form-control confirm-password"
           :placeholder="newPasswordConfirmTip">
    <label class="form-check-label text-danger">{{newPasswordConfirmErrorTip}}</label>
    <br/>
    <div class="text-center row">
      <div class="col-6 col-sm-6">
        <a class="btn btn-outline-primary btn-block" :href="loginPath">{{loginTip}}</a>
      </div>
      <div class="col-6 col-sm-6">
        <button class="btn btn-danger btn-block" onclick="reset();">{{passwordResetTip}}</button>
      </div>
    </div>
  </div>
</template>

<script>/* eslint-disable */
import app from '../App'

export default {
  name: 'PasswordReset',
  data () {
    return {
      loginPath: app.data().path.login,
      passwordResetWelcomeMessage: '重置密码',
      passwordResetNameTip: '手机号',
      passwordResetNameErrorTip: '',
      verifyCodeTip: '收到的验证码',
      verifyCodeSendTip: '发送验证码',
      verifyCodeErrorTip: '',
      newPasswordTip: '请输入你的新密码',
      newPasswordErrorTip: '',
      newPasswordConfirmTip: '请再次输入你的新密码',
      newPasswordConfirmErrorTip: '',
      loginTip: '继续登录',
      passwordResetTip: '重置密码'

    }
  }
}
</script>

<style scoped>
  @import "../assets/css/style.css";
</style>
