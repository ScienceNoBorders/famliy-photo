<template>
  <el-card shadow="hover">
    <template #header>
      <div class="card-header">
        <span>关注项目总览</span>
      </div>
    </template>
    <div class="project_box box">
      <el-table :data="tableData" height="298px" style="width: 100%">
        <el-table-column label="项目名称" show-overflow-tooltip>
          <template #default="scope">
            <span>{{ scope.row.data }}</span>
          </template>
        </el-table-column>
        <el-table-column label="项目状态" width="100">
          <template #default="scope">
            <div class="name-wrapper">
              <el-tag :type="scope.row.type">{{ scope.row.name }}</el-tag>
            </div>
          </template> </el-table-column>
        <el-table-column label="责任人" width="80">
          <template #default="scope">
            <span>{{ scope.row.user }}</span>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </el-card>
</template>

<style lang="scss" scoped>
  .el-tag--small {
    height: 22px;
    line-height: 22px;
  }

  .project_box {
    .el-table th {
      padding: 8px 0;
    }

    .el-table td {
      padding: 8px 0;
      color: #202d40;
    }

    .el-table .cell {
      line-height: 22px;
      font-size: 14px;
    }

  }
</style>

<script>
export default {

  components: {},
  props: {

  },
  data() {
    return {
      activeName: 'first',
      tableData: [{
        data: 'DevOps平台解决方案',
        name: '未开始',
        user: '魏小康',
        type: 'danger'
      },
      {
        data: '主数据管理系统',
        name: '进行中',
        user: '余瑶',
        type: 'info'
      },
      {
        data: '任务管理系统',
        name: '已完成',
        user: '范小敏',
        type: 'warning'
      },
      {
        data: '健康信息云平台',
        name: '进行中',
        user: '刘家杰',
        type: 'success'
      },
      {
        data: '应用支撑平台',
        name: '进行中',
        user: '范小敏',
        type: 'info'
      },
      {
        data: '疫情防控解决方案',
        name: '进行中',
        user: '魏小康',
        type: 'warning'
      },
      {
        data: '办公自动化系统OA',
        name: '进行中',
        user: '李晓红',
        type: 'danger'
      }
      ]
    }
  }

}
</script>
