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
            <el-button @click="punchJob" type="success"><i class="glyphicon glyphicon-floppy-open"></i>{{workTip}}
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

export default {
  name: 'Job',
  data () {
    return {
      datetime: '',
      companies: [],
      myCompany: '',
      myWay: '上班',
      workTip: '',
      ways: ['上班', '加班'],
      weekday: ['日', '一', '二', '三', '四', '五', '六']
    }
  },
  methods: {
    punchJob: function () {
      console.info('punch')
    }
  },
  mounted () {
    setInterval(() => {
      this.datetime = `${dayjs().format('YYYY-MM-DD HH:mm:ss')} 星期${this.weekday[dayjs().format('d')]}`
    }, 1000)
  }
}
</script>

<style scoped>
  @import "../../assets/css/style.css";
</style>
