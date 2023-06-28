<template>
  <div>
    <svg-icon icon-class="question-line" @click="goto" />
    <i class="ri-survey-line"></i>
  </div>
</template>

<script>
export default {
  name: 'RuoYiDoc',
  data() {
    return {
      url: 'http://aidex.vip:8083/'
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>
