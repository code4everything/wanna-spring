<!--suppress HtmlFormInputWithoutLabel -->
<template>
  <div class="row">
    <div class="col-sm-4 col-12">
      <!--移动端-->
      <div v-if="isMobile" class="row">
        <div class="col-10 offset-1 rounded bg-light">
          <br/>
          <div class="row">
            <div class="col-10 offset-1">
              <el-date-picker :editable="false" :clearable="false" v-model="date" type="date"
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
          <div class="col-sm-10 offset-sm-1">
            <el-date-picker v-model="dateStart" type="date" value-format="yyyy-MM-dd"></el-date-picker>
          </div>
          <div class="col-sm-12"><br/></div>
          <div class="col-sm-10 offset-sm-1">
            <el-date-picker :editable="false" :clearable="false" v-model="dateEnd" type="date"
                            value-format="yyyy-MM-dd"></el-date-picker>
          </div>
          <div class="col-sm-12"><br/></div>
        </div>
        <br/>
        <div class="row bg-light rounded">
          <div class="col-sm-12 text-right"><br/>
            <button class="btn btn-outline-success" @click="dialogVisible=true"><i
              class="glyphicon glyphicon-fullscreen"></i></button>
          </div>
          <div class="col-sm-12">
            <ve-line :data="chartData" :extend="{'xAxis.0.axisLabel.rotate': 45}"></ve-line>
          </div>
        </div>
        <br/>
      </div>
    </div>
    <div v-if="isMobile" class="col-12"><br/></div>
    <div class="col-sm-8 col-12">
      <router-view :date="date"></router-view>
    </div>
    <el-dialog v-if="!isMobile" :visible.sync="dialogVisible" :fullscreen="true">
      <ve-line :data="fullChartData"></ve-line>
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
    }
  },
  methods: {
    getChartData: function () {
      let href = '/' + window.location.hash
      if (href === app.data().path.daily) {
        requestListDaily(this.dateStart, this.dateEnd).then(data => this.handleData(data, '每日得分'))
      } else if (href === app.data().path.todo) {
        requestListTodoCount(this.dateStart, this.dateEnd).then(data => this.handleData(data, '每日代办数量'))
      }
      this.isFirst = false
    },
    handleData: function (data, col) {
      this.chartData.rows = []
      this.chartData.columns = ['date', col]
      this.fullChartData.rows = []
      this.fullChartData.columns = ['date', col]
      if (data.code === 200) {
        data.data.forEach(item => {
          let ele = {'date': dayjs(item.date).format('MM-DD')}
          ele[col] = item.score
          this.chartData.rows.push(ele)
          ele = {'date': item.date}
          ele[col] = item.score
          this.fullChartData.rows.push(ele)
        })
      } else {
        console.error(data.msg)
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
      this.date = this.dateEnd
      if (!this.isFirst) {
        this.getChartData()
      }
    }
  },
  updated: function () {
    $('input[data-toggle="tooltip"]').tooltip()
  }
}
</script>

<style scoped>
</style>
