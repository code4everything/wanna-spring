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
    <div class="row">
      <div class="bg-light rounded col-10 offset-1 offset-sm-0 col-sm-12">
        <br/>
        <br/>
      </div>
    </div>
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
      isMobile: false,
      incomes: []
    }
  },
  methods: {},
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
</style>
