export default {
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
