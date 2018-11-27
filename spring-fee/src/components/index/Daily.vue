<!--suppress HtmlFormInputWithoutLabel -->
<template>
  <div class="row">
    <!--日程记录-->
    <div class="rounded bg-light col-10 offset-1 col-sm-11 offset-sm-1">
      <br/>
      <div class="row">
        <div class="col-sm-2 col-4 text-right"><h5 class="h5-v-middle">{{weatherTip}}：</h5></div>
        <div class="col-sm-3 col-7">
          <el-input :placeholder="weatherTip" type="text" v-model="daily.weather"/>
        </div>
        <div class="col-12" v-if="isMobile"><br/></div>
        <div class="col-sm-2 col-4 text-right"><h5 class="h5-v-middle">{{scoreTip}}：</h5></div>
        <div class="col-sm-4 col-7">
          <el-input-number :max="10" :min="0" :placeholder="scoreTip" class="w-100" v-model="daily.score"/>
        </div>
      </div>
      <br/>
      <div class="row">
        <div class="col-sm-2 col-4 text-right"><h5 class="h5-v-middle">{{contentTip}}：</h5></div>
        <div class="col-sm-9 col-7">
          <el-input :placeholder="contentTip" rows="5" type="textarea" v-model="daily.content"></el-input>
        </div>
      </div>
      <br/>
      <div class="row">
        <div class="col-sm-10 offset-sm-1 offset-1 col-10 text-right">
          <el-button @click="showModal(null)" type="warning"><i class="glyphicon glyphicon-plus-sign"></i>
            {{saveDetailTip}}
          </el-button>
          <el-button @click="saveDaily" type="primary"><i class="glyphicon glyphicon-floppy-disk"></i> {{saveTip}}
          </el-button>
        </div>
      </div>
      <br/>
    </div>
    <div class="col-12 col-sm-12"><br/></div>
    <!--日程详细记录-->
    <div class="rounded bg-light col-10 offset-1 col-sm-11 offset-sm-1">
      <br/>
      <el-table :data="dailyDetail" @row-click="showModalOnMobile">
        <div v-if="!isMobile">
          <el-table-column type="index"></el-table-column>
        </div>
        <el-table-column label="开始" prop="startTime"></el-table-column>
        <el-table-column label="结束" prop="endTime"></el-table-column>
        <el-table-column label="记录" prop="content"></el-table-column>
        <el-table-column label="操作" v-if="!isMobile">
          <template slot-scope="scope">
            <a @click="showModal(scope.$index)" class="text-primary" href="javascript:">{{editTip}}</a>
            &emsp;<a @click="remove(scope.$index)" class="text-danger" href="javascript:">{{removeTip}}</a>
          </template>
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
    showModalOnMobile: function (row) {
      if (this.isMobile) {
        this.showModal(this.dailyDetail.indexOf(row))
      }
    },
    showModal: function (index) {
      if (validator.isEmpty(this.daily.id)) {
        utils.showWarning(this, '请先保存日程记录')
      } else {
        this.currentIndex = index
        let daily = utils.isNull(this.currentIndex) ? this.defaultDailyDetail : this.dailyDetail[this.currentIndex]
        this.currentDaily = utils.clone(daily)
        $('#daily-modal').modal('show')
      }
    },
    remove: function (index) {
      if (utils.isNull(index)) {
        index = this.currentIndex
      } else if (typeof index === 'object') {
        for (let i = 0; i < this.dailyDetail.length; i++) {
          if (this.dailyDetail[i].id === index.id) {
            index = i
            break
          }
        }
      }
      if (utils.isNotNull(index) && index > -1) {
        let self = this
        this.$confirm(`是否确定删除索引位置位于 '${parseInt(index) + 1}' 的日程记录`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          requestRemoveDailies(self.dailyDetail[index].id).then(data => {
            if (data.code === 200) {
              self.dailyDetail.splice(index, 1)
              utils.showSuccess(this, data.msg)
            } else {
              utils.showError(this, data.msg)
            }
          })
        }).catch(() => {
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
        utils.showSuccess(this, data.msg)
      } else {
        utils.showError(this, data.msg)
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
        } else {
          utils.showError(this, data.msg)
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
