<template>
  <div class="row">
    <div class="col-12 col-sm-12 bg-light rounded">
      <br/>
      <el-progress :text-inside="true" :stroke-width="18" :percentage="progress" :color="color"></el-progress>
      <br/>
    </div>
  </div>
</template>

<script>/* eslint-disable indent */
export default {
  name: 'DaysLeft',
  data () {
    return {
      progress: 66,
      color: 'green'
    }
  }
}
</script>

<style scoped>

</style>
