<!--suppress HtmlFormInputWithoutLabel -->
<template>
  <div class="modal fade" id="asset-modal" tabindex="-1" role="dialog"
       aria-labelledby="asset-modal-label" aria-hidden="true">
    <div class="modal-dialog" :style="{width:isMobile?'90%':'100%'}">
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title" id="asset-modal-label">{{title}}</h4>
          <button type="button" class="close" data-dismiss="modal"
                  aria-hidden="true">&times;
          </button>
        </div>
        <div class="modal-body">
          <div class="container data" :data-id="income.id">
            <div class="row">
              <div class="col-sm-8 col-7">
                <el-date-picker :placeholder="dateTip" :title="dateTip" class="w-100" value-format="yyyy-MM-dd"
                                data-toggle="tooltip" v-model="income.date"/>
              </div>
              <div class="col-sm-4 col-5">
                <el-select :title="typeTip" v-model="income.type" data-toggle="tooltip">
                  <el-option v-for="(type,index) in types" :value="type.value" :key="index"
                             :label="type.tip"></el-option>
                </el-select>
              </div>
            </div>
            <br/>
            <div class="row">
              <div class="col-sm-4 col-6">
                <el-select :title="payWayTip" v-model="income.way" data-toggle="tooltip">
                  <el-option v-for="(payWay,index) in payWays" :value="index+1" :key="index"
                             :label="payWay"></el-option>
                </el-select>
              </div>
              <div class="col-sm-4 col-6">
                <el-select :title="categoryTip" v-model="income.category" filterable allow-create
                           data-toggle="tooltip" @change="saveCategory" default-first-option>
                  <el-option v-for="(category,index) in categories" :value="category" :key="index"
                             :label="category"></el-option>
                </el-select>
              </div>
              <div v-if="isMobile" class="col-12"><br/></div>
              <div class="col-sm-4 col-12">
                <el-input type="text" :placeholder="moneyTip" data-toggle="tooltip" :title="moneyTip"
                          v-model="income.money"/>
              </div>
            </div>
            <br/>
            <div class="row">
              <div class="col-sm-12 col-12">
                <el-input type="textarea" :placeholder="remarkTip" data-toggle="tooltip" :title="remarkTip"
                          v-model="income.remark" rows="3"></el-input>
              </div>
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-dismiss="modal"><i
            class="glyphicon glyphicon-remove"></i>
            {{closeTip}}
          </button>
          <button type="button" class="btn btn-success" @click="saveIncome"><i
            class="glyphicon glyphicon-floppy-open"></i>
            {{saveTip}}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<!--suppress JSPrimitiveTypeWrapperUsage -->
<script>/* eslint-disable */
import utils from '../../assets/js/utils'
import validator from '../../../static/js/validator.min'
import layer from '../../../static/js/layer'
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
        layer.load(1)
        requestSaveCategory(this.income.category).then(data => {
          layer.closeAll()
          if (data.code === 200) {
            self.categories.push(data.data.name)
          } else {
            layer.alert(data.msg)
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
        layer.load(1)
        if (validator.isEmpty(this.income.id)) {
          requestSaveIncome(this.income).then(data => this.handleIncomeReturnData(data))
        } else {
          requestUpdateIncome(this.income.id, this.income).then(data => this.handleIncomeReturnData(data))
        }
      } else {
        layer.alert('数据格式不合法')
      }
    },
    handleIncomeReturnData: function (data) {
      layer.closeAll()
      if (data.code === 200) {
        this.$parent.updateIncome(data.data)
      } else {
        layer.alert(data.msg)
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
        layer.alert(data.msg)
      }
    })
  }
}
</script>

<style scoped>
  @import "../../assets/css/style.css";
</style>
