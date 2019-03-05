<!-- 暂时不考虑兼容移动端 -->
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
              <el-option :key="index" :label="way" :value="index+1"
                         v-for="(way,index) in ways"></el-option>
            </el-select>
          </div>
          <div class="col-sm-3">
            <div class="row">
              <div class="col-sm-6">
                <el-button :type="hasJob?'danger':'success'" @click="punchJob"><i class="glyphicon glyphicon-time"></i>
                  {{hasJob?'下班打卡':'上班打卡'}}
                </el-button>
              </div>
              <div class="col-sm-6">
                <el-button :disabled="!hasJob" @click="dialogVisible=true" type="primary"><i
                  class="glyphicon glyphicon-edit"></i> 写日志
                </el-button>
              </div>
            </div>
          </div>
        </div>
        <br/>
      </div>
    </div>
    <br/>
    <div class="row">
      <div class="col-sm-8 bg-light rounded">
        <br/>
        <el-tabs @tab-click="handleTabClick" v-model="currentTab">
          <el-tab-pane label="工作记录" name="all">
            <div class="row">
              <div class="col-sm-12 text-left">
                <el-select default-first-option v-model="companyFilter">
                  <el-option :key="index" :label="company" :value="company"
                             v-for="(company,index) in companies"></el-option>
                </el-select>
              </div>
            </div>
            <br/>
            <div class="row">
              <div class="col-sm-12">
                <job-log></job-log>
              </div>
            </div>
          </el-tab-pane>
          <el-tab-pane label="加班记录" name="overtime">
            <div class="row">
              <div class="col-sm-12 text-left">
                <el-select default-first-option v-model="status">
                  <el-option :key="index" :label="state" :value="index"
                             v-for="(state,index) in statusList"></el-option>
                </el-select>
              </div>
            </div>
            <br/>
            <div class="row">
              <div class="col-sm-12">
                <job-log></job-log>
              </div>
            </div>
          </el-tab-pane>
        </el-tabs>
        <br/>
      </div>
    </div>
    <!--日志弹窗-->
    <el-dialog :visible.sync="dialogVisible" title="工作日志">
      <el-input rows="5" type="textarea" v-model="todayJob.diary"></el-input>
      <div class="dialog-footer" slot="footer">
        <el-button @click="dialogVisible = false">关闭</el-button>
        <el-button @click="writeDiary" type="primary">保存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>/* eslint-disable indent */
import dayjs from 'dayjs'
import {
  requestCompanies,
  requestFinishWork,
  requestJobOfToday,
  requestStartWorking,
  requestWriteDiary
} from '../../api/api'
import utils from '../../assets/js/utils'
import JobLog from './job/JobLog'

export default {
  name: 'Job',
  components: {JobLog},
  data () {
    return {
      datetime: '',
      companies: [],
      myCompany: '',
      myWay: 1,
      ways: ['工作', '加班'],
      weekday: ['日', '一', '二', '三', '四', '五', '六'],
      hasJob: false,
      todayJob: {diary: ''},
      jobRefreshed: false,
      lastDayOfWeek: -1,
      dialogVisible: false,
      currentJob: {},
      currentTab: 'overtime',
      status: '',
      companyFilter: '',
      statusList: ['未处理', '已处理']
    }
  },
  methods: {
    punchJob: function () {
      if (!this.companies.includes(this.myCompany)) {
        this.companies.push(this.myCompany)
      }
      if (this.hasJob) {
        // 下班打卡
        requestFinishWork(this.todayJob.id, this.myWay, this.myCompany).then(data => {
          if (data.code === 200) {
            this.todayJob = data.data
            utils.showSuccess(this, '打卡成功')
          } else {
            utils.showError(this, data.msg)
          }
        })
      } else {
        // 上班打卡
        requestStartWorking(this.myWay, this.myCompany).then(data => {
          if (data.code === 200) {
            this.hasJob = true
            this.todayJob = data.data
            utils.showSuccess(this, '打卡成功')
          } else {
            utils.showError(this, data.msg)
          }
        })
      }
    },
    refreshJob: function () {
      this.jobRefreshed = true
      requestJobOfToday().then(data => {
        if (data.code === 200) {
          this.hasJob = true
          this.todayJob = data.data
        } else {
          this.hasJob = false
        }
      })
    },
    writeDiary: function () {
      requestWriteDiary(this.todayJob.id, this.todayJob.diary).then(data => {
        if (data.code === 200) {
          utils.showSuccess(this, data.msg)
        } else {
          utils.showError(this.data.msg)
        }
      })
      this.dialogVisible = false
    },
    handleTabClick: function () {
      console.info('tab changed')
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
    // 列出公司
    requestCompanies().then(data => {
      if (data.code === 200) {
        this.companies = data.data
        this.myCompany = this.companies[this.companies.length - 1]
      } else {
        utils.showError(this, data.msg)
      }
    })
  }
}
</script>

<style scoped>
  @import "../../assets/css/style.css";
</style>
