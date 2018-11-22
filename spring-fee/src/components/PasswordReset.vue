<!--suppress ALL -->
<template>
  <div>
    <h3>{{passwordResetWelcomeMessage}}</h3><br/>
    <input type="text" class="form-control email" id="register-name" maxlength="100"
           :placeholder="passwordResetNameTip" @keyup="checkRegisterName"/>
    <label class="form-check-label text-danger">{{passwordResetNameErrorTip}}</label>
    <br/>
    <div class="form-inline text-justify-all row">
      <div class="col-sm-7 col-md-7 col-6">
        <input type="text" id="verify-code" maxlength="6" class="w-100 form-control email-verify-code"
               :placeholder="verifyCodeTip" @keyup="validateVerifyCode"/>
      </div>
      <div class="col-sm-5 col-md-5 col-6 text-right">
        <button class="btn btn-outline-info sendVerifyCode btn-block" @click="sendVerifyCode">{{verifyCodeSendTip}}
        </button>
      </div>
    </div>
    <label class="form-check-label text-danger">{{verifyCodeErrorTip}}</label>
    <br/>
    <input type="password" id="new-password" maxlength="50" class="form-control password"
           :placeholder="newPasswordTip">
    <label class="form-check-label text-danger">{{newPasswordErrorTip}}</label>
    <br/>
    <input type="password" id="confirm-password" max="50" class="form-control confirm-password"
           :placeholder="newPasswordConfirmTip" @keyup="checkPasswordConsistency">
    <label class="form-check-label text-danger">{{newPasswordConfirmErrorTip}}</label>
    <br/>
    <div class="text-center row">
      <div class="col-6 col-sm-6">
        <a class="btn btn-outline-primary btn-block" :href="loginPath">{{loginTip}}</a>
      </div>
      <div class="col-6 col-sm-6">
        <button class="btn btn-danger btn-block" @click="resetPassword">{{passwordResetTip}}</button>
      </div>
    </div>
  </div>
</template>

<script>/* eslint-disable */
import app from '../App'
import validator from '../../static/js/validator.min'
import {requestResetPassword, requestValidateVerifyCode, requestVerifyCode} from '../api/api'

export default {
  name: 'PasswordReset',
  data () {
    return {
      loginPath: app.data().path.login,
      passwordResetWelcomeMessage: '重置密码',
      passwordResetNameTip: '邮箱',
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
  },
  methods: {
    checkRegisterName: function () {
      this.passwordResetNameErrorTip = validator.isEmail($('#register-name').val()) ? '' : '邮箱格式不正确'
    },
    checkPasswordConsistency: function () {
      this.newPasswordConfirmErrorTip = $('#new-password').val() === $('#confirm-password').val() ? '' : '两次输入的密码不一样'
    },
    sendVerifyCode: function () {
      let email = $('#register-name').val()
      if (validator.isEmail(email)) {
        layer.load(1)
        requestVerifyCode(email).then(data => {
          layer.closeAll()
          layer.alert(data.msg)
        })
      }
    },
    validateVerifyCode: function () {
      let vcode = $('#verify-code').val()
      let email = $('#register-name').val()
      if (vcode.length === 6 && validator.isEmail(email)) {
        requestValidateVerifyCode(email, vcode).then(data => {
          this.verifyCodeErrorTip = data.code === 200 ? '' : '验证码错误'
        })
      }
    },
    resetPassword: function () {
      let vcode = $('#verify-code').val()
      let email = $('#register-name').val()
      let newPassword = $('#confirm-password').val()
      if (validator.isEmpty(email) || validator.isEmpty(vcode) || validator.isEmpty(newPassword)) {
        layer.alert('数据不能为空')
      } else if (validator.isEmpty(this.passwordResetNameErrorTip) && validator.isEmpty(this.newPasswordConfirmErrorTip) && validator.isEmpty(this.verifyCodeErrorTip)) {
        layer.load(1)
        requestResetPassword({email: email, newPassword: newPassword, vcode: vcode}).then(data => {
          layer.closeAll()
          if (data.code === 200) {
            window.location = this.loginPath
          } else {
            layer.alert(data.msg)
          }
        })
      } else {
        layer.alert('格式不正确')
      }
    }
  }
}
</script>

<style scoped>
  @import "../assets/css/style.css";
</style>
