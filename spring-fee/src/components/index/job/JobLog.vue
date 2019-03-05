<template>
  <div>
    <el-table :data="jobs" ref="filterTable">
      <el-table-column :formatter="formatJobDate" align="center" label="日期"></el-table-column>
      <el-table-column :formatter="formatJobOnline" align="center" label="上班"></el-table-column>
      <el-table-column :formatter="formatJobOffline" align="center" label="下班"></el-table-column>
      <el-table-column :formatter="formatJobDuration" align="center" label="时长"></el-table-column>
      <el-table-column align="center" label="方式">
        <template slot-scope="scope">
          <el-tag :type="scope.row.workWay === '1' ? 'success' : 'warning'"
                  disable-transitions>{{scope.row.workWay==='1'?'工作':'加班'}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" label="状态">
        <template slot-scope="scope">
          <el-select default-first-option v-model="scope.row.status">
            <el-option :key="index" :label="state" :value="index"
                       v-for="(state,index) in statusList"></el-option>
          </el-select>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>/* eslint-disable indent */

export default {
  name: 'JobLog',
  data () {
    return {
      statusList: ['未处理', '已处理']
    }
  },
  props: ['jobs'],
  methods: {
    formatJobDate: function (job) {

    },
    formatJobOnline: function (job) {

    },
    formatJobOffline: function (job) {

    },
    formatJobDuration: function (job) {

    }
  }
}
</script>

<style scoped>

</style>
