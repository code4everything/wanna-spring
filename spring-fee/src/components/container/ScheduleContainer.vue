<template>
  <div class="row">
    <div class="col-sm-4 col-12">
      <!--移动端-->
      <div v-if="isMobile" class="row">
        <div class="col-10 offset-1 rounded bg-light">
          <div class="row">
            <div class="col-10 offset-1">
              <br/>
              <input type="date" class="form-control" v-model="date" :placeholder="dateTip" :title="dateTip"
                     data-toggle="tooltip"/>
              <br/>
            </div>
          </div>
        </div>
      </div>
      <!--电脑端-->
      <div v-else>
        <div class="bg-light rounded row">
          <div class="col-sm-12"><br/></div>
          <div class="col-sm-10 offset-sm-1">
            <input type="date" class="form-control" v-model="dateStart" :placeholder="dateStartTip"
                   :title="dateStartTip" data-toggle="tooltip"/>
          </div>
          <div class="col-sm-12"><br/></div>
          <div class="col-sm-10 offset-sm-1">
            <input type="date" class="form-control" v-model="dateEnd" :placeholder="dateEndTip" :title="dateEndTip"
                   data-toggle="tooltip"/>
          </div>
          <div class="col-sm-12"><br/></div>
        </div>
        <br/>
        <div class="bg-light rounded row">
          <div class="col-sm-10 offset-sm-1 justify-content-center">
            <br/>
            <ul class="list-group text-center">
              <li class="list-group-item" v-for="(date,index) in dates" :key="index">{{date}}</li>
            </ul>
            <br/>
          </div>
        </div>
      </div>
    </div>
    <div v-if="isMobile" class="col-12"><br/></div>
    <div class="col-sm-8 col-12">
      <router-view></router-view>
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
      dateStartTip: '开始日期',
      dateEndTip: '结束日期',
      dates: []
    }
  },
  methods: {
    updateDateList: function () {
      if (this.dateStart <= this.dateEnd) {
        this.dates = []
        let now = this.dateStart
        this.dates.push(this.dateStart)
        while (now !== this.dateEnd) {
          now = dayjs(now).add(1, 'day').format('YYYY-MM-DD')
          this.dates.push(now)
        }
      }
    }
  },
  mounted: function () {
    this.isMobile = utils.isMobile()
    let now = dayjs().format('YYYY-MM-DD')
    this.date = this.dateEnd = this.dateStart = now
  },
  watch: {
    dateStart: function () {
      this.updateDateList()
    },
    dateEnd: function () {
      this.updateDateList()
    }
  },
  updated: function () {
    $('input[data-toggle="tooltip"]').tooltip()
  }
}
</script>

<style scoped>

</style>
