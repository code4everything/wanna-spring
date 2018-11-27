<!--suppress HtmlFormInputWithoutLabel -->
<template>
  <div>
    <!--操作菜单-->
    <div class="row">
      <div class="bg-light rounded col-10 offset-1 offset-sm-0 col-sm-12">
        <br/>
        <div class="row">
          <div class="col-sm-5 col-8 text-left">
            <h6 style="padding-top: 10px;" v-html="formatAssetString()" v-if="isMobile"></h6>
            <h5 class="h5-v-middle" v-else v-html="formatAssetString()"></h5>
          </div>
          <div class="col-sm-2 text-right" v-if="!isMobile">
            <el-date-picker :clearable="false" :editable="false" :placeholder="dateStartTip"
                            @change="listIncome" class="w-100" v-model="startDate" value-format="yyyy-MM-dd"/>
          </div>
          <div class="col-sm-2 text-left" v-if="!isMobile">
            <el-date-picker :clearable="false" :editable="false" :placeholder="dateEndTip"
                            @change="listIncome" class="w-100" v-model="endDate" value-format="yyyy-MM-dd"/>
          </div>
          <div class="col-sm-3 col-4 text-left">
            <div class="row">
              <div class="col-sm-6 col-12">
                <el-button @click="showModal(null)" class="w-100" type="primary"><i
                  class="glyphicon glyphicon-plus-sign"></i>
                  {{addIncomeTip}}
                </el-button>
              </div>
              <div class="col-sm-6 col-6" v-if="!isMobile">
                <el-button @click="showReporter" class="w-100" type="success"><i class="glyphicon glyphicon-stats"></i>
                  {{reporterTip}}
                </el-button>
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
      <div :key="index" v-for="(income,index) in incomes">
        <div :data-index="index" class="row data">
          <div :data-index="index" class="bg-light rounded col-10 offset-1 text-left">
            <div class="row vertical-middle">
              <div class="col-12">
                <h6 v-html="income.date"></h6>
                <h6 v-html="formatIncomeString(income)"></h6>
              </div>
              <div class="col-12 text-left">
                <a @click="showModal(null)" class="text-info" href="javascript:">{{editTip}}</a>
                &emsp;<a @click="remove(null)" class="text-danger" href="javascript:">{{removeTip}}</a>
              </div>
            </div>
          </div>
        </div>
        <br/>
      </div>
    </div>
    <!--电脑端-->
    <div class="row" v-else>
      <div class="col-sm-12 bg-light rounded justify-content-center text-center">
        <br/>
        <el-table :data="incomes">
          <el-table-column type="index"></el-table-column>
          <el-table-column align="center" label="日期" prop="date"></el-table-column>
          <el-table-column align="center" label="类型">
            <template slot-scope="scope">
              <span>{{formatTypeString(scope.row)}}</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="分类" prop="category"></el-table-column>
          <el-table-column align="center" label="金额">
            <template slot-scope="scope">
              <span>{{Number(scope.row.money / 100).toFixed(2)}}</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="支付方式">
            <template slot-scope="scope">
              <span>{{payWays[scope.row.way-1]}}</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="创建时间">
            <template slot-scope="scope">
              <span>{{formatDate(scope.row.createTime)}}</span>
            </template>
          </el-table-column>
          <el-table-column align="center" label="操作">
            <template slot-scope="scope">
              <a @click="showModal(scope.$index)" class="text-primary" href="javascript:">{{editTip}}</a>
              &emsp;<a @click="remove(scope.$index)" class="text-danger" href="javascript:">{{removeTip}}</a>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <br/>
    <asset-modal :income="currentIncome" :pay-ways="payWays"></asset-modal>
    <!-- 报表弹窗 -->
    <el-dialog :fullscreen="true" :visible.sync="dialogVisible" title="收益报表" v-if="!isMobile">
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
              <el-date-picker :clearable="false" :editable="false" class="w-100" placeholder="开始月份" type="month"
                              v-model="startMonth" value-format="yyyy-MM"></el-date-picker>
            </div>
            <div class="col-sm-3">
              <el-date-picker :clearable="false" :editable="false" class="w-100" placeholder="结束月份" type="month"
                              v-model="endMonth" value-format="yyyy-MM"></el-date-picker>
            </div>
            <div class="col-sm-3">
              <button @click="listIncomeMonth" class="btn btn-outline-success btn-block">查询</button>
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
              <el-date-picker :clearable="false" :editable="false" class="w-100" placeholder="开始年份" type="year"
                              v-model="startYear" value-format="yyyy"></el-date-picker>
            </div>
            <div class="col-sm-3">
              <el-date-picker :clearable="false" :editable="false" class="w-100" placeholder="结束年份" type="year"
                              v-model="endYear" value-format="yyyy"></el-date-picker>
            </div>
            <div class="col-sm-3">
              <button @click="listIncomeYear" class="btn btn-outline-success btn-block">查询</button>
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
    remove: function (index) {
      let key = utils.isNull(index) ? $(window.event.srcElement).parents('.data').attr('data-index') : index
      let self = this
      this.$confirm(`是否确定删除索引位置位于 “${parseInt(key) + 1}” 的收益记录`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        requestRemoveIncome(self.incomes[key].id).then(data => {
          if (data.code === 200) {
            self.getAssetBalance()
            self.incomes.splice(key, 1)
          } else {
            utils.showError(this, data.msg)
          }
        })
      }).catch(() => {
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
    showModal: function (index) {
      this.currentIndex = utils.isNull(index) ? $(window.event.srcElement).parents('.data').attr('data-index') : index
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
      this.getAssetBalance()
      requestListIncome('', this.startDate, this.endDate).then(data => {
        if (data.code === 200) {
          this.incomes = data.data
        } else {
          utils.showError(this, data.msg)
        }
      })
    },
    getAssetBalance: function () {
      requestAssetBalance().then(data => {
        if (data.code === 200) {
          this.asset = data.data / 100
        } else {
          utils.showError(this, data.msg)
        }
      })
    },
    listIncomeMonth: function () {
      if (validator.isEmpty(this.startMonth) || validator.isEmpty(this.endMonth)) {
        utils.showWarning(this, '月份不能为空')
      } else {
        this.monthData.rows = []
        requestListIncomeMonth(this.startMonth, this.endMonth).then(data => {
          if (data.code === 200) {
            data.data.forEach(item => {
              this.monthData.rows.push({'month': item.date, '支出': (item.money / 100).toFixed(2)})
            })
          } else {
            utils.showError(this, data.msg)
          }
        })
      }
    },
    listIncomeYear: function () {
      if (validator.isEmpty(this.startYear) || validator.isEmpty(this.endYear)) {
        utils.showWarning(this, '年份不能为空')
      } else {
        this.yearData.rows = []
        requestListIncomeYear(this.startYear, this.endYear).then(data => {
          if (data.code === 200) {
            data.data.forEach(item => {
              this.yearData.rows.push({'year': item.date, '支出': (item.money / 100).toFixed(2)})
            })
          } else {
            utils.showError(this, data.msg)
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
