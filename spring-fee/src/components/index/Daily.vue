<!--suppress HtmlFormInputWithoutLabel -->
<template>
  <div class="row">
    <!--日程记录-->
    <div class="rounded bg-light col-10 offset-1 col-sm-11 offset-sm-1">
      <br/>
      <div class="row">
        <div class="col-sm-2 col-4 text-right"><h5 class="h5-v-middle">{{weatherTip}}：</h5></div>
        <div class="col-sm-3 col-7">
          <input type="text" class="form-control" v-model="daily.weather" :placeholder="weatherTip"/>
        </div>
        <div v-if="isMobile" class="col-12"><br/></div>
        <div class="col-sm-2 col-4 text-right"><h5 class="h5-v-middle">{{scoreTip}}：</h5></div>
        <div class="col-sm-3 col-7">
          <input type="number" class="form-control" v-model="daily.score" :placeholder="scoreTip"/>
        </div>
      </div>
      <br/>
      <div class="row">
        <div class="col-sm-2 col-4 text-right"><h5 class="h5-v-middle">{{contentTip}}：</h5></div>
        <div class="col-sm-8 col-7">
          <textarea class="form-control" v-model="daily.content" :placeholder="contentTip" rows="5"></textarea>
        </div>
      </div>
      <br/>
      <div class="row">
        <div class="col-sm-8 offset-sm-2 offset-1 col-10 text-right">
          <button class="btn btn-info" @click="showModal"><i class="glyphicon glyphicon-plus-sign"></i>
            {{saveDetailTip}}
          </button>
          <button class="btn btn-primary" @click="saveDaily"><i class="glyphicon glyphicon-floppy-disk"></i> {{saveTip}}
          </button>
        </div>
      </div>
      <br/>
    </div>
    <div class="col-12 col-sm-12"><br/></div>
    <!--日程详细记录-->
    <div class="rounded bg-light col-10 offset-1 col-sm-11 offset-sm-1">
      <br/>
      <table class="table table-hover">
        <thead>
        <tr>
          <th v-for="(th,index) in ths" :key="index">{{th}}</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(dailies,index) in dailyDetail" :key="index" :data-index="index" class="data"
            @click="showModalOnMobile">
          <td v-if="!isMobile">{{index+1}}</td>
          <td>{{dailies.startTime}}</td>
          <td>{{dailies.endTime}}</td>
          <td>{{dailies.content}}</td>
          <td v-if="!isMobile">
            <a class="text-primary" href="javascript:" @click="showModal">{{editTip}}</a>
            &emsp;<a href="javascript:" class="text-danger" @click="remove">{{removeTip}}</a>
          </td>
        </tr>
        </tbody>
      </table>
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
import layer from '../../../static/js/layer'

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
      defaultDaily: {id: '', score: '', weather: '', content: '', date: ''},
      daily: {},
      ths: ['编号', '开始', '结束', '记录', '动作'],
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
        layer.alert('请先保存日程记录')
      } else {
        this.currentIndex = $(window.event.srcElement).parents('.data').attr('data-index')
        let daily = utils.isNull(this.currentIndex) ? this.defaultDailyDetail : this.dailyDetail[this.currentIndex]
        this.currentDaily = utils.clone(daily)
        $('#daily-modal').modal('show')
      }
    },
    remove: function () {
      let index = $(window.event.srcElement).parents('.data').attr('data-index')
      if (utils.isNull(index)) {
        index = this.currentIndex
      }
      if (utils.isNotNull(index)) {
        let self = this
        layer.confirm('是否确定删除索引位置位于 “' + (parseInt(index) + 1) + '” 的日程记录', {
          btn: ['确定', '取消']
        }, function () {
          layer.load(1)
          requestRemoveDailies(self.dailyDetail[index].id).then(data => {
            layer.closeAll()
            if (data.code === 200) {
              self.dailyDetail.splice(index, 1)
            } else {
              layer.closeAll()
            }
          })
          layer.closeAll()
        })
      }
    },
    saveDaily: function () {
      if ($.isNumeric(this.daily.score)) {
        this.daily.date = this.date
        layer.load(1)
        if (validator.isEmpty(this.daily.id)) {
          requestSaveDaily(this.daily).then(data => this.handleDailyReturnData(data))
        } else {
          requestUpdateDaily(this.daily).then(data => this.handleDailyReturnData(data))
        }
      } else {
        layer.alert('数据不能为空')
      }
    },
    handleDailyReturnData: function (data) {
      layer.closeAll()
      if (data.code === 200) {
        this.daily = data.data
        layer.alert('保存成功')
      } else {
        layer.alert(data.message)
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
      layer.load(1)
      requestGetDaily(this.date).then(data => {
        layer.closeAll()
        if (data.code === 200) {
          this.daily = data.data
          requestListDailies(data.data.id).then(data => {
            if (data.code === 200) {
              this.dailyDetail = data.data
            } else {
              layer.alert(data.message)
            }
          })
        } else {
          layer.alert(data.message)
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
    if (this.isMobile) {
      this.ths.shift()
      this.ths.pop()
    }
    setTimeout(() => {
      this.listDaily()
      this.isFirst = false
      // 修改父组件日期列表
      this.$parent.changeDate(-7, 0)
    }, 200)
  },
  updated: function () {
    $('[data-toggle="tooltip"]').tooltip()
  }
}
</script>

<style scoped>
  .h5-v-middle {
    padding-top: 10px;
  }
</style>
