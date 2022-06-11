<template>
  <el-card shadow="hover">
    <template #header>
      <div class="card-header">
        <span>产品热度</span>
      </div>
    </template>
    <div class="box">
      <ul class="top_list">
        <li v-for="(item, index) in toplist" :key="index">
          <el-tag :type="item.type" class="list-type">{{ item.top }}</el-tag>
          <span class="list-text">{{ item.data }}</span>
          <div class="list-right">
            <span
              class="list-number "
            >{{ item.number }}</span>
            <span
              class="
              list-icon"
            > <svg-icon :icon-class=" item.icon " /></span>
          </div>
        </li>
      </ul>
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
      toplist: [{
        top: '1',
        data: 'CRM工作台解决方案',
        icon: 'arrow-up-fill',
        number: '1080',
        type: ''
      },
      {
        top: '2',
        data: '项目管理工作台解决方案',
        icon: 'arrow-up-fill',
        number: '1080',
        type: ''
      
      },
      {
        top: '3',
        data: '任务管理工作台解决方案',
        icon: '',
        number: '1080',
        type: ''
      },
      {
        top: '4',
        data: '流程审批页解决方案',
        icon: '',
        number: '1080',
        type: 'info'
      },
      {
        top: '5',
        data: '通用表格页解决方案',
        icon: '',
        number: '1080',
        type: 'info'
      },
      {
        top: '6',
        data: '树组件解决方案',
        icon: '',
        number: '1080',
        type: 'info'
      },
      {
        top: '7',
        data: '财务管理工作台解决方案',
        icon: '',
        number: '1080',
        type: 'info'
      }
      ]
    }
  }

}
</script>
