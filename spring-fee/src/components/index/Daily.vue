<template>
  <div></div>
</template>

<script>/* eslint-disable */

export default {
  name: 'Daily',
  data () {
    return {}
  },
  props: ['date'],
  methods: {},
  watch: {
    date: function (newValue) {
      console.info('call child method success, value: ' + newValue)
    }
  }
}
</script>

<style scoped>

</style>
