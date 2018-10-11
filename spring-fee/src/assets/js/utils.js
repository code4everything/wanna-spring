import {requestVerifyCode} from '../../api/api'

export default {
  sendVerifyCodeByEmail (email) {
    console.info('send verify code to ' + email)
    requestVerifyCode(email).then(data => {
      console.info(data)
    })
  }
}
