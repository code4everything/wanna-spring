<!--suppress HtmlFormInputWithoutLabel -->
<template>
  <div>
    <nav class="navbar navbar-expand-sm bg-dark navbar-dark fixed-top">
      <img :src="logoUrl" class="navbar-brand rounded-circle" style="width: 2rem;height: 2.5rem;"/>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar"
              @click="toggleSearchStatus" aria-expanded="false">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="collapsibleNavbar">
        <ul class="navbar-nav nav nav-pills">
          <li class="nav-item" v-for="(menu,index) in menus" :key="index">
            <a class="nav-link" :id="menu.id" data-toggle="pill" :href="menu.href"
               @click="navigateTo">{{menu.title}}</a>
          </li>
        </ul>
      </div>
      <div class="form-inline w-50" v-show="shouldShowSearch">
        <input class="form-control border-dark bg-dark text-white form-control-sm w-100" type="search"
               :placeholder="searchTip" id="search"/>
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
import validator from '../../static/js/validator.min'
import utils from '../assets/js/utils'

const activeStr = 'active'

export default {
  name: 'Index',
  data () {
    return {
      shouldShowSearch: true,
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
        href: app.data().path.profile,
        title: '我的资料'
      }]
    }
  },
  mounted: function () {
    // 检测当前用户是否登录
    let token = utils.getCookie('token')
    if (token === null || validator.isEmpty(token)) {
      window.location.href = app.data().path.login
    }
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
      window.location.href = $(window.event.srcElement).attr('href')
    },
    toggleSearchStatus: function () {
      this.shouldShowSearch = $('.navbar-toggler').attr('aria-expanded') === 'true'
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  @import "../assets/css/style.css";
</style>
