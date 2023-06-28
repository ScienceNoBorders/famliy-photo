<template>
  <el-card shadow="hover">
    <template #header>
      <div class="card-header">
        <span>待办事项</span>
      </div>
    </template>
    <div class="todo_box box">
      <div class="todo_top">
        <el-row :gutter="16">
          <el-col :span="8">
            <div class="todo_top_box">
              <p>待审批</p>
              <span>10</span>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="todo_top_box">
              <p>待提交</p>
              <span>10</span>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="todo_top_box">
              <p>待阅示</p>
              <span>10</span>
            </div>
          </el-col>
        </el-row>
      </div>
      <ul class="todo_list">
        <li v-for="(item, index) in detaildata" :key="index">
          <p>{{ item.state }}</p><span>{{ item.txt }}</span>
        </li>
      </ul>
    </div>
  </el-card>
</template>

<script>

export default {

  components: {
  },
  props: {

  },
  data() {
    return {
      detaildata: [
        {
          state: '【待审批】',
          txt: '公告发布审批公告发布审批公告发布审批公告发布审批公告发布审批公告发布审批公告发布审批公告发布审批',
          hint: '公告发布审批说明1'
        },
        {
          state: '【待提交】',
          txt: '差旅费报销差旅费报销',
          hint: '公告发布审批说明2'
        },
        {
          state: '【待提交】',
          txt: '录用管理',
          hint: '公告发布审批说明3'
        },
        {
          state: '【待审批】',
          txt: '通知公告',
          hint: '公告发布审批说明4'
        },
        {
          state: '【待审批】',
          txt: '公告发布审批公告发布审批',
          hint: '公告发布审批说明5'
        }
      ]

    }
  }

}

</script>
<style lang="scss" scoped>
 .todo_top {
    .todo_top_box{
      background: #F7F9FA;
      border-radius:4px ;
      padding:12px  16px;
      span{
        font-size: 24px;
        line-height: 32px;

        color:#5584fd ;
      }
      p{
        font-size: 14px;
        color:#383838 ;
        margin: 0;
        line-height: 20px;
      }
    }
   }
   .todo_list{
     margin: 0 ;
     padding: 0;
     li{
      list-style: none;
      background: #F7F9FA;
      line-height: 20px;
      padding: 8px;
      margin:6px 0;
      cursor: pointer;
      font-size: 14px;
      p{
        margin:  0;
        display: inline-block;
        margin: 0 4px;
        vertical-align: middle;
      }
      span{
        display: inline-block;
        margin: 0 4px;
        width: calc(100% - 120px);
        overflow: hidden;
        text-overflow:ellipsis;
        white-space: nowrap;
        vertical-align: middle;
      }
     }
     li:hover{color: #5584fd;
     }
 }
</style>
