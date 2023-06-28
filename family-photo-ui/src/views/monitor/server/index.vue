<template>
  <div class="app-container sys-chart">
    <el-row>
      <el-col :span="24" class="card-box monitor-box">
        <el-row :gutter="12">
          <el-col :span="6">
            <el-card shadow="never">
              <div class="text-number" v-if="server.cpu">{{ server.cpu.cpuNum }}</div>
              <div class="title-text">CPU核心数</div>
            </el-card>
          </el-col>
          <el-col :span="6">
            <el-card shadow="never">
              <div class="text-number" v-if="server.cpu" :style="{color:getColor(server.cpu)}">{{ server.cpu.used }}%</div>
              <div class="title-text">
                系统CPU使用率
              </div>
            </el-card>
          </el-col>
          <el-col :span="6">
            <el-card shadow="never">
              <div class="text-number" v-if="server.cpu" :style="{color:getColor(server.cpu)}">{{ server.cpu.sys }}%</div>
              <div class="title-text">
                系统使用率
              </div>
            </el-card>
          </el-col>
          <el-col :span="6">
            <el-card shadow="never">
              <div class="text-number" v-if="server.cpu">{{ server.cpu.free }}%</div>
              <div class="title-text">
                当前CPU空闲率
              </div>
            </el-card>
          </el-col>
        </el-row>
      </el-col>

      <el-col :span="24" class="card-box monitor-box02">
        <el-card>
          <template #header>
            <div class="card-header">
               <svg-icon icon-class="database-2-line"/>
              <span>内存</span>
            </div>
          </template>
          <el-row :gutter="12">
            <el-col :span="6" class="monitor-box02-body">
              <el-card shadow="never">
                <div class="title-text">总内存</div>
                <el-row>
                  <el-col :span="12" class="statistic-box">
                    <div class="statistic-title">内存</div>
                    <div class="statistic-content"><span class="text01" v-if="server.mem">{{ server.mem.total }}</span>G</div>
                  </el-col>
                  <el-col :span="12" class="statistic-box">
                    <div class="statistic-title">JVM</div>
                    <div class="statistic-content"><span class="text02" v-if="server.jvm">{{ server.jvm.total }}</span>M</div>
                  </el-col>
                </el-row>
              </el-card>
            </el-col>
            <el-col :span="6" class="monitor-box02-body">
              <el-card shadow="never">
                <div class="title-text">已用内存</div>
                <el-row>
                  <el-col :span="12" class="statistic-box">
                    <div class="statistic-title">内存</div>
                    <div class="statistic-content"><span class="text01" v-if="server.mem">{{ server.mem.used}}</span>G</div>
                  </el-col>
                  <el-col :span="12" class="statistic-box">
                    <div class="statistic-title">JVM</div>
                    <div class="statistic-content"><span class="text02" v-if="server.jvm">{{ server.jvm.used}}</span>M</div>
                  </el-col>
                </el-row>
              </el-card>
            </el-col>
            <el-col :span="6" class="monitor-box02-body">
              <el-card shadow="never">
                <div class="title-text">剩余内存</div>
                <el-row>
                  <el-col :span="12" class="statistic-box">
                      <div class="statistic-title">内存</div>
                      <div class="statistic-content"><span class="text01" v-if="server.mem">{{ server.mem.free }}</span>G</div>
                  </el-col>
                  <el-col :span="12" class="statistic-box">
                    <div class="statistic-title">JVM</div>
                    <div class="statistic-content"><span class="text02" v-if="server.jvm">{{ server.jvm.free }}</span>M</div>
                  </el-col>
                </el-row>
              </el-card>
            </el-col>
            <el-col :span="6" class="monitor-box02-body">
              <el-card shadow="never">
                <div class="title-text">使用率</div>
                <el-row>
                  <el-col :span="12" class="statistic-box">
                    <div class="statistic-title">内存</div>
                    <div class="statistic-content"><span class="text01" v-if="server.mem" :class="{'text-danger': server.mem.usage > 80}">{{ server.mem.usage }}</span>%</div>
                  </el-col>
                  <el-col :span="12" class="statistic-box">
                    <div class="statistic-title">JVM</div>
                    <div class="statistic-content"><span class="text02" v-if="server.jvm" :class="{'text-danger': server.jvm.usage > 80}">{{ server.jvm.usage }}</span>%</div>
                  </el-col>
                </el-row>
              </el-card>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
      <el-col :span="24" class="card-box monitor-box03">
        <el-card>
          <template #header>
            <div class="card-header">
              <svg-icon icon-class="computer-line"/>
              <span>磁盘状态</span>
            </div>
            </template>
            <el-row :gutter="12" class="disc-caption-border">
              <el-col :span="8" v-for="(sysFile, index) in server.sysFiles" :key="index">
                <el-card shadow="never" class="disc-caption-box">
                  <div class="disc-caption-text">{{ sysFile.dirName }}<em>磁盘类型：{{ sysFile.sysTypeName }}</em></div>
                  <el-progress type="dashboard" :percentage="sysFile.usage" :color="colors" />
                  <el-row>
                    <el-col :span="12"><div class="disc-other">总大小<em class="disc-other-color01">{{ sysFile.total}}</em></div></el-col>
                    <el-col :span="12"><div class="disc-other">可用大小<em class="disc-other-color02">{{ sysFile.free }}</em></div></el-col>
                  </el-row>
                </el-card>
              </el-col>
            </el-row>
        </el-card>
      </el-col>
      <el-col :span="24" class="card-box monitor-table">
        <el-card>
          <template #header>
            <div class="card-header">
               <svg-icon icon-class="function-line"/>
              <span>服务器信息</span>
            </div>
          </template>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <table cellspacing="0" style="width: 100%;">
              <tbody>
                <tr>
                  <td class="el-table__cell is-leaf" width="50%">服务器名称：<div class="cell-content" v-if="server.sys">{{ server.sys.computerName }}</div></td>
                  <td class="el-table__cell is-leaf">操作系统：<div class="cell-content" v-if="server.sys">{{ server.sys.osName }}</div></td>
                </tr>
                <tr>
                  <td class="el-table__cell is-leaf">服务器IP：<div class="cell-content" v-if="server.sys">{{ server.sys.computerIp }}</div></td>
                  <td class="el-table__cell is-leaf">系统架构：<div class="cell-content" v-if="server.sys">{{ server.sys.osArch }}</div></td>
                </tr>
              </tbody>
            </table>
          </div>
        </el-card>
      </el-col>

      <el-col :span="24" class="card-box monitor-table">
        <el-card>
          <template #header>
            <div class="card-header">
              <svg-icon icon-class="stack-line"/>
              <span>Java虚拟机信息</span>
            </div>
          </template>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <table cellspacing="0" style="width: 100%;table-layout:fixed;">
              <tbody>
                <tr>
                  <td class="el-table__cell is-leaf">Java名称：<div class="cell-content" v-if="server.jvm">{{ server.jvm.name }}</div></td>
                  <td class="el-table__cell is-leaf">Java版本：<div class="cell-content" v-if="server.jvm">{{ server.jvm.version }}</div></td>
                </tr>
                <tr>
                  <td class="el-table__cell is-leaf">启动时间：<div class="cell-content" v-if="server.jvm">{{ server.jvm.startTime }}</div></td>
                  <td class="el-table__cell is-leaf">运行时长：<div class="cell-content" v-if="server.jvm">{{ server.jvm.runTime }}</div></td>
                </tr>
                <tr>
                  <td colspan="2" class="el-table__cell is-leaf">安装路径：<div class="cell-content" v-if="server.jvm">{{ server.jvm.home }}</div></td>
                </tr>
                <tr>
                  <td colspan="2" class="el-table__cell is-leaf">项目路径：<div class="cell-content" v-if="server.sys">{{ server.sys.userDir }}</div></td>
                </tr>
                <tr>
                  <td colspan="2" class="el-table__cell is-leaf" style="padding-left: 100px;"><span style="width: 80px;position: absolute;left: 30px;">运行参数：</span><div class="cell-content" v-if="server.jvm">{{ server.jvm.inputArgs }}</div></td>
                </tr>
              </tbody>
            </table>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getServer } from "@/api/monitor/server";

export default {
  name: "Server",
  data() {
    return {
      // 服务器信息
      server: [],
      colors: [
        { color: '#f56c6c', percentage: 20 },
        { color: '#e6a23c', percentage: 40 },
        { color: '#5cb87a', percentage: 60 },
        { color: '#ed5565', percentage: 80 },
        { color: '#ed5565', percentage: 100 },
      ]
    };
  },
  created() {
    this.getList();
    this.openLoading();
  },
  methods: {
    /** 查询服务器信息 */
    getList() {
      getServer().then(response => {
        this.server = response.data;
        this.$modal.closeLoading();
      });
    },
    /** 获取颜色 */
    getColor(obj) {
      let color = '#000000';
      if(obj != null){
        //系统CPU使用率
        if(obj.sys != null && obj.sys> 80){
           color = '#ed5565';
        }else if(obj.used != null && obj.used> 80){
          //用户CPU使用率
          color = '#ed5565';
        }
      }
      return color;
    },
    // 打开加载层
    openLoading() {
      this.$modal.loading("正在加载服务监控数据，请稍候！");
    }
  }
};
</script>
<style lang="scss">
  .sys-chart{
    .monitor-box .text-number {
        color: #000000;
        font-size: 42px;
        text-align: center;
    }
    .monitor-box .title-text {
        font-size: 12px;
        margin: 0;
        padding: 0;
        color: #666;
        text-align: center;
    }
    .card-header{
      display: flex;
      align-items: center;
    }
    .card-box .el-card__header {
        font-size: 14px;
        font-weight: 700;
        color: #262626;
        padding:10px 15px;
        border-bottom: 0;
        .svg-icon{
          margin-right: 5px;
          color:#2496ff;
          font-size: 18px;
        }
      }
    .monitor-box02 {
        .monitor-box02-body{
          .el-card{
          background: #f7f9fa;
          border: 0;
          .el-card__body{
            padding:0;
            .title-text{
              background: #f0f3f5;
              text-align: center;
              line-height: 40px;
              color: #666;
              font-size: 14px;
              font-weight: 700;
            }
            .text-number{
              width: 50%;
              text-align: center;
              padding: 10px;
              color: rgba(0,0,0,.65);
              font-size: 14px;
              line-height: 1.5;
              display: inline-block;
            }
          }
          }
          .statistic-title{
            font-size: 12px;
            color: #666;
            margin-bottom: 4px;
            text-align: center;
          }
          .statistic-box{
            padding:10px;
          }
          .statistic-content{
            font-size: 12px;
            text-align: center;
            color: #666;
            padding: 5px 0 0;
          }
          .text01{
            color: #3f8652;
            font-size: 22px;
            padding-right: 5px;
          }
          .text02{
            color: #f60;
            font-size: 22px;
            padding-right: 5px;
          }
          .text-danger{
            color: #ff0000!important;
          }
        }
    }
    .monitor-box03 {
        .disc-caption-text {
          font-size: 16px;
          font-weight: bold;
          color: #333;
          margin-bottom: 5px;
          text-align: left;
          display: block;
          em {
            font-size: 12px;
            color: #858585;
            font-style: normal;
            font-weight: normal;
            margin-left: 5px;
          }
        }
        .disc-caption-box{
          text-align: center;
          border: 0;
          border-right: 1px solid #d9d9d9;
          border-radius: 0;
          .disc-other {
            font-size: 12px;
            color: #666666;
            line-height: 20px;
            text-align: center;
          }
          .disc-other em {
            font-style: normal;
            font-size: 22px;
            margin-top: 10px;
            display: block;
          }
          .disc-other em.disc-other-color01 {
            color: #2496ff;
          }
          .disc-other em.disc-other-color02 {
            color: #3f8652;
          }
        }
        .disc-caption-border .el-col-8:last-of-type .disc-caption-box{
          border-right: 0;
        }
      }
      .monitor-table{
        .el-card__body{
          padding:0 0 10px;
          .el-table tr td{
            border-top: 1px solid #f8f8f8;
            border-bottom: 0;
            font-size: 14px;
            font-weight: 400;
            color: #666;
            padding:10px 0px 10px 30px;
            .cell-content{
              color: #000;
              display: inline-block;
            }
          }
          .el-table tr:nth-child(odd) {
              background: #fbfbfb;
          }
        }
      }
  }
</style>
