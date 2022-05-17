<template>
 <div id="main" class="scrollbar">
   <el-button @click="ddd()"></el-button>
       <div id="content">
         <div class="icon-item" v-for="(item,index) in dataList" :key="index">
           <div class="icon-info"><i :class="item"></i>
             <p>{{item}}</p>
           </div>
         </div>
       </div>
     </div>
</template>

<script>
  import { getIconList } from "./icon.js";
  export default {
    name: "Icon",
    data() {
      return {
         dataList:[]
      };
    },
    created() {
      this.dataList = getIconList()
    },
    methods: {
      // ddd(){
      //   let dom = document.querySelectorAll(".icon-info");
      //   const that = this
      //   dom.forEach(function(item){
      //     const iconName = item.firstChild.className
      //     console.log("'"+iconName+"',")
      //     that.dataList.push(iconName)
      //   })
      //   const dd = this.dataList
      // }
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
