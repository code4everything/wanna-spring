<!--suppress ALL -->
<template>
  <div>
    <h3>{{registerWelcomeMessage}}</h3><br/>
    <input :placeholder="usernameTip" class="form-control" id="username" maxlength="50" type="text"/>
    <label class="form-check-label text-danger">{{usernameErrorTip}}</label>
    <br/>
    <input :class="['form-control','register-name']" :placeholder="registerNameTip" @keyup="checkRegisterName"
           id="register-name"
           maxlength="100" type="text">
    <label class="form-check-label text-danger">{{registerNameErrorTip}}</label>
    <br/>
    <div class="form-inline text-justify-all row">
      <div class="col-sm-7 col-6">
        <input :placeholder="verifyCodeTip" @keyup="validateVerifyCode" class="w-100 form-control verify-code"
               id="verify-code" maxlength="6" type="text"/>
      </div>
      <div class="col-sm-5 col-6 text-right">
        <button @click="sendVerifyCode" class="btn btn-outline-info sendVerifyCode btn-block">{{verifyCodeSendTip}}
        </button>
      </div>
    </div>
    <label class="form-check-label text-danger">{{verifyCodeErrorTip}}</label>
    <br/>
    <input :placeholder="passwordTip" class="form-control password" id="register-password" maxlength="50"
           type="password">
    <label class="form-check-label text-danger">{{passwordErrorTip}}</label>
    <br/>
    <input :placeholder="passwordConfirmTip" @keyup="checkPasswordConsistency" class="form-control confirm-password"
           id="confirm-password"
           max="50" type="password">
    <label class="form-check-label text-danger">{{passwordConfirmErrorTip}}</label>
    <br/>
    <div class="text-center row">
      <div class="col-6 col-sm-6">
        <a :href="loginPath" class="btn btn-outline-warning btn-block">{{loginPathTip}}</a>
      </div>
      <div class="col-6 col-sm-6">
        <button @click="register" class="btn btn-primary btn-block">{{registerTip}}</button>
      </div>
    </div>
  </div>
</template>

<script>/* eslint-disable indent */
import app from '../App'
import validator from '../../static/js/validator.min'
import $ from '../../static/js/jquery-3.3.1'
import {requestRegister, requestValidateVerifyCode, requestVerifyCode} from '../api/api'
import utils from '../assets/js/utils'

export default {
  name: 'Register',
  // eslint-disable-next-line space-before-function-paren
  data () {
    return {
      loginPath: app.data().path.login,
      registerTip: '注册',
      loginPathTip: '已有账号？',
      registerWelcomeMessage: '欢迎注册',
      usernameErrorTip: '',
      usernameTip: '用户名',
      registerNameTip: '邮箱',
      registerNameErrorTip: '',
      verifyCodeTip: '收到的验证码',
      verifyCodeSendTip: '发送验证码',
      verifyCodeErrorTip: '',
      passwordTip: '请输入你的密码',
      passwordErrorTip: '',
      passwordConfirmTip: '请再次输入你的密码',
      passwordConfirmErrorTip: ''
    }
  },
  methods: {
    checkRegisterName: function () {
      this.registerNameErrorTip = validator.isEmail($('#register-name').val()) ? '' : '邮箱格式不正确'
    },
    checkPasswordConsistency: function () {
      this.passwordConfirmErrorTip = $('#register-password').val() === $('#confirm-password').val() ? '' : '两次输入的密码不一样'
    },
    sendVerifyCode: function () {
      var email = $('#register-name').val()
      if (validator.isEmail(email)) {
        requestVerifyCode(email).then(data => {
          utils.showSuccess(this, data.msg)
        })
      }
    },
    validateVerifyCode: function () {
      let vcode = $('#verify-code').val()
      let email = $('#register-name').val()
      if (vcode.length === 6 && validator.isEmail(email)) {
        requestValidateVerifyCode(email, vcode).then(data => {
          if (data.ok) {
            this.verifyCodeErrorTip = data.data ? '' : '验证码错误'
          }
        })
      }
    },
    register: function () {
      let username = $('#username').val()
      let vcode = $('#verify-code').val()
      let email = $('#register-name').val()
      let password = $('#confirm-password').val()
      if (validator.isEmpty(username) || validator.isEmpty(email) || validator.isEmpty(vcode) || validator.isEmpty(password)) {
        utils.showWarning(this, '数据不能为空')
      } else if (validator.isEmpty(this.registerNameErrorTip) && validator.isEmpty(this.passwordConfirmErrorTip) && validator.isEmpty(this.verifyCodeErrorTip)) {
        requestRegister({username: username, email: email, password: password, vcode: vcode}).then(data => {
          if (data.ok) {
            window.location = this.loginPath
          } else {
            utils.showError(this, data.msg)
          }
        })
      } else {
        utils.showWarning(this, '格式不正确')
      }
    }
  }
}
</script>

<style scoped>
  @import "../assets/css/style.css";
</style>
