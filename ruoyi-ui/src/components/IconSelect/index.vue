<!-- @author zhengjie -->
<template>
  <div class="icon-body">
    <el-input v-model="name" style="position: relative;" clearable placeholder="请输入图标名称" @clear="filterIcons" @input.native="filterIcons">
      <i slot="suffix" class="el-icon-search el-input__icon" />
    </el-input>
    <el-tabs type="border-card">
      <el-tab-pane  :label="classify.classifyName" v-for="classify of iconList">
        <div class="icon-list">
        <div v-for="item of classify.iconList" :key="item"  @click="selectedIcon(item)">
          <svg-icon :icon-class="item" style="height: 30px;width: 16px;" />
          <span>{{ item }}</span>
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import icons from './requireIcons'
export default {
  name: 'IconSelect',
  props:{
    notExitisLabel:['办公']
  },
  data() {
    return {
      name: '',
      iconList: icons
    }
  },
  methods: {
    filterIcons() {
      this.iconList = JSON.parse(JSON.stringify(icons))
      if (this.name) {
        let index = 0
        const that = this
        this.iconList.forEach(function(icons){
          that.iconList[index].iconList = icons.iconList.filter(item => item.includes(that.name))
          index++;
         }
        )
      }
    },
    selectedIcon(name) {
      this.$emit('selected', name)
      document.body.click()
    },
    reset() {
      this.name = ''
      this.iconList = icons
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .icon-body {
    width: 100%;
    padding: 10px;
    .icon-list {
      height: 200px;
      overflow-y: scroll;
      div {
        height: 30px;
        line-height: 30px;
        margin-bottom: -5px;
        cursor: pointer;
        width: 33%;
        float: left;
      }
      span {
        display: inline-block;
        vertical-align: -0.15em;
        fill: currentColor;
        overflow: hidden;
      }
    }
  }
</style>
