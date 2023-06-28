<template>
  <div class="icons-container">
    <el-tabs type="border-card">
      <el-tab-pane :label="classify.classifyName" v-for="classify of iconList">
        <el-input v-model="name" style="position: relative;padding: 16px;" clearable placeholder="请输入图标名称" @clear="filterIcons" @input.native="filterIcons">
          <i slot="suffix" class="el-icon-search el-input__icon" />
        </el-input>
        <div class="grid">
          <div v-for="item of classify.iconList" :key="item" @click="handleClipboard(generateIconCode(item),$event)">
            <el-tooltip disabled placement="top">
              <div slot="content">
                {{ generateIconCode(item) }}
              </div>
              <div class="icon-item">
                <svg-icon :icon-class="item" class-name="disabled" />
                <span>{{ item }}</span>
              </div>
            </el-tooltip>
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane label="Element-UI Icons">
        <div class="grid">
          <div v-for="item of elementIcons" :key="item" @click="handleClipboard(generateElementIconCode(item),$event)">
            <el-tooltip placement="top">
              <div slot="content">
                {{ generateElementIconCode(item) }}
              </div>
              <div class="icon-item">
                <i :class="'el-icon-' + item" />
                <span>{{ item }}</span>
              </div>
            </el-tooltip>
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import icons from './svg-icons'
import elementIcons from './element-icons'
import Clipboard from 'clipboard'

export default {
  name: 'Icons',
  data() {
    return {
      iconList:icons,
      elementIcons
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
    generateIconCode(symbol) {
      return `<svg-icon icon-class="${symbol}" />`
    },
    generateElementIconCode(symbol) {
      return `<i class="el-icon-${symbol}" />`
    },
    handleClipboard(text, event) {
      clipboard(text, event)
    },
    handleClipboard(text, event) {
      const msg = '复制成功：' + text
      const clipboard = new Clipboard(event.target, {
        text: () => text
      })
      clipboard.on('success', () => {
        this.$message({
          message: msg,
          type: 'success',
          duration: 1500
        })
        clipboard.destroy()
      })
      clipboard.on('error', () => {
        this.$message({
          message: '复制',
          type: 'error'
        })
        clipboard.destroy()
      })
      clipboard.onClick(event)
    }
  }
}
</script>

<style lang="scss" scoped>

.icons-container {

  margin: 16px;
  overflow: hidden;

  .grid {
    position: relative;
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(120px, 1fr));
  }

  .icon-item {
    margin: 20px;
    height: 85px;
    text-align: center;
    width: 100px;
    float: left;
    font-size: 30px;
    color: #24292e;
    cursor: pointer;
  }

  span {
    display: block;
    font-size: 16px;
    margin-top: 10px;
  }

  .disabled {
    pointer-events: none;
  }
.grid{
    border-top: 1px solid #eee;
  }
}
.icons-container span{
    font-size: 12px!important;
    color:#99a9bf ;
  }
  .icons-container svg{
    font-size: 24px!important;
    color:#606266 ;
  }
/* .icons-container .icon-item:hover svg,
.icons-container .icon-item:hover span,
{
  color:
} */
  .icon-item{
    padding-top: 8px;
    margin: 0!important;
       border-right: 1px solid #eee;
       border-bottom: 1px solid #eee;
       margin-right: -1px;
       margin-bottom: -1px;
           width: 100%!important;
           text-align: center;
           height: 120px;
  }


</style>
