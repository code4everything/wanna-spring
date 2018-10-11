<template>
  <div class="row">
    <div class="col-1 col-sm-4"></div>
    <div class="col-sm-4 col-10 rounded content-box text-center center-vertical bg-light"
         style="padding: 2rem 3rem;margin-top: 6rem;">
      <router-view/>
    </div>
  </div>
</template>

<script>/* eslint-disable indent */

export default {
  name: 'SigninContainer'
}
</script>

<style scoped>
  @import "../../assets/css/style.css";
</style>
