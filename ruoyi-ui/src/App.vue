<template>
  <div id="app"  :class="size">
    <router-view @reloadSize="reloadSize"/>
  </div>
</template>

<script>
import store from '@/store'
export default  {
  name:  'App',
  data() {
    return {
      size: store.getters.size,
    }
   },
   methods:{
     reloadSize(newSize){
       this.size = newSize;
     }
   },
    metaInfo() {
        return {
            title: this.$store.state.settings.dynamicTitle && this.$store.state.settings.title,
            titleTemplate: title => {
                return title ? `${title} - ${process.env.VUE_APP_TITLE}` : process.env.VUE_APP_TITLE
            }
        }
    }
}
</script>
