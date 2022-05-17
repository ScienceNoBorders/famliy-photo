<template>
  <div class="typical-home" ref="portaletDiv" style="margin: 8px 0;">
    <el-row :gutter="16" class="module-list">
      <!-- 待办事项 -->
      <el-col :span="24" >
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>项目简介</span>
            </div>
          </template>
          <div class="box">
            <p style="text-indent: 30px;line-height: 200%;margin: -5px;">
              基于RuoYi-Vue扩展，仅升级前端展示样式，修改代码生成器，未修改任何后端代码，目前已经在多个项目中使用，取得良好应用，适配RuoYi-Vue版本：V3.8.2
            </p>
            <p style="text-indent: 30px;line-height: 200%;color: red;">
               如果对该套主题样式有兴趣（获取升级主题源码），可联系【15389457063】或者扫描在Aidex体验环境微信扫码咨询【皮皮大刺猬】
            </p>
            <p>
              <el-row :gutter="24">
                <el-col :span="8">
                  <el-button  style="height: 48px;font-weight:600;font-size: 18px;width: 100%;" type="primary" @click="goTarget('http://aidex.vip:8085/')">
                    新上验收宝（含APP），点击体验
                  </el-button>
                </el-col>
                <el-col :span="8">
                  <el-button  style="height: 48px;font-size: 18px;width: 100%;" type="success" @click="goTarget('https://gitee.com/big-hedgehog/aidex-sharp')">
                    访问AiDex
                  </el-button>
                </el-col>
                <el-col :span="8">
                  <el-button  style="height: 48px;font-size: 18px;width: 100%;" type="warning" @click="goTarget('https://www.bilibili.com/video/BV13L411u7oC?spm_id_from=333.999.0.0')">
                    我的B站
                  </el-button>
                </el-col>
              </el-row>
            </p>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  export default {
    computed: {
    },
    created () {
    },
    methods: {
      goTarget (url) {
        window.open(url)
      },
      caseDisplay (url) {
        this.$message.success(
          '案例正在研发中，目前尚未实现，敬请期待！',
          3
         )
      }
    }
  }
</script>
