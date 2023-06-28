<template>
  <el-card shadow="hover">
    <template #header>
      <div class="card-header">
        <span>公告</span>
      </div>
    </template>
    <div class="box">
      <el-tabs v-model="activeName">
        <el-tab-pane label="公司" name="first">
          <ul class="list" style="height: 250px;">
            <li v-for="(item, index) in dataList" :key="index">
              <span>【{{ item.type }}】</span> <span>{{ item.data }}</span>
            </li>
          </ul>
        </el-tab-pane>
        <el-tab-pane label="平台" name="second">
          <span>平台</span>
        </el-tab-pane>
        <el-tab-pane label="项目" name="third">
          <span>项目</span>
        </el-tab-pane>
        <el-tab-pane label="活动" name="fourth">
          <span>活动</span>
        </el-tab-pane>
      </el-tabs>
    </div>
  </el-card>
</template>
<script>
export default {
  components: {},
  props: {

  },
  data() {
    return {
      activeName: 'first',
      dataList: [{
        data: '公司为什么需要建立一套统一的开发框架？',
        type: '公司'
      },
      {
        data: ' 进行技术沉淀——提升公司整体技术能力，避免陷入一个人的能力决定一个项目',
        type: '公司'
      },
      {
        data: 'LOGO要这样排，你得PPT颜值不用愁',
        type: '公司'
      },
      {
        data: '文艺青年最后的音乐据点：网易云音乐评论区',
        type: '公司'
      },
      {
        data: '长在火山泥里的夏威夷果，不用剥壳好好吃。 ',
        type: '公司'
      },
      {
        data: '产品经理如何处理工作中的突发事件',
        type: '公司'
      },
      {
        data: '统一开发框架（平台）的建设思路',
        type: '公司'
      }
      ]
    }
  }

}
</script>
