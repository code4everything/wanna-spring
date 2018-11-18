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
              <el-date-picker v-model="date" type="date" value-format="yyyy-MM-dd"></el-date-picker>
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
            <el-date-picker v-model="dateEnd" type="date" value-format="yyyy-MM-dd"></el-date-picker>
          </div>
          <div class="col-sm-12"><br/></div>
        </div>
        <br/>
        <div class="bg-light rounded row">
          <div class="col-sm-10 offset-sm-1 justify-content-center">
            <br/>
            <div class="list-group text-center">
              <a @click="dateChange" class="list-group-item list-group-item-action border-0"
                 v-for="(date,index) in dates" :key="index">{{date}}</a>
            </div>
            <br/>
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
    },
    dateChange: function () {
      this.date = $(window.event.srcElement).text()
      this.changeActiveDate()
    },
    changeDate: function (leftOffset, rightOffset) {
      if (!this.isMobile) {
        this.dateStart = dayjs().add(leftOffset, 'day').format('YYYY-MM-DD')
        this.dateEnd = dayjs().add(rightOffset, 'day').format('YYYY-MM-DD')
        setTimeout(() => this.changeActiveDate(), 500)
      }
    },
    changeActiveDate: function () {
      if (!this.isMobile) {
        let className = 'list-group-item-primary'
        let self = this
        $('.list-group-item').each(function () {
          if ($(this).text() === self.date) {
            $(this).addClass(className)
          } else {
            $(this).removeClass(className)
          }
        })
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
