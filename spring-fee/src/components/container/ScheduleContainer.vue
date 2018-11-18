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
          <div class="col-sm-12"><br/></div>
          <div class="col-sm-12">
            <ve-line :data="chartData"></ve-line>
          </div>
        </div>
        <br/>
      </div>
    </div>
    <div v-if="isMobile" class="col-12"><br/></div>
    <div class="col-sm-8 col-12">
      <router-view :date="date"></router-view>
    </div>
  </div>
</template>

<!--suppress JSCheckFunctionSignatures -->
<script>/* eslint-disable */
import utils from '../../assets/js/utils'
import dayjs from 'dayjs'

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
        columns: ['date', '统计'],
        rows: []
      }
    }
  },
  methods: {},
  mounted: function () {
    this.isMobile = utils.isMobile()
    let now = dayjs().format('YYYY-MM-DD')
    this.date = this.dateEnd = now
    this.dateStart = dayjs(now).add(-1, 'month').format('YYYY-MM-DD')
  },
  watch: {
    dateStart: function () {
      // for future
    },
    dateEnd: function () {
      this.date = this.dateEnd
    }
  },
  updated: function () {
    $('input[data-toggle="tooltip"]').tooltip()
  }
}
</script>

<style scoped>
</style>
