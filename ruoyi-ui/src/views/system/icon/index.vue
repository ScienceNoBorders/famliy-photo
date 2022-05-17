<template>
 <div id="main" class="scrollbar">
   <i :class="el-icon-loading"></i>
   ddddddddddddddddd
  </div>
</template>
<script>
  import iconList from '@/utils/generator/icon.json'
  const originList = iconList.map(name => `el-icon-${name}`)
  export default {
    data() {
      return {
        iconList: originList,
        active: null,
        key: ''
      }
    },
    watch: {
      key(val) {
        if (val) {
          this.iconList = originList.filter(name => name.indexOf(val) > -1)
        } else {
          this.iconList = originList
        }
      }
    },
    methods: {
      onOpen() {
        this.active = this.current
        this.key = ''
      },
      onClose() {},
      onSelect(icon) {
        this.active = icon
        this.$emit('select', icon)
        this.$emit('update:visible', false)
      }
    }
  };
</script>

<style>
  *{
    margin: 0;
    padding: 0;
  }
  .container{
    position: relative;
    width: 80%;
    height: inherit;
    max-width: 1200px;
    margin: 0 auto;
  }
  .icon-type-item{
    position: relative;
    padding-top: 100px;
  }
  .icon-type-name{
    position: absolute;
    top: 20px;
  }
.icon-list .icon-type-item .icon-items .icon-item-group{
  position: relative;
    display: inline-block;
    width: 10%;
    box-sizing: border-box;
}
.icon-type-name:before{
  content: "";
    width: 3px;
    height:16px;
    display: inline-block;
    vertical-align: middle;
    margin: -4px 8px 0 0;
    background-color: #006aff;
}
.icon-type-name{
  font-size: 18px;
    font-weight: 600;
    color: #162a48;
    margin:24px 0 40px;
}
.icon-info{
  text-align: center;
}
.icon-info i{
  display: block;
    margin-top: 20px;
    font-size: 24px;
    color: #162a48;
}
.icon-info p{
      margin-top: 12px;
    color: #909eb0;
}
.icon-item:hover{
  border: 1px solid #006aff;
}
.icon-item{
  position: relative;
    width: 100%;
    height: 100px;
    display: inline-block;
    cursor: pointer;
    text-align: center;
    border-radius: 2px;
    transition: all .2s cubic-bezier(.4,.6,.5,1.32);
    vertical-align: middle;
    border: 1px solid transparent;
}
</style>
