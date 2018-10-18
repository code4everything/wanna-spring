export default {
  formatInteger: function (value, length) {
    let str = value === null || value === undefined ? '' : value.toString()
    while (str.length < length) {
      str = '0' + str
    }
    return str
  },
  isMobile: function () {
    let userAgent = navigator.userAgent.toLowerCase()
    let isPad = userAgent.indexOf('ipad') > -1
    let isIpone = userAgent.indexOf('iphone') > -1
    let isMidp = userAgent.indexOf('midp') > -1
    let isUc = userAgent.indexOf('rv:1.2.3.4') > -1 || userAgent.indexOf('ucweb') > -1
    let isAndroid = userAgent.indexOf('android') > -1
    let isCe = userAgent.indexOf('windows ce') > -1
    let isWm = userAgent.indexOf('windows mobile') > -1
    return isPad || isIpone || isMidp || isUc || isAndroid || isCe || isWm || userAgent.indexOf('mobile') > -1
  },
  getCookie: function (name) {
    if (document.cookie.length > 0) {
      let start = document.cookie.indexOf(name + '=')
      if (start !== -1) {
        start += name.length + 1
        let end = document.cookie.indexOf(';', start)
        if (end === -1) {
          end = document.cookie.length
        }
        return document.cookie.substring(start, end)
      }
    }
    return ''
  },
  setCookie: function (key, value) {
    let date = new Date()
    document.cookie = key + '=' + value + ';expires=' + date.setTime(date.getTime() + 30 * 24 * 60 * 60 * 1000)
  }
}
