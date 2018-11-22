<!--suppress HtmlFormInputWithoutLabel -->
<template>
  <div class="row">
    <!--日程记录-->
    <div class="rounded bg-light col-10 offset-1 col-sm-11 offset-sm-1">
      <br/>
      <div class="row">
        <div class="col-sm-2 col-4 text-right"><h5 class="h5-v-middle">{{weatherTip}}：</h5></div>
        <div class="col-sm-3 col-7">
          <el-input type="text" v-model="daily.weather" :placeholder="weatherTip"/>
        </div>
        <div v-if="isMobile" class="col-12"><br/></div>
        <div class="col-sm-2 col-4 text-right"><h5 class="h5-v-middle">{{scoreTip}}：</h5></div>
        <div class="col-sm-4 col-7">
          <el-input-number class="w-100" v-model="daily.score" :placeholder="scoreTip" :min="0" :max="10"/>
        </div>
      </div>
      <br/>
      <div class="row">
        <div class="col-sm-2 col-4 text-right"><h5 class="h5-v-middle">{{contentTip}}：</h5></div>
        <div class="col-sm-9 col-7">
          <el-input type="textarea" v-model="daily.content" :placeholder="contentTip" rows="5"></el-input>
        </div>
      </div>
      <br/>
      <div class="row">
        <div class="col-sm-10 offset-sm-1 offset-1 col-10 text-right">
          <el-button type="warning" icon="el-icon-circle-plus" @click="showModal">{{saveDetailTip}}</el-button>
          <el-button type="primary" icon="el-icon-success" @click="saveDaily">{{saveTip}}</el-button>
        </div>
      </div>
      <br/>
    </div>
    <div class="col-12 col-sm-12"><br/></div>
    <!--日程详细记录-->
    <div class="rounded bg-light col-10 offset-1 col-sm-11 offset-sm-1">
      <br/>
      <el-table :data="dailyDetail">
        <el-table-column type="index" v-if="!isMobile"></el-table-column>
        <el-table-column prop="startTime" label="开始"></el-table-column>
        <el-table-column prop="endTime" label="结束"></el-table-column>
        <el-table-column prop="content" label="记录"></el-table-column>
        <el-table-column label="动作" v-if="!isMobile">
          <a class="text-primary" href="javascript:" @click="showModal">{{editTip}}</a>
          &emsp;<a href="javascript:" class="text-danger" @click="remove">{{removeTip}}</a>
        </el-table-column>
      </el-table>
    </div>
    <div class="col-12 col-sm-12"><br/></div>
    <daily-modal :dailies="currentDaily"></daily-modal>
  </div>
</template>

<script>/* eslint-disable */
import utils from '../../assets/js/utils'
import DailyModal from '../modal/DailyModal'
import validator from '../../../static/js/validator.min'
import {
  requestGetDaily,
  requestListDailies,
  requestRemoveDailies,
  requestSaveDaily,
  requestUpdateDaily
} from '../../api/api'

export default {
  name: 'Daily',
  components: {DailyModal},
  data () {
    return {
      isMobile: false,
      saveDetailTip: '添加片段',
      saveTip: '保存',
      contentTip: '日志',
      scoreTip: '分数',
      weatherTip: '天气',
      editTip: '编辑',
      removeTip: '删除',
      defaultDailyDetail: {content: '', dailyId: '', endTime: '', id: '', startTime: ''},
      defaultDaily: {id: '', score: 8, weather: '', content: '', date: ''},
      daily: {},
      dailyDetail: [],
      currentIndex: 0,
      currentDaily: {},
      isFirst: true
    }
  },
  props: ['date'],
  methods: {
    showModalOnMobile: function () {
      if (this.isMobile) {
        this.showModal()
      }
    },
    showModal: function () {
      if (validator.isEmpty(this.daily.id)) {
        this.$message({
          showClose: true,
          message: '请先保存日程记录',
          type: 'warning'
        })
      } else {
        this.currentIndex = $(window.event.srcElement).parents('.data').attr('data-index')
        let daily = utils.isNull(this.currentIndex) ? this.defaultDailyDetail : this.dailyDetail[this.currentIndex]
        this.currentDaily = utils.clone(daily)
        $('#daily-modal').modal('show')
      }
    },
    remove: function () {
      // TODO table更新后index同样需要更新
      let index = $(window.event.srcElement).parents('.data').attr('data-index')
      if (utils.isNull(index)) {
        index = this.currentIndex
      }
      if (utils.isNotNull(index)) {
        let self = this
        this.$confirm(`是否确定删除索引位置位于 '${parseInt(index) + 1}' 的日程记录`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          requestRemoveDailies(self.dailyDetail[index].id).then(data => {
            if (data.code === 200) {
              self.dailyDetail.splice(index, 1)
              utils.showSuccess(data.msg)
            }
          })
        })
      }
    },
    saveDaily: function () {
      this.daily.date = this.date
      if (validator.isEmpty(this.daily.id)) {
        requestSaveDaily(this.daily).then(data => this.handleDailyReturnData(data))
      } else {
        requestUpdateDaily(this.daily).then(data => this.handleDailyReturnData(data))
      }
    },
    handleDailyReturnData: function (data) {
      if (data.code === 200) {
        this.daily = data.data
        this.$parent.getChartData()
        utils.showSuccess(data.msg)
      }
    },
    updateDailies: function (dailies) {
      $('#daily-modal').modal('hide')
      if (utils.isNull(this.currentIndex)) {
        this.dailyDetail.unshift(dailies)
      } else {
        this.dailyDetail.splice(this.currentIndex, 1, dailies)
      }
    },
    listDaily: function () {
      this.daily = utils.clone(this.defaultDaily)
      this.dailyDetail = []
      requestGetDaily(this.date).then(data => {
        if (data.code === 200) {
          this.daily = data.data
          requestListDailies(data.data.id).then(data => {
            if (data.code === 200) {
              this.dailyDetail = data.data
            }
          })
        }
      })
    }
  },
  watch: {
    date: function () {
      if (!this.isFirst) {
        this.listDaily()
      }
    }
  },
  mounted: function () {
    this.currentDaily = utils.clone(this.defaultDailyDetail)
    this.isMobile = utils.isMobile()
    setTimeout(() => {
      if (utils.isNotNull(this.date) && !validator.isEmpty(this.date)) {
        this.listDaily()
        this.isFirst = false
        this.$parent.getChartData()
      }
    }, 500)
  }
}
</script>

<style scoped>
  .h5-v-middle {
    padding-top: 10px;
  }
</style>
