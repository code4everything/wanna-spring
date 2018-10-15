<!--suppress ALL -->
<template>
  <div>
    <h3>{{registerWelcomeMessage}}</h3><br/>
    <input type="text" id="username" maxlength="50" class="form-control" :placeholder="usernameTip"/>
    <label class="form-check-label text-danger">{{usernameErrorTip}}</label>
    <br/>
    <input type="text" id="register-name" maxlength="100" :class="['form-control','register-name']"
           :placeholder="registerNameTip" @keyup="checkRegisterName">
    <label class="form-check-label text-danger">{{registerNameErrorTip}}</label>
    <br/>
    <div class="form-inline text-justify-all row">
      <div class="col-sm-7 col-6">
        <input type="number" id="verify-code" maxlength="6"
               class="w-100 form-control verify-code"
               :placeholder="verifyCodeTip" @keyup="validateVerifyCode"/>
      </div>
      <div class="col-sm-5 col-6 text-right">
        <button class="btn btn-outline-info sendVerifyCode btn-block" @click="sendVerifyCode">{{verifyCodeSendTip}}
        </button>
      </div>
    </div>
    <label class="form-check-label text-danger">{{verifyCodeErrorTip}}</label>
    <br/>
    <input type="password" id="register-password" maxlength="50" class="form-control password"
           :placeholder="passwordTip">
    <label class="form-check-label text-danger">{{passwordErrorTip}}</label>
    <br/>
    <input type="password" id="confirm-password" max="50" class="form-control confirm-password"
           :placeholder="passwordConfirmTip" @keyup="checkPasswordConsistency">
    <label class="form-check-label text-danger">{{passwordConfirmErrorTip}}</label>
    <br/>
    <div class="text-center row">
      <div class="col-6 col-sm-6">
        <a class="btn btn-outline-warning btn-block" :href="loginPath">{{loginPathTip}}</a>
      </div>
      <div class="col-6 col-sm-6">
        <button class="btn btn-primary btn-block" @click="register">{{registerTip}}</button>
      </div>
    </div>
  </div>
</template>

<script>/* eslint-disable indent */
import app from '../App'
import validator from '../../static/js/validator.min'
import $ from '../../static/js/jquery-3.3.1'
import {requestRegister, requestValidateVerifyCode, requestVerifyCode} from '../api/api'
import layer from '../../static/js/layer'

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
        layer.load(1)
        requestVerifyCode(email).then(data => {
          layer.closeAll()
          console.info(data)
          layer.alert(data.message)
        })
      }
    },
    validateVerifyCode: function () {
      let vcode = $('#verify-code').val()
      let email = $('#register-name').val()
      if (vcode.length === 6 && validator.isEmail(email)) {
        requestValidateVerifyCode({email: email, vcode: vcode}).then(data => {
          console.info(data)
          this.verifyCodeErrorTip = data.code === 200 ? '' : '验证码错误'
        })
      }
    },
    register: function () {
      let username = $('#username').val()
      let vcode = $('#verify-code').val()
      let email = $('#register-name').val()
      let password = $('#confirm-password').val()
      if (validator.isEmpty(username) || validator.isEmpty(email) || validator.isEmpty(vcode) || validator.isEmpty(password)) {
        layer.alert('数据不能为空')
      } else if (validator.isEmpty(this.registerNameErrorTip) && validator.isEmpty(this.passwordConfirmErrorTip) && validator.isEmpty(this.verifyCodeErrorTip)) {
        layer.load(1)
        requestRegister({username: username, email: email, password: password, vcode: vcode}).then(data => {
          layer.closeAll()
          console.info(data)
          if (data.code === 200) {
            window.location = this.loginPath
          } else {
            layer.alert(data.message)
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
