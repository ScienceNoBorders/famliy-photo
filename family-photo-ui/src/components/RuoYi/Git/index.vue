<template>
  <div>
    <svg-icon icon-class="github-line" @click="goto" />
  </div>
</template>

<script>
export default {
  name: 'RuoYiGit',
  data() {
    return {
      url: 'https://gitee.com/big-hedgehog/aidex-sharp'
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>
