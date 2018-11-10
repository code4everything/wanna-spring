<!--suppress HtmlFormInputWithoutLabel -->
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
                   data-toggle="tooltip" v-model="startDate" @change="listIncome"/>
          </div>
          <div v-if="!isMobile" class="col-sm-3">
            <input type="date" class="form-control" :placeholder="dateEndTip" :title="dateEndTip"
                   data-toggle="tooltip" v-model="endDate" @change="listIncome"/>
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
                  class="glyphicon glyphicon-stats"></i> {{reporterTip}}
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
            <th v-for="(th,index) in ths" :key="index">{{th}}</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="(income,index) in incomes" :key="index" :data-index="index" class="data">
            <td>{{index+1}}</td>
            <td>{{formatDateString(income)}}</td>
            <td>{{formatTypeString(income)}}</td>
            <td>{{income.category}}</td>
            <td>{{Number(income.money/100).toFixed(2)}}</td>
            <td>{{payWays[income.way-1]}}</td>
            <!--suppress JSUnresolvedVariable -->
            <td>{{formatDate(income.createTime)}}</td>
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
    <asset-modal :income="currentIncome" :pay-ways="payWays"></asset-modal>
  </div>
</template>

<script>/* eslint-disable */
import utils from '../../assets/js/utils'
import layer from '../../../static/js/layer'
import AssetModal from '../modal/AssetModal'
import dayjs from 'dayjs'
import {requestAssetBalance, requestListIncome, requestRemoveIncome} from '../../api/api'

export default {
  name: 'Income',
  components: {AssetModal},
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
      currentIncome: {},
      currentIndex: 0,
      ths: ['编号', '日期', '类型', '分类', '金额', '支付方式', '创建时间', '动作'],
      defaultIncome: {category: '未分类', date: '', money: '', remark: '', type: -1, way: 1, id: ''},
      payWays: ['其他', '支付宝', '微信', '银联', '信用卡', '现金'],
      incomes: [],
      startDate: '',
      endDate: ''
    }
  },
  methods: {
    remove: function () {
      let key = $(window.event.srcElement).parents('.data').attr('data-index')
      let self = this
      layer.confirm('是否确定删除索引位置位于 “' + (parseInt(key) + 1) + '” 的收益记录', {
        btn: ['确定', '取消']
      }, function () {
        layer.load(1)
        requestRemoveIncome(self.incomes[key].id).then(data => {
          layer.closeAll()
          if (data.code === 200) {
            self.getAssetBalance()
            self.incomes.splice(key, 1)
          } else {
            layer.alert(data.msg)
          }
        })
        layer.closeAll()
      })
    },
    formatDate: function (date) {
      return dayjs(date).format('YYYY-MM-DD HH:mm:ss')
    },
    formatAssetString: function () {
      return this.assetTip + Number(this.asset).toFixed(2) + this.space + this.unit
    },
    formatIncomeString: function (income) {
      return this.formatTypeString(income) + this.space + income.category + this.space + (income.money / 100).toFixed(2) + this.space + this.unit
    },
    formatDateString: function (income) {
      return income.year + '-' + String(income.month).padStart(2, '0') + '-' + String(income.day).padStart(2, '0')
    },
    formatTypeString: function (income) {
      return income.type < 0 ? '支出' : '收入'
    },
    showReporter: function () {
      layer.alert('敬请期待')
    },
    updateIncome: function (income) {
      this.getAssetBalance()
      $('#asset-modal').modal('hide')
      if (utils.isNull(this.currentIndex)) {
        this.incomes.unshift(income)
      } else {
        this.incomes.splice(this.currentIndex, 1, income)
      }
    },
    showModal: function () {
      this.currentIndex = $(window.event.srcElement).parents('.data').attr('data-index')
      if (utils.isNull(this.currentIndex)) {
        this.currentIncome = utils.clone(this.defaultIncome)
        this.currentIncome.date = dayjs(new Date()).format('YYYY-MM-DD')
      } else {
        this.currentIncome = utils.clone(this.incomes[this.currentIndex])
        this.currentIncome.date = this.formatDateString(this.currentIncome)
        this.currentIncome.money = this.currentIncome.money / 100
      }
      $('#asset-modal').modal('show')
    },
    listIncome: function () {
      layer.load(1)
      this.getAssetBalance()
      requestListIncome('', this.startDate, this.endDate).then(data => {
        layer.closeAll()
        if (data.code === 200) {
          this.incomes = data.data
        } else {
          layer.alert(data.msg)
        }
      })
    },
    getAssetBalance: function () {
      requestAssetBalance().then(data => {
        if (data.code === 200) {
          this.asset = data.data / 100
        } else {
          layer.alert(data.msg)
        }
      })
    }
  },
  mounted: function () {
    this.isMobile = utils.isMobile()
    this.startDate = dayjs().add(-30, 'day').format('YYYY-MM-DD')
    this.endDate = dayjs().format('YYYY-MM-DD')
    this.listIncome()
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
