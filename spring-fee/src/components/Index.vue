<!--suppress HtmlFormInputWithoutLabel -->
<template>
  <div>
    <nav class="navbar navbar-expand-sm bg-dark navbar-dark fixed-top">
      <img :src="logoUrl" class="navbar-brand rounded-circle" style="width: 2rem;height: 2.5rem;"/>
      <button aria-expanded="false" class="navbar-toggler" data-target="#collapsibleNavbar"
              data-toggle="collapse" type="button">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="collapsibleNavbar">
        <ul class="navbar-nav nav nav-pills">
          <li :key="index" class="nav-item" v-for="(menu,index) in menus">
            <a :href="menu.href" :id="menu.id" @click="navigateTo" class="nav-link"
               data-toggle="pill">{{menu.title}}</a>
          </li>
        </ul>
      </div>
    </nav>
    <br/>
    <div class="row justify-content-center">
      <div class="col-sm-10 col-12">
        <router-view/>
      </div>
    </div>
  </div>
</template>

<script>/* eslint-disable indent */
import app from '../App'
import $ from 'jquery'
import cookie from 'js-cookie'

const activeStr = 'active'

export default {
  name: 'Index',
  data () {
    return {
      logoUrl: 'static/img/logo.png',
      searchTip: '搜索',
      menus: [{
        id: 'nav-01',
        href: app.data().path.income,
        title: '收入支出'
      }, {
        id: 'nav-02',
        href: app.data().path.daily,
        title: '日常记录'
      }, {
        id: 'nav-03',
        href: app.data().path.todo,
        title: '待办事项'
      }, {
        id: 'nav-04',
        href: app.data().path.job,
        title: '工作日志'
      }, {
        id: 'nav-05',
        href: app.data().path.passwordReset,
        title: '重置密码'
      }, {
        id: 'nav-06',
        href: app.data().path.login,
        title: '登出账号'
      }]
    }
  },
  mounted: function () {
    // 检测路径，并路由到指定路径
    const href = '/' + window.location.hash
    if (href === app.data().path.index) {
      window.location.href = this.menus[0].href
    }
    this.menus.forEach(menu => {
      let id = '#' + menu.id
      if (href === menu.href) {
        $(id).addClass(activeStr)
      } else {
        $(id).removeClass(activeStr)
      }
    })
  },
  methods: {
    navigateTo: function () {
      let href = $(window.event.srcElement).attr('href')
      if (href === app.data().path.login) {
        cookie.remove('token')
      }
      if (href === app.data().path.passwordReset) {
        window.open(href)
      } else {
        window.location.href = href
      }
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  @import "../assets/css/style.css";
</style>
