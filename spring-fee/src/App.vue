<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>/* eslint-disable indent */

export default {
  name: 'App',
  data () {
    return {
      path: {
        register: '/#/register',
        passwordReset: '/#/password/reset',
        login: '/#/login'
      },
      publicKey: '-----BEGIN PUBLIC KEY-----\n' +
        'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDe5H2RXDbyl5kgirNhJbZAvTSA\n' +
        '/5AqWINkn65/et7EM/7wr4ijDqYjrwW44bKILjTMLv1zgSp8CHhpA+5tRukKGKQt\n' +
        'uRaaFd5mvggBkTytGOdVGqqqZUylfgcKrykf3jQdkv5dm1pCRsh1G1wvSsD2SPW4\n' +
        'O4iXc5gGu4aPXTVpWQIDAQAB\n' +
        '-----END PUBLIC KEY-----\n'
    }
  }
}
</script>

<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
  }
</style>
