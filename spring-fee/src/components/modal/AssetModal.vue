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
                <input type="date" class="form-control" :placeholder="dateTip" :title="dateTip"
                       data-toggle="tooltip" v-model="income.date"/>
              </div>
              <div class="col-sm-4 col-5">
                <select class="form-control" :title="typeTip" v-model="income.type" data-toggle="tooltip">
                  <option v-for="(type,index) in types" :value="type.value" :key="index">{{type.tip}}</option>
                </select>
              </div>
            </div>
            <br/>
            <div class="row">
              <div class="col-sm-4 col-6">
                <select class="form-control" :title="payWayTip" v-model="income.way" data-toggle="tooltip">
                  <option v-for="(payWay,index) in payWays" :value="index+1" :key="index">{{payWay}}</option>
                </select>
              </div>
              <div class="col-sm-4 col-6">
                <input type="text" class="form-control" :placeholder="categoryTip" :title="categoryTip"
                       data-toggle="tooltip" v-show="editable" v-model="income.category" @keyup.enter="saveCategory"
                       @blur="editable=false" id="category-edit"/>
                <select v-show="!editable" class="form-control" :title="categoryTip" v-model="income.category"
                        data-toggle="tooltip" @dblclick="toEdit" @keyup.enter="toEdit">
                  <option v-for="(category,index) in categories" :value="category" :key="index">{{category}}</option>
                </select>
              </div>
              <div v-if="isMobile" class="col-12"><br/></div>
              <div class="col-sm-4 col-12">
                <input type="text" class="form-control" :placeholder="moneyTip" data-toggle="tooltip" :title="moneyTip"
                       v-model="income.money"/>
              </div>
            </div>
            <br/>
            <div class="row">
              <div class="col-sm-12 col-12">
                <textarea class="form-control" :placeholder="remarkTip" data-toggle="tooltip" :title="remarkTip"
                          v-model="income.remark"></textarea>
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
      editable: false,
      types: [{value: -1, tip: '支出'}, {value: 1, tip: '收入'}],
      categories: ['未分类']
    }
  },
  props: ['income', 'payWays'],
  methods: {
    toEdit: function () {
      this.editable = true
      setTimeout(() => $('#category-edit').focus(), 200)
    },
    saveCategory: function () {
      let self = this
      if (validator.isEmpty(this.income.category)) {
        this.income.category = this.categories[0]
        this.editable = false
      }
      if (this.editable) {
        layer.load(1)
        requestSaveCategory(this.income.category).then(data => {
          layer.closeAll()
          if (data.code === 200) {
            self.categories.push(data.data.name)
            self.editable = false
          }
        })
      }
    },
    saveIncome: function () {
      let self = this
      if (dayjs(this.income.date).isValid() && $.isNumeric(this.income.money)) {
        if (validator.isEmpty(this.income.category)) {
          this.income.category = this.categories[0]
        }
        layer.load(1)
        if (validator.isEmpty(this.income.id)) {
          requestSaveIncome(this.income).then(data => {
            this.handleIncomeReturnData(data, self)
          })
        } else {
          requestUpdateIncome(this.income.id, this.income).then(data => {
            this.handleIncomeReturnData(data, self)
          })
        }
        $('#asset-modal').modal('hide')
      } else {
        layer.alert('数据格式不合法')
      }
    },
    handleIncomeReturnData: function (data, that) {
      layer.closeAll()
      if (data.code === 200) {
        that.$parent.updateIncome(data.data)
      } else {
        layer.alert(data.message)
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
      }
    })
  },
  updated: function () {
    // 处理时间和金额
  }
}
</script>

<style scoped>
  @import "../../assets/css/style.css";
</style>
