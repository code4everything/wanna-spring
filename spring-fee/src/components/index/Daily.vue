<template>
  <div class="row">
    <!--日程记录-->
    <div class="rounded bg-light col-10 offset-1 col-sm-11 offset-sm-1">
      <br/>
      <div class="row">
        <div class="col-sm-2 col-4 text-right"><h5 class="h5-v-middle">{{weatherTip}}：</h5></div>
        <div class="col-sm-3 col-7">
          <input type="text" class="form-control" v-model="daily.weather" :placeholder="weatherTip"/>
        </div>
        <div v-if="isMobile" class="col-12"><br/></div>
        <div class="col-sm-2 col-4 text-right"><h5 class="h5-v-middle">{{scoreTip}}：</h5></div>
        <div class="col-sm-3 col-7">
          <input type="number" class="form-control" v-model="daily.score" :placeholder="scoreTip"/>
        </div>
      </div>
      <br/>
      <div class="row">
        <div class="col-sm-2 col-4 text-right"><h5 class="h5-v-middle">{{contentTip}}：</h5></div>
        <div class="col-sm-8 col-7">
          <textarea class="form-control" v-model="daily.content" :placeholder="contentTip" rows="4"></textarea>
        </div>
      </div>
      <br/>
      <div class="row">
        <div class="col-sm-8 offset-sm-2 offset-1 col-10">
          <button class="btn btn-success btn-block">{{saveTip}}</button>
        </div>
      </div>
      <br/>
    </div>
    <div class="col-12 col-sm-12"><br/></div>
    <!--日程详细记录-->
    <div class="rounded bg-light col-10 offset-1 col-sm-11 offset-sm-1"></div>
  </div>
</template>

<script>/* eslint-disable */
import utils from '../../assets/js/utils'

export default {
  name: 'Daily',
  data () {
    return {
      isMobile: false,
      saveTip: '保存',
      contentTip: '日志',
      scoreTip: '分数',
      weatherTip: '天气',
      defaultDaily: {content: '', createTime: '', id: '', score: '', weather: ''},
      daily: {}
    }
  },
  props: ['date'],
  methods: {},
  watch: {
    date: function () {
      this.daily = JSON.parse(JSON.stringify(this.defaultDaily))
    }
  },
  mounted: function () {
    this.isMobile = utils.isMobile()
  }
}
</script>

<style scoped>
  .h5-v-middle {
    padding-top: 10px;
  }
</style>
