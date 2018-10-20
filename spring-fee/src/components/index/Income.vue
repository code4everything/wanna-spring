<template>
  <div>
    <!--操作菜单-->
    <div class="row">
      <div class="bg-light rounded col-10 offset-1 offset-sm-0 col-sm-12">
        <br/>
        <div class="row">
          <div class="col-sm-3 col-8 text-left">
            <h6 v-if="isMobile" style="padding-top: 10px;" v-html="formatAssetString()"></h6>
            <h5 v-else class="h5-v-middle" v-html="formatAssetString()"></h5>
          </div>
          <div v-if="!isMobile" class="col-sm-3">
            <input type="date" class="form-control" :placeholder="dateStartTip" :title="dateStartTip"
                   data-toggle="tooltip"/>
          </div>
          <div v-if="!isMobile" class="col-sm-3">
            <input type="date" class="form-control" :placeholder="dateEndTip" :title="dateEndTip"
                   data-toggle="tooltip"/>
          </div>
          <div class="col-sm-3 col-4">
            <div class="row">
              <div class="col-sm-6 col-12">
                <button class="btn btn-primary btn-block" @click="showModal"><i
                  class="glyphicon glyphicon-plus-sign"></i> {{addIncomeTip}}
                </button>
              </div>
              <div v-if="!isMobile" class="col-sm-6 col-6">
                <button class="btn btn-info btn-block" @click="showReporter"><i
                  class="glyphicon glyphicon-equalizer"></i> {{reporterTip}}
                </button>
              </div>
            </div>
          </div>
        </div>
        <br/>
      </div>
    </div>
    <!--内容模块-->
    <br/>
    <!--移动端-->
    <div v-if="isMobile">
      <div v-for="(income,index) in incomes" :key="index">
        <div class="row data" :data-index="index">
          <div class="bg-light rounded col-10 offset-1 text-left" :data-index="index">
            <div class="row vertical-middle">
              <div class="col-12">
                <h6 v-html="formatDateString(income)"></h6>
                <h6 v-html="formatIncomeString(income)"></h6>
              </div>
              <div class="col-12 text-left">
                <a href="javascript:" class="text-info" @click="showModal">{{editTip}}</a>
                &emsp;<a href="javascript:" class="text-danger" @click="remove">{{removeTip}}</a>
              </div>
            </div>
          </div>
        </div>
        <br/>
      </div>
    </div>
    <!--电脑端-->
    <div v-else class="row">
      <div class="col-sm-12 bg-light rounded justify-content-center text-center">
        <br/>
        <table class="table table-hover">
          <thead>
          <tr>
            <th>编号</th>
            <th>日期</th>
            <th>类型</th>
            <th>分类</th>
            <th>金额</th>
            <th>支付方式</th>
            <th>创建时间</th>
            <th>动作</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="(income,index) in incomes" :key="index" :data-index="index" class="data">
            <td>{{index+1}}</td>
            <td>{{formatDateString(income)}}</td>
            <td>{{formatTypeString(income)}}</td>
            <td>{{income.category}}</td>
            <td>{{income.money/100}}</td>
            <td>{{income.way}}</td>
            <td>{{income.createTime}}</td>
            <td>
              <a class="text-info" href="javascript:" @click="showModal">{{editTip}}</a>
              &emsp;<a href="javascript:" class="text-danger" @click="remove">{{removeTip}}</a>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
    <br/>
  </div>
</template>

<script>/* eslint-disable */
import utils from '../../assets/js/utils'
import layer from '../../../static/js/layer'

export default {
  name: 'Income',
  data () {
    return {
      assetTip: '总资产：',
      asset: '0',
      dateStartTip: '开始日期',
      dateEndTip: '截止日期',
      addIncomeTip: '添加',
      reporterTip: '报表',
      unit: '元',
      isMobile: false,
      space: '&emsp;',
      editTip: '编辑',
      removeTip: '删除',
      incomes: []
    }
  },
  methods: {
    remove: function () {
      let key = $(window.event.srcElement).parents('.data').attr('data-index')
      let self = this
      layer.confirm('是否确定删除索引位置位于 “' + (parseInt(key) + 1) + '” 的收益记录', {
        btn: ['确定', '取消']
      }, function () {
        self.incomes.splice(key, 1)
        layer.closeAll()
      })
    },
    formatAssetString: function () {
      return this.assetTip + this.asset + this.space + this.unit
    },
    formatIncomeString: function (income) {
      return this.formatTypeString(income) + this.space + income.category + this.space + (income.money / 100) + this.space + this.unit
    },
    formatDateString: function (income) {
      return income.year + '-' + utils.formatInteger(income.month, 2) + '-' + utils.formatInteger(income.day, 2)
    },
    formatTypeString: function (income) {
      return income.type < 0 ? '支出' : '收入'
    },
    showReporter: function () {
      layer.alert('敬请期待')
    },
    showModal: function () {

    }
  },
  mounted: function () {
    this.isMobile = utils.isMobile()
  },
  updated: function () {
    $('[data-toggle="tooltip"]').tooltip()
  }
}
</script>

<style scoped>
  @import "../../assets/css/style.css";

  .vertical-middle {
    margin: 15px 0 10px 0;
  }
</style>
