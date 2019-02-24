<template>
  <div class="row">
    <div @click="updateDaysLeft" class="col-10 col-sm-10 offset-1 offset-sm-1 bg-light rounded">
      <br/>
      <el-tooltip :content="tip" class="item" effect="dark" placement="top-start">
        <el-progress :color="color" :percentage="progress" :stroke-width="15" :text-inside="true"></el-progress>
      </el-tooltip>
      <br/>
    </div>
  </div>
</template>

<script>/* eslint-disable indent */
import dayjs from 'dayjs'

export default {
  name: 'DaysLeft',
  data () {
    return {
      progress: 66,
      color: 'green',
      tip: ''
    }
  },
  methods: {
    updateDaysLeft: function () {
      let now = dayjs()
      let start = now.startOf('year')
      let end = now.endOf('year')
      let day = 60 * 60 * 24
      let len = Number.parseInt((end.unix() - start.unix()) / day) + 1
      let progress = Number.parseInt((now.unix() - start.unix()) / day) + 1
      this.tip = '已过 ' + progress + ' 天'
      this.progress = Number.parseInt(progress * 100 / len)
    }
  },
  mounted () {
    this.updateDaysLeft()
  },
  watch: {
    progress: function () {
      this.color = this.progress < 50 ? '#3c7b24' : (this.progress > 80 ? '#d4706c' : '#915f24')
    }
  }
}
</script>

<style scoped>

</style>
