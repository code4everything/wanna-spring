<!-- 暂时不考虑兼容移动端 -->
<template>
  <div>
    <div class="row">
      <div class="bg-light rounded col-10 offset-1 offset-sm-0 col-sm-12">
        <br/>
        <div class="row">
          <div class="col-sm-3 text-right h5-v-middle">
            <h6>{{datetime}}</h6>
          </div>
          <div class="col-sm-3 text-left">
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
          <div class="col-sm-4">
            <div class="row">
              <div class="col-sm-4">
                <el-button :type="hasJob?'danger':'success'" @click="punchJob" class="w-100"><i
                  class="glyphicon glyphicon-time"></i>{{hasJob?' 下班':' 上班'}}
                </el-button>
              </div>
              <div class="col-sm-4">
                <el-button :disabled="!hasJob" @click="dialogVisible=true" class="w-100" type="primary"><i
                  class="glyphicon glyphicon-edit"></i> 写日志
                </el-button>
              </div>
              <div class="col-sm-4">
                <el-button @click="resetCurrentJob" class="w-100" type="warning"><i
                  class="glyphicon glyphicon-record"></i> 新增
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
      <!--工作日志-->
      <div class="col-sm-8" style="padding-right: 40px;">
        <br/>
        <div class="row">
          <div class="col-sm-12 rounded bg-light">
            <br/>
            <el-tabs v-model="currentTab">
              <el-tab-pane label="工作记录" name="all">
                <div class="row">
                  <div class="col-sm-12 text-left">
                    <el-select default-first-option v-model="companyFilter">
                      <el-option :key="index" :label="company" :value="company"
                                 v-for="(company,index) in companies"></el-option>
                    </el-select>
                    &emsp;
                    <el-button @click="listLog" round type="primary"><i class="glyphicon glyphicon-refresh"></i> 刷新
                    </el-button>
                  </div>
                </div>
                <br/>
                <div class="row">
                  <div class="col-sm-12">
                    <job-log :jobs="jobs1" :offset="currPage1" :size="pageSize1" :total="totals1"></job-log>
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
                    &emsp;
                    <el-button @click="listLog" round type="primary"><i class="glyphicon glyphicon-refresh"></i> 刷新
                    </el-button>
                  </div>
                </div>
                <br/>
                <div class="row">
                  <div class="col-sm-12">
                    <job-log :jobs="jobs2" :offset="currPage2" :size="pageSize2" :total="totals2"></job-log>
                  </div>
                </div>
              </el-tab-pane>
            </el-tabs>
            <br/>
          </div>
        </div>
      </div>
      <!--日志详情-->
      <div class="col-sm-4" style="padding-left: 40px;">
        <br/>
        <div class="row">
          <div class="col-sm-12 rounded bg-light">
            <br/>
            <div class="row">
              <div class="col-sm-12">
                <el-date-picker class="w-100" placeholder="上班时间" type="datetime"
                                v-model="currentJob.workTimeStart"></el-date-picker>
              </div>
            </div>
            <br/>
            <div class="row">
              <div class="col-sm-12">
                <el-date-picker class="w-100" placeholder="下班时间" type="datetime"
                                v-model="currentJob.workTimeEnd"></el-date-picker>
              </div>
            </div>
            <br/>
            <div class="row">
              <div class="col-sm-12">
                <el-select allow-create class="w-100" filterable v-model="currentJob.company">
                  <el-option :key="index" :label="company" :value="company"
                             v-for="(company,index) in companies"></el-option>
                </el-select>
              </div>
            </div>
            <br/>
            <div class="row">
              <div class="col-sm-12">
                <el-select class="w-100" default-first-option v-model="currentJob.workWay">
                  <el-option :key="index" :label="way" :value="index+1"
                             v-for="(way,index) in ways"></el-option>
                </el-select>
              </div>
            </div>
            <br/>
            <div class="row">
              <div class="col-sm-12">
                <el-input placeholder="工作日志" rows="5" type="textarea" v-model="currentJob.workDiary"></el-input>
              </div>
            </div>
            <br/>
            <div class="row">
              <div class="col-sm-12">
                <el-button @click="saveJob" class="w-100" type="success">{{saveTip}}</el-button>
              </div>
            </div>
            <br/>
          </div>
        </div>
      </div>
    </div>
    <br/>
    <!--日志弹窗-->
    <el-dialog :visible.sync="dialogVisible" title="工作日志">
      <el-input rows="5" type="textarea" v-model="todayJob.workDiary"></el-input>
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
  requestListOvertime,
  requestListWorked,
  requestSaveJob,
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
      todayJob: {workDiary: ''},
      lastDayOfWeek: -1,
      dialogVisible: false,
      currentJob: {
        id: '',
        workTimeStart: '',
        workTimeEnd: '',
        company: '',
        workWay: 1,
        workDiary: ''
      },
      currentTab: 'overtime',
      status: '',
      companyFilter: '',
      statusList: ['未处理', '已处理'],
      currPage1: 1,
      currPage2: 1,
      pageSize1: 30,
      pageSize2: 30,
      jobs1: [],
      jobs2: [],
      totals1: 0,
      totals2: 0,
      saveTip: '添  加'
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
          if (data.ok) {
            this.todayJob = data.data
            utils.showSuccess(this, '打卡成功')
          } else {
            utils.showError(this, data.msg)
          }
        })
      } else {
        // 上班打卡
        requestStartWorking(this.myWay, this.myCompany).then(data => {
          if (data.ok) {
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
      requestJobOfToday().then(data => {
        if (data.ok) {
          this.hasJob = true
          this.todayJob = data.data
        } else {
          this.hasJob = false
        }
      })
    },
    writeDiary: function () {
      requestWriteDiary(this.todayJob.id, this.todayJob.workDiary).then(data => {
        if (data.ok) {
          utils.showSuccess(this, data.msg)
        } else {
          utils.showError(this.data.msg)
        }
      })
      this.dialogVisible = false
    },
    listLog: function () {
      if (this.currentTab === 'all') {
        this.listLog4C(this.currPage1, this.pageSize1)
      } else {
        this.listLog4C(this.currPage2, this.pageSize2)
      }
    },
    listLog4C: function (currPage, size) {
      if (this.currentTab === 'all') {
        requestListWorked(this.companyFilter, currPage - 1, size).then(data => this.handleJobData(data))
      } else {
        requestListOvertime(this.status, currPage - 1, size).then(data => this.handleJobData(data))
      }
    },
    handleJobData: function (data) {
      if (data.ok) {
        if (this.currentTab === 'all') {
          this.jobs1 = data.data.content
          // noinspection JSUnresolvedVariable
          this.totals1 = data.data.totalElements
        } else {
          this.jobs2 = data.data.content
          // noinspection JSUnresolvedVariable
          this.totals2 = data.data.totalElements
        }
      } else {
        utils.showError(this, data.msg)
      }
    },
    resetCurrentJob: function () {
      this.currentJob = {
        id: '',
        workTimeStart: '',
        workTimeEnd: '',
        company: this.myCompany,
        workWay: 1,
        workDiary: ''
      }
      this.saveTip = '添  加'
    },
    setCurrentJob: function (job) {
      this.currentJob = {
        id: job.id,
        workTimeStart: new Date(job.workTimeStart),
        workTimeEnd: new Date(job.workTimeEnd),
        company: job.company,
        workWay: Number.parseInt(job.workWay),
        workDiary: job.workDiary
      }
      this.saveTip = '更  新'
    },
    saveJob: function () {
      let job = this.currentJob
      requestSaveJob(job.id, job.workTimeStart.getTime(), job.workTimeEnd.getTime(), job.company, job.workWay.toString(), job.workDiary).then(data => {
        if (data.ok) {
          this.currentJob.id = data.data.id
          this.saveTip = '更新'
          utils.showSuccess(this, data.msg)
        } else {
          utils.showError(this, data.msg)
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
      if (idx !== this.lastDayOfWeek) {
        // 刷新打卡记录
        this.lastDayOfWeek = idx
        this.refreshJob()
      }
    }, 1000)
    // 列出公司
    requestCompanies().then(data => {
      if (data.ok) {
        this.companies = data.data
        this.myCompany = this.companies[this.companies.length - 1]
      } else {
        utils.showError(this, data.msg)
      }
    })
  },
  watch: {
    status: function () {
      this.listLog()
    },
    companyFilter: function () {
      this.listLog()
    }
  }
}
</script>

<style scoped>
  @import "../../assets/css/style.css";
</style>
