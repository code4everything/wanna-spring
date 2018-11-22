<template>
  <div class="row justify-content-center">
    <!--<div class="col-1 col-sm-4"></div>-->
    <div class="col-sm-4 col-10 rounded content-box text-center center-vertical bg-light"
         :style="{'padding': '2rem 3rem','margin-top':marginTop}">
      <router-view/>
    </div>
  </div>
</template>

<script>/* eslint-disable indent */
import utils from '../../assets/js/utils'

export default {
  name: 'SigninContainer',
  data () {
    return {
      isMobile: false,
      marginTop: '6rem'
    }
  },
  mounted: function () {
    this.isMobile = utils.isMobile()
    this.marginTop = this.isMobile ? '1rem' : '6rem'
  }
}
</script>

<style scoped>
  @import "../../assets/css/style.css";
</style>
