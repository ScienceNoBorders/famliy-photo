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
              <Notice />
            </el-col>
            <el-col>
              <ProductBox />
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
import AppBox from '@/views/dashboard/AppBox'
import About from '@/views/dashboard/About'
import Todo from '@/views/dashboard/ToDo'
import ProjectBox from '@/views/dashboard/ProjectBox'
import Notice from '@/views/dashboard/Notice'
import ProductBox from '@/views/dashboard/ProductBox'
import LineChart from '@/views/dashboard/LineChart'
import PieChart from '@/views/dashboard/PieChart'
import RaddarChart from '@/views/dashboard/RaddarChart'
import BarChart from '@/views/dashboard/BarChart'
export default {

  components: {
    LineChart,
    AppBox,
    About,
    Todo,
    ProjectBox,
    RaddarChart,
    BarChart,
    PieChart,
    Notice,
    ProductBox
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
