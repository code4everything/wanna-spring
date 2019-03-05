<template>
  <div>
    <div class="row">
      <div class="bg-light rounded col-10 offset-1 offset-sm-0 col-sm-12">
        <br/>
        <div class="row">
          <div class="col-sm-3 text-right h5-v-middle">
            <h5>{{datetime}}</h5>
          </div>
          <div class="col-sm-4 text-left">
            <el-select allow-create class="w-100" default-first-option filterable v-model="myCompany">
              <el-option :key="index" :label="company" :value="company"
                         v-for="(company,index) in companies"></el-option>
            </el-select>
          </div>
          <div class="col-sm-2 text-left">
            <el-select class="w-100" default-first-option v-model="myWay">
              <el-option :key="index" :label="way" :value="way"
                         v-for="(way,index) in ways"></el-option>
            </el-select>
          </div>
          <div class="col-sm-3">
            <el-button :type="hasJob?'danger':'success'" @click="punchJob"><i class="glyphicon glyphicon-time"></i>
              {{hasJob?'下班打卡':'上班打卡'}}
            </el-button>
          </div>
        </div>
        <br/>
      </div>
    </div>
  </div>
</template>

<script>/* eslint-disable indent */
import dayjs from 'dayjs'
import {requestJobOfToday} from '../../api/api'

export default {
  name: 'Job',
  data () {
    return {
      datetime: '',
      companies: [],
      myCompany: '',
      myWay: '上班',
      ways: ['上班', '加班'],
      weekday: ['日', '一', '二', '三', '四', '五', '六'],
      hasJob: false,
      currentJob: {},
      jobRefreshed: false,
      lastDayOfWeek: -1
    }
  },
  methods: {
    punchJob: function () {
      this.workUp = !this.workUp
    },
    refreshJob: function () {
      this.jobRefreshed = true
      requestJobOfToday().then(data => {
        if (data.code === 200) {
          this.hasJob = true
          this.currentJob = data.data
        } else {
          this.hasJob = false
        }
      })
    }
  },
  mounted () {
    setInterval(() => {
      // 刷新时间
      let now = new Date()
      let idx = dayjs(now).format('d')
      this.datetime = `${dayjs(now).format('YYYY-MM-DD HH:mm:ss')} 星期${this.weekday[idx]}`
      if (!this.jobRefreshed && idx > this.lastDayOfWeek) {
        // 刷新打卡记录
        this.lastDayOfWeek = idx
        this.refreshJob()
      }
    }, 1000)
  }
}
</script>

<style scoped>
  @import "../../assets/css/style.css";
</style>
