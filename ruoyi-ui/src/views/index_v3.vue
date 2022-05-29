<template>
  <div class="home" style="padding: 16px;">
    <AppBox />
    <About />
    <div class="idnex-box">
      <el-row>
        <el-col :md="16" :lg="16" :xl="18">
          <el-row>
            <el-col :span="12">
              <todo />
            </el-col>
            <el-col :span="12">
              <ProjectBox />
            </el-col>
            <el-col :span="24">
              <el-card shadow="hover">
                <template #header>
                  <div class="card-header">
                    <span>任务完成情况</span>
                  </div>
                </template>
                <div class="box">
                  <LineChart />
                </div>
              </el-card>
            </el-col>

            <el-col :span="12">
              <el-card shadow="hover">
                <template #header>
                  <div class="card-header">
                    <span>雷达图</span>
                  </div>
                </template>
                <div class="box">
                  <RaddarChart />
                </div>
              </el-card>
            </el-col>

            <el-col :span="12">
              <el-card shadow="hover">
                <template #header>
                  <div class="card-header">
                    <span>柱状图</span>
                  </div>
                </template>
                <div class="box">
                  <BarChart />
                </div>
              </el-card>
            </el-col>
          </el-row>

        </el-col>
        <el-col :md="8" :lg="8" :xl="6">
          <el-row>
            <el-col>
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
                        <li v-for="(item, index) in detalist" :key="index">
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
            </el-col>

            <el-col>
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
            </el-col>

            <el-col>
              <el-card shadow="hover">
                <template #header>
                  <div class="card-header">
                    <span>饼图</span>
                  </div>
                </template>
                <div class="box">
                  <PieChart />
                </div>
              </el-card>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import LineChart from '@/views/dashboard/LineChart'
import AppBox from '@/views/dashboard/AppBox'
import About from '@/views/dashboard/About'
import todo from '@/views/dashboard/ToDo'
import ProjectBox from '@/views/dashboard/ProjectBox'
import RaddarChart from '@/views/dashboard/RaddarChart'
import BarChart from '@/views/dashboard/BarChart'
import PieChart from '@/views/dashboard/PieChart'

export default {

  components: {
    LineChart,
    AppBox,
    About,
    todo,
    ProjectBox,
    RaddarChart,
    BarChart,
    PieChart
  },
  props: {

  },
  data() {
    return {
      activeName: 'first',
      detalist: [{
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

      ],
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

<style  lang="scss" >
  .top_list{
  list-style: none;
  margin: 0 ;
  padding: 0;
  li{
    padding: 8px;
    margin: 4px 0;
    position: relative;
    border-radius: 4px;
    cursor: pointer;
     font-size: 14px;
  }
  li:hover{
    background-color: #f2f4f9;
  }
    .list-type{
          display: inline-block;
          width: 20px;
          line-height: 20px;
          text-align: center;
          height: 20px;
          border-radius: 10px;
          vertical-align: middle;
          margin: 0 4px;
          border: 0;
          padding: 0;
      }
    .list-text{
    line-height: 18px;
    vertical-align: middle;
    }
    .list-right{
      position: absolute;
      right: 8px;
      top: 12px;
        svg{
          color: red;
        }
    }
  }

.home{
   margin-top: -8px;
   .el-tabs__header{
    margin-bottom: 8px;
   }
.list{
  margin: 0 ;
  padding: 0;
  overflow: auto;
  li{
    line-height: 32px;
    margin: 4px 0;
    font-size: 14px;
    color: #30324a;
    list-style: none;
    cursor: pointer;
    overflow: hidden;
    text-overflow:ellipsis;
    white-space: nowrap;
  }
  li:hover{
    color: #5b7fff;
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
    background-color: #5584fd;
  }

  .el-carousel__item:nth-child(3) {
    background-color: orange;
  }
  .el-carousel__item:nth-child(2) {
    background-color:blue;
  }
  .el-carousel__item:nth-child(1) {
    background-color:red;
  }
    .app_box{
      margin:  0 -8px;
      .el-card{
        margin: 8px;
      }
      .el-card__body{
         padding: 0;
     }
    }
.idnex-box{
  margin: 0 -8px;
   .el-card{
     margin: 8px;
   }
   .el-card__body{
      padding: 0;
     .box{
       padding: 16px;
       min-height: 240px;
     }
  }

}
.el-card__header{
     padding:16px;
     line-height: 20px;
     font-size: 16px;
     font-weight: 600;
     border: 0;
     padding-bottom: 0;
}

}
</style>
