<template>
  <div class="app_box">
    <el-row>
      <!-- 顶部列表-->
      <el-col :md="16" :lg="8" :xl="6">
        <el-card shadow="hover">
          <div style="height: 146px;">
            <el-carousel trigger="click" height="146px" @click.native="linkTo" style="cursor:pointer">
              <el-carousel-item v-for="item in 2" :key="item" />
            </el-carousel>
          </div>
        </el-card>
      </el-col>

      <el-col v-for="(item, index) in dataList" :key="index" :md="8" :lg="4" :xl="3">
        <el-card shadow="hover" @click="addTabMenu(item.path)">
          <div class="app_inner" @click="addTabMenu(item.path)">
            <span class="app_icon" :style="{ background: getMenuColor(index) }">
              <svg-icon :icon-class="item.icon" />
            </span>
            <span>{{ item.text }}</span>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>

export default {
  name: 'DragDialogDemo',
  data() {
    return {
      dialogTableVisible: false,
      colorList: ['#5584fd', '#3470ff', '#ff8801', '#00d6b9', '#7e3bf3'],
      dataList: [
        {
          icon: 'user',
          text: '用户管理',
          path: '/system/user'
        },
        {
          icon: 'druid',
          text: '部门管理',
          path: '/system/dept'
        },
        {
          icon: 'swagger',
          text: '岗位管理',
          path: '/system/post'
        },
        {
          icon: 'peoples',
          text: '角色管理',
          path: '/system/role'
        },
        {
          icon: 'email',
          text: '菜单管理',
          path: '/system/menu'
        },
        {
          icon: 'dict',
          text: '字典管理',
          path: 'system/dict'
        },
        {
          icon: 'settings-2-fill',
          text: '参数设置',
          path: 'system/config'
        },
        {
          icon: 'component',
          text: '代码生成',
          color: '#00d6b9',
          path: '/tool/gen'
        },
        {
          icon: 'apps-fill',
          text: '定时任务',
          path: '/monitor/job'
        },
        {
          icon: 'profile-line',
          text: '通知公告',
          path: '/system/notice'
        },
        {
          icon: 'eye-fill',
          text: '操作日志',
          path: '/system/log/operlog'
        },
        {
          icon: 'logininfor',
          text: '登陆日志',
          path: '/system/log/logininfor'
        },
        {
          icon: 'dashboard',
          text: '服务监控',
          path: '/monitor/server'
        },
        {
          icon: 'question-fill',
          text: '在线图标',
          path: '/system/icons'
        }
      ]
    }
  },
  methods: {
    getMenuColor (index) {
      return this.colorList[index % 4]
    },
    addTabMenu (path) {
      this.$router.push(path)
    },
    linkTo () {
      window.open('http://aidex.vip')
    }
  }
}
</script>
<style lang="scss" scope>

  .el-card__body{
   .app_inner{
     padding: 16px;
     height: 64px;
     line-height: 32px;
     padding-left: 64px;
     position: relative;
     cursor: pointer;
     .app_icon{
     position: absolute;
     left: 14px;
     top: 12px;
     height:40px;
     width: 40px;
     background: #cfd4db;
     color: #FFFFFF;
     border-radius:50% ;
     border: 1px solid rgba(0,0,0,.1);
     box-sizing: border-box;
     .svg-icon{
         text-align: center;
         font-size: 22px;
         line-height: 40px;
         margin:8px auto;
         display: block;
         vertical-align: middle;
      }
   }
  }
  }
  .el-carousel__item h3 {
      color: #475669;
      font-size: 14px;
      opacity: 0.75;
      line-height: 130px;
      margin: 0;
      text-align: center;
    }

    .el-carousel__item:nth-child(4) {
     background: url(banner2.png);
    }

    .el-carousel__item:nth-child(3) {
     background: url(banner.png);
    }

    .app_box{
      margin:  0 -8px;
      .el-card{
        margin: 8px;
      }

    }
</style>
