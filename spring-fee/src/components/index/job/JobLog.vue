<template>
  <div>
    <el-table :data="jobs" @row-click="selectRow" ref="filterTable">
      <el-table-column :formatter="formatJobDate" :resizable="true" align="center" label="日期"
                       min-width="150px"></el-table-column>
      <el-table-column :formatter="formatJobOnline" align="center" label="上班"></el-table-column>
      <el-table-column :formatter="formatJobOffline" align="center" label="下班"></el-table-column>
      <el-table-column :formatter="formatJobDuration" align="center" label="时长"></el-table-column>
      <el-table-column align="center" label="状态">
        <template slot-scope="scope">
          <el-select :disabled="scope.row.workWay==='1'" @change="updateStatus(scope.row)" v-model="scope.row.status">
            <el-option :key="index" :label="state" :value="index.toString()"
                       v-for="(state,index) in statusList"></el-option>
          </el-select>
        </template>
      </el-table-column>
    </el-table>
    <br/>
    <el-pagination :current-page="currPage" :page-size="pageSize" :page-sizes="[30, 50, 100, 200]"
                   :total="total" @current-change="pageChange"
                   @size-change="sizeChange" layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
  </div>
</template>

<script>/* eslint-disable indent */
import dayjs from 'dayjs'
import {requestUpdateJobStatus} from '../../../api/api'
import utils from '../../../assets/js/utils'

// noinspection JSUnresolvedVariable
export default {
  name: 'JobLog',
  data () {
    return {
      statusList: ['未处理', '已处理'],
      weekday: ['日', '一', '二', '三', '四', '五', '六'],
      currPage: 1,
      pageSize: 30
    }
  },
  props: ['jobs', 'total', 'size', 'offset'],
  methods: {
    formatJobDate: function (job) {
      // noinspection JSUnresolvedVariable
      return `${dayjs(job.workTimeStart).format('YYYY-MM-DD')} 星期${this.weekday[dayjs(job.workTimeStart).format('d')]}`
    },
    formatJobOnline: function (job) {
      // noinspection JSUnresolvedVariable
      return dayjs(job.workTimeStart).format('HH:mm:ss')
    },
    formatJobOffline: function (job) {
      // noinspection JSUnresolvedVariable
      return dayjs(job.workTimeEnd).format('HH:mm:ss')
    },
    formatJobDuration: function (job) {
      // noinspection JSUnresolvedVariable
      let span = (job.workTimeEnd - job.workTimeStart) / 1000 / 60
      return `${Number.parseInt(span / 60)} 小时 ${Number.parseInt(span % 60)} 分`
    },
    pageChange: function (page) {
      this.currPage = page
      this.$parent.$parent.$parent.listLog4C(this.currPage, this.pageSize)
    },
    sizeChange: function (size) {
      this.pageSize = size
      this.$parent.$parent.$parent.listLog4C(this.currPage, this.pageSize)
    },
    updateStatus: function (job) {
      requestUpdateJobStatus(job.id, job.status).then(data => {
        if (data.error) {
          utils.showError(this, data.msg)
        }
      })
    },
    selectRow: function (job) {
      this.$parent.$parent.$parent.setCurrentJob(job)
    }
  },
  mounted: function () {
    this.currPage = this.offset
    this.pageSize = this.size
  }
}
</script>

<style scoped>

</style>
