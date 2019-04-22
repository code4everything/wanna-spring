<!--suppress HtmlFormInputWithoutLabel -->
<template>
  <div class="row">
    <div class="col-sm-5 col-12">
      <!--移动端-->
      <div class="row" v-if="isMobile">
        <div class="col-10 offset-1 rounded bg-light">
          <br/>
          <div class="row">
            <div class="col-10 offset-1">
              <el-date-picker :clearable="false" :editable="false" type="date" v-model="date"
                              value-format="yyyy-MM-dd"></el-date-picker>
            </div>
          </div>
          <br/>
        </div>
      </div>
      <!--电脑端-->
      <div v-else>
        <div class="bg-light rounded row">
          <div class="col-sm-12"><br/></div>
          <div class="col-sm-5 text-right">
            <h5 style="padding-top: 7px;">当前日期：</h5>
          </div>
          <div class="col-sm-7 text-left">
            <el-date-picker :clearable="false" :editable="false" class="w-75" type="date"
                            v-model="date" value-format="yyyy-MM-dd"></el-date-picker>
          </div>
          <div class="col-sm-12"><br/></div>
        </div>
        <br/>
        <div class="row bg-light rounded">
          <div class="col-sm-12"><br/></div>
          <div class="col-sm-5">
            <el-date-picker :clearable="false" :editable="false" class="w-100" type="date"
                            v-model="dateStart" value-format="yyyy-MM-dd"></el-date-picker>
          </div>
          <div class="col-sm-5">
            <el-date-picker :clearable="false" :editable="false" class="w-100" type="date"
                            v-model="dateEnd" value-format="yyyy-MM-dd"></el-date-picker>
          </div>
          <div class="col-sm-2">
            <button @click="dialogVisible=true" class="btn btn-outline-success"><i
              class="glyphicon glyphicon-fullscreen"></i></button>
          </div>
          <div class="col-sm-12"><br/></div>
          <div class="col-sm-12">
            <ve-line :data="chartData" :extend="extend"></ve-line>
          </div>
        </div>
        <br/>
        <div class="row bg-light rounded">
          <div class="col-sm-12"><br/></div>
          <div class="col-sm-10 offset-1 text-left">
            <span style="font-size: 18px;">最高得分：</span><span style="font-size: 24px;">{{max}}</span>
          </div>
          <div class="col-sm-10 offset-1 text-left">
            <span style="font-size: 18px;">最低得分：</span><span style="font-size: 24px;">{{min}}</span>
          </div>
          <div class="col-sm-10 offset-1 text-left">
            <span style="font-size: 18px;">平均得分：</span><span
            style="font-size: 24px;">{{Number(average).toFixed(2)}}</span>
          </div>
          <div class="col-sm-12"><br/></div>
        </div>
        <br/>
      </div>
    </div>
    <div class="col-12" v-if="isMobile"><br/></div>
    <div class="col-sm-7 col-12">
      <router-view :date="date"></router-view>
    </div>
    <el-dialog :fullscreen="true" :visible.sync="dialogVisible" v-if="!isMobile">
      <ve-line :data="fullChartData" :extend="extend"></ve-line>
    </el-dialog>
  </div>
</template>

<!--suppress JSCheckFunctionSignatures -->
<script>/* eslint-disable */
import utils from '../../assets/js/utils'
import dayjs from 'dayjs'
import app from '../../App'
import {requestListDaily, requestListTodoCount} from '../../api/api'

export default {
  name: 'ScheduleContainer',
  data () {
    return {
      isMobile: false,
      date: '',
      dateStart: '',
      dateEnd: '',
      dateTip: '日期',
      chartData: {
        columns: [],
        rows: []
      },
      isFirst: true,
      dialogVisible: false,
      fullChartData: {
        columns: [],
        rows: []
      },
      extend: {'xAxis.0.axisLabel.rotate': 45},
      min: 0,
      max: 0,
      average: 0.0
    }
  },
  methods: {
    getChartData: function () {
      if (!this.isMobile) {
        let href = '/' + window.location.hash
        if (href === app.data().path.daily) {
          requestListDaily(this.dateStart, this.dateEnd).then(data => this.handleData(data, '每日得分'))
        } else if (href === app.data().path.todo) {
          requestListTodoCount(this.dateStart, this.dateEnd).then(data => this.handleData(data, '每日代办数量'))
        }
        this.isFirst = false
      }
    },
    handleData: function (data, col) {
      this.chartData.rows = []
      this.chartData.columns = ['date', col]
      this.fullChartData.rows = []
      this.fullChartData.columns = ['date', col]
      this.min = 10
      this.max = 0
      let sum = 0.0
      if (data.ok) {
        data.data.forEach(item => {
          let ele = {'date': dayjs(item.date).format('MM-DD')}
          ele[col] = item.score
          this.chartData.rows.push(ele)
          ele = {'date': item.date}
          ele[col] = item.score
          this.fullChartData.rows.push(ele)
          // 统计
          if (item.score < this.min) {
            this.min = item.score
          }
          if (item.score > this.max) {
            this.max = item.score
          }
          sum += item.score
        })
        this.average = sum / data.data.length
      } else {
        utils.showError(this, data.msg)
      }
    }
  },
  mounted: function () {
    this.isMobile = utils.isMobile()
    let now = dayjs().format('YYYY-MM-DD')
    this.date = this.dateEnd = now
    this.dateStart = dayjs(now).add(-1, 'month').format('YYYY-MM-DD')
  },
  watch: {
    dateStart: function () {
      if (!this.isFirst) {
        this.getChartData()
      }
    },
    dateEnd: function () {
      if (!this.isFirst) {
        this.getChartData()
      }
    }
  }
}
</script>

<style scoped>
</style>
