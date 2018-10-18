<template>
  <div>
    <!--操作菜单-->
    <div class="row">
      <div class="bg-light rounded col-10 offset-1 offset-sm-0 col-sm-12">
        <br/>
        <div class="row">
          <div class="col-sm-3 col-12 text-left">
            <h5 class="h5-v-middle">{{assetTip+asset+' '+moneyUnit}}</h5>
          </div>
          <div class="col-sm-3 col-12">
            <input type="date" class="form-control" :placeholder="dateStartTip" :title="dateStartTip"
                   data-toggle="tooltip"/>
          </div>
          <div class="col-12 br-space" v-if="isMobile"></div>
          <div class="col-sm-3 col-12">
            <input type="date" class="form-control" :placeholder="dateEndTip" :title="dateEndTip"
                   data-toggle="tooltip"/>
          </div>
          <div class="col-12 br-space" v-if="isMobile"></div>
          <div class="col-sm-3 col-12">
            <div class="row">
              <div class="col-sm-6 col-6">
                <button class="btn btn-primary btn-block"><i class="glyphicon glyphicon-plus-sign"></i> {{addIncomeTip}}
                </button>
              </div>
              <div class="col-sm-6 col-6">
                <button class="btn btn-info btn-block"><i class="glyphicon glyphicon-equalizer"></i> {{reporterTip}}
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
    <div v-for="(income,index) in incomes" :key="index">
      <div class="row" :data-index="index">
        <div class="bg-light rounded col-10 offset-1 offset-sm-0 col-sm-12 text-left" :data-index="index">
          <div class="row vertical-middle">
            <div class="col-sm-10 col-12">
              <div v-if="isMobile">
                <h6 v-html="formatDateString(income)"></h6>
                <h6 v-html="formatIncomeString(income)"></h6>
              </div>
              <h5 v-else v-html="formatFullString(income)"></h5>
            </div>
            <div :class="['col-sm-2 col-12',isMobile?'text-left':'text-right']">
              <a href="javascript:">{{editTip}}</a>&emsp;<a href="javascript:" style="color: red;">{{removeTip}}</a>
            </div>
          </div>
        </div>
      </div>
      <br/>
    </div>
    <br/>
  </div>
</template>

<script>/* eslint-disable */
import utils from '../../assets/js/utils'

export default {
  name: 'Income',
  data () {
    return {
      assetTip: '总资产：',
      asset: '0',
      moneyUnit: '元',
      dateStartTip: '开始日期',
      dateEndTip: '截止日期',
      addIncomeTip: '添加',
      reporterTip: '报表',
      unit: '元',
      isMobile: false,
      space: '&emsp;',
      editTip: '编辑',
      removeTip: '删除',
      incomes: [{
        'assetId': 'string',
        'category': '饮食',
        'createTime': 0,
        'date': 'string',
        'day': 9,
        'id': 'string',
        'money': 233,
        'month': 10,
        'remark': 'string',
        'type': -1,
        'way': '0',
        'year': 2018
      }, {
        'assetId': 'string',
        'category': '饮食',
        'createTime': 0,
        'date': 'string',
        'day': 9,
        'id': 'string',
        'money': 233,
        'month': 10,
        'remark': 'string',
        'type': -1,
        'way': '0',
        'year': 2018
      }, {
        'assetId': 'string',
        'category': '饮食',
        'createTime': 0,
        'date': 'string',
        'day': 9,
        'id': 'string',
        'money': 233,
        'month': 10,
        'remark': 'string',
        'type': -1,
        'way': '0',
        'year': 2018
      }, {
        'assetId': 'string',
        'category': '饮食',
        'createTime': 0,
        'date': 'string',
        'day': 9,
        'id': 'string',
        'money': 233,
        'month': 10,
        'remark': '我是备注',
        'type': -1,
        'way': '0',
        'year': 2018
      }]
    }
  },
  methods: {
    formatFullString: function (income) {
      return this.formatDateString(income) + this.space + this.formatIncomeString(income) + this.space + '备注：' + income.remark
    },
    formatIncomeString: function (income) {
      return (income.type < 0 ? '支出' : '收入') + this.space + income.category + this.space + (income.money / 100) + this.space + this.unit
    },
    formatDateString: function (income) {
      return income.year + '-' + utils.formatInteger(income.month, 2) + '-' + utils.formatInteger(income.day, 2)
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
