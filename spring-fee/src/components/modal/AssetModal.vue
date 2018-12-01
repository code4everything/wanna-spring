<!--suppress HtmlFormInputWithoutLabel -->
<template>
  <div aria-hidden="true" aria-labelledby="asset-modal-label" class="modal fade" id="asset-modal"
       role="dialog" tabindex="-1">
    <div :style="{width:isMobile?'90%':'100%'}" class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title" id="asset-modal-label">{{title}}</h4>
          <button aria-hidden="true" class="close" data-dismiss="modal"
                  type="button">&times;
          </button>
        </div>
        <div class="modal-body">
          <div :data-id="income.id" class="container data">
            <div class="row">
              <div class="col-sm-8 col-7">
                <el-date-picker :clearable="false" :editable="false" :placeholder="dateTip"
                                class="w-100" v-model="income.date" value-format="yyyy-MM-dd"/>
              </div>
              <div class="col-sm-4 col-5">
                <el-select v-model="income.type">
                  <el-option :key="index" :label="type.tip" :value="type.value"
                             v-for="(type,index) in types"></el-option>
                </el-select>
              </div>
            </div>
            <br/>
            <div class="row">
              <div class="col-sm-4 col-6">
                <el-select v-model="income.way">
                  <el-option :key="index" :label="payWay" :value="index+1"
                             v-for="(payWay,index) in payWays"></el-option>
                </el-select>
              </div>
              <div class="col-sm-4 col-6">
                <el-select @change="saveCategory" allow-create default-first-option filterable
                           v-model="income.category">
                  <el-option :key="index" :label="category" :value="category"
                             v-for="(category,index) in categories"></el-option>
                </el-select>
              </div>
              <div class="col-12" v-if="isMobile"><br/></div>
              <div class="col-sm-4 col-12">
                <el-input :placeholder="moneyTip" type="text" v-model="income.money"/>
              </div>
            </div>
            <br/>
            <div class="row">
              <div class="col-sm-12 col-12">
                <el-input :placeholder="remarkTip" rows="3" type="textarea" v-model="income.remark"></el-input>
              </div>
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <el-button data-dismiss="modal" type="info"><i class="glyphicon glyphicon-remove"></i> {{closeTip}}
          </el-button>
          <el-button @click="saveIncome" type="success"><i class="glyphicon glyphicon-floppy-open"></i> {{saveTip}}
          </el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<!--suppress JSPrimitiveTypeWrapperUsage -->
<script>/* eslint-disable */
import utils from '../../assets/js/utils'
import validator from '../../../static/js/validator.min'
import dayjs from 'dayjs'
import $ from 'jquery'
import {requestListCategory, requestSaveCategory, requestSaveIncome, requestUpdateIncome} from '../../api/api'

export default {
  name: 'AssetModal',
  data () {
    return {
      dateTip: '日期',
      typeTip: '类型',
      title: '编辑收益记录',
      moneyTip: '金额',
      payWayTip: '支付方式',
      isMobile: false,
      remarkTip: '备注',
      categoryTip: '分类，双击或回车编辑',
      closeTip: '关闭',
      saveTip: '保存',
      types: [{value: -1, tip: '支出'}, {value: 1, tip: '收入'}],
      categories: ['未分类']
    }
  },
  props: ['income', 'payWays'],
  methods: {
    saveCategory: function () {
      let self = this
      if (validator.isEmpty(this.income.category)) {
        this.income.category = this.categories[0]
      }
      if (!this.categories.includes(this.income.category)) {
        requestSaveCategory(this.income.category).then(data => {
          if (data.code === 200) {
            self.categories.push(data.data.name)
          } else {
            utils.showError(this, data.msg)
          }
        })
      }
    },
    saveIncome: function () {
      if (dayjs(this.income.date).isValid() && $.isNumeric(this.income.money)) {
        if (validator.isEmpty(this.income.category)) {
          this.income.category = this.categories[0]
        }
        //处理金额
        this.income.money = Number(this.income.money).toFixed(2) * 100
        if (validator.isEmpty(this.income.id)) {
          requestSaveIncome(this.income).then(data => this.handleIncomeReturnData(data))
        } else {
          requestUpdateIncome(this.income.id, this.income).then(data => this.handleIncomeReturnData(data))
        }
      } else {
        utils.showWarning(this, '数据格式不合法')
      }
    },
    handleIncomeReturnData: function (data) {
      if (data.code === 200) {
        this.$parent.updateIncome(data.data)
      } else {
        utils.showError(this, data.msg)
      }
    },
  },
  mounted: function () {
    this.isMobile = utils.isMobile()
    let self = this
    requestListCategory().then(data => {
      if (data.code === 200 && data.data.length > 0) {
        data.data.forEach(category => {
          if (category.name !== self.categories[0]) {
            self.categories.push(category.name)
          }
        })
      } else if (data.code === 401) {
        utils.showError(this, data.msg)
      }
    })
  }
}
</script>

<style scoped>
  @import "../../assets/css/style.css";
</style>
