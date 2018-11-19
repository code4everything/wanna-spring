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
            <el-date-picker :editable="false" :clearable="false" :placeholder="dateStartTip"
                            class="w-100" value-format="yyyy-MM-dd" v-model="startDate" @change="listIncome"/>
          </div>
          <div v-if="!isMobile" class="col-sm-3">
            <el-date-picker :editable="false" :clearable="false" :placeholder="dateEndTip"
                            class="w-100" value-format="yyyy-MM-dd" v-model="endDate" @change="listIncome"/>
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
                <h6 v-html="income.date"></h6>
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
            <td>{{income.date}}</td>
            <td>{{formatTypeString(income)}}</td>
            <td>{{income.category}}</td>
            <td>{{Number(income.money / 100).toFixed(2)}}</td>
            <td>{{payWays[income.way-1]}}</td>
            <!--suppress JSUnresolvedVariable -->
            <td>{{formatDate(income.createTime)}}</td>
            <td>
              <a class="text-info" href="javascript:" @click="showModal">{{editTip}}</a>
              &emsp;<a href="javascript:" class="text-danger" @click="remove">{{removeTip}}</a>
            </td>
          </tr>
          <tr v-show="totalExpense>0">
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td>{{totalExpenseTip}}</td>
            <td>{{Number(totalExpense / 100).toFixed(2)+' '+unit}}</td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
    <br/>
    <asset-modal :income="currentIncome" :pay-ways="payWays"></asset-modal>
    <!-- 报表弹窗 -->
    <el-dialog v-if="!isMobile" title="收益报表" :visible.sync="dialogVisible" :fullscreen="true">
      <!-- 基本数据 -->
      <div class="row">
        <div class="col-sm-4">
          <ve-pie :data="categoryData"></ve-pie>
        </div>
        <div class="col-sm-8">
          <ve-line :data="dayData"></ve-line>
        </div>
      </div>
      <!-- 月度报表 -->
      <div class="row">
        <div class="col-sm-6 offset-sm-3">
          <div class="row">
            <div class="col-sm-3"><h5 class="h5-v-middle">月度报表</h5></div>
            <div class="col-sm-3">
              <el-date-picker :editable="false" :clearable="false" v-model="startMonth" type="month" placeholder="开始月份"
                              value-format="yyyy-MM" class="w-100"></el-date-picker>
            </div>
            <div class="col-sm-3">
              <el-date-picker :editable="false" :clearable="false" v-model="endMonth" type="month" placeholder="结束月份"
                              value-format="yyyy-MM" class="w-100"></el-date-picker>
            </div>
            <div class="col-sm-3">
              <button class="btn btn-outline-success btn-block" @click="listIncomeMonth">查询</button>
            </div>
          </div>
        </div>
      </div>
      <br/>
      <div class="row">
        <div class="col-sm-12">
          <ve-histogram :data="monthData"></ve-histogram>
        </div>
      </div>
      <!-- 年度报表 -->
      <div class="row">
        <div class="col-sm-6 offset-sm-3">
          <div class="row">
            <div class="col-sm-3"><h5 class="h5-v-middle">年度报表</h5></div>
            <div class="col-sm-3">
              <el-date-picker :editable="false" :clearable="false" v-model="startYear" type="year" placeholder="开始年份"
                              value-format="yyyy" class="w-100"></el-date-picker>
            </div>
            <div class="col-sm-3">
              <el-date-picker :editable="false" :clearable="false" v-model="endYear" type="year" placeholder="结束年份"
                              value-format="yyyy" class="w-100"></el-date-picker>
            </div>
            <div class="col-sm-3">
              <button class="btn btn-outline-success btn-block" @click="listIncomeYear">查询</button>
            </div>
          </div>
        </div>
      </div>
      <br/>
      <div class="row">
        <div class="col-sm-12">
          <ve-histogram :data="yearData"></ve-histogram>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>/* eslint-disable */
import utils from '../../assets/js/utils'
import layer from '../../../static/js/layer'
import AssetModal from '../modal/AssetModal'
import validator from '../../../static/js/validator.min'
import dayjs from 'dayjs'
import {
  requestAssetBalance,
  requestListIncome,
  requestListIncomeMonth,
  requestListIncomeYear,
  requestRemoveIncome
} from '../../api/api'

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
      defaultIncome: {category: '未分类', date: '', money: '', remark: '', type: -1, way: 2, id: ''},
      payWays: ['其他', '支付宝', '微信', '银联', '信用卡', '现金'],
      incomes: [],
      startDate: '',
      endDate: '',
      totalExpenseTip: '支出合计',
      totalExpense: 0,
      dialogVisible: false,
      categoryData: {
        columns: ['category', 'total'],
        rows: []
      },
      dayData: {
        columns: ['date', '支出'],
        rows: []
      },
      startMonth: '',
      endMonth: '',
      monthData: {
        columns: ['month', '支出'],
        rows: []
      },
      startYear: '',
      endYear: '',
      yearData: {
        columns: ['year', '支出'],
        rows: []
      }
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
    formatTypeString: function (income) {
      return income.type < 0 ? '支出' : '收入'
    },
    showReporter: function () {
      this.dialogVisible = true
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
    },
    listIncomeMonth: function () {
      if (validator.isEmpty(this.startMonth) || validator.isEmpty(this.endMonth)) {
        this.$message.error('月份不能为空')
      } else {
        this.monthData.rows = []
        layer.load(1)
        requestListIncomeMonth(this.startMonth, this.endMonth).then(data => {
          layer.closeAll()
          if (data.code === 200) {
            data.data.forEach(item => {
              this.monthData.rows.push({'month': item.date, '支出': (item.money / 100).toFixed(2)})
            })
          } else {
            this.$message.error(data.msg)
          }
        })
      }
    },
    listIncomeYear: function () {
      if (validator.isEmpty(this.startYear) || validator.isEmpty(this.endYear)) {
        this.$message.error('年份不能为空')
      } else {
        this.yearData.rows = []
        layer.load(1)
        requestListIncomeYear(this.startYear, this.endYear).then(data => {
          layer.closeAll()
          if (data.code === 200) {
            data.data.forEach(item => {
              this.yearData.rows.push({'year': item.date, '支出': (item.money / 100).toFixed(2)})
            })
          } else {
            this.$message.error(data.msg)
          }
        })
      }
    }
  },
  mounted: function () {
    this.isMobile = utils.isMobile()
    this.startDate = dayjs().add(-30, 'day').format('YYYY-MM-DD')
    this.endDate = dayjs().format('YYYY-MM-DD')
    this.listIncome()
  },
  watch: {
    incomes: function () {
      if (!this.isMobile) {
        // 清空数据
        this.categoryData.rows = []
        this.dayData.rows = []
        this.totalExpense = 0
        this.incomes.forEach(income => {
          if (income.type === -1) {
            // 统计支出合计
            this.totalExpense += income.money
            // 统计分类合计
            let dest = this.categoryData.rows.filter(item => {
              return item.category === income.category
            })
            if (dest.length > 0) {
              dest[0].total += income.money
            } else {
              this.categoryData.rows.push({'category': income.category, 'total': income.money})
            }
            // 统计日支出合计
            dest = this.dayData.rows.filter(item => {
              return item.date === income.date
            })
            if (dest.length > 0) {
              dest[0]['支出'] += income.money
            } else {
              this.dayData.rows.unshift({'date': income.date, '支出': income.money})
            }
          }
        })
        // 格式化数据
        this.categoryData.rows.forEach(item => {
          item.total = (item.total / 100).toFixed(2)
        })
        this.dayData.rows.forEach(item => {
          item['支出'] = (item['支出'] / 100).toFixed(2)
        })
      }
    }
  }
}
</script>

<style scoped>
  @import "../../assets/css/style.css";

  .vertical-middle {
    margin: 15px 0 10px 0;
  }
</style>
