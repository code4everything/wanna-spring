export default {
  clone: function (object) {
    return JSON.parse(JSON.stringify(object))
  },
  isNotNull: function (e) {
    return !this.isNull(e)
  },
  isNull: function (e) {
    return e === null || e === undefined
  },
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
  }
}
