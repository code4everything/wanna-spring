<template>
  <div></div>
</template>

<script>/* eslint-disable */

export default {
  name: 'Todo',
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
