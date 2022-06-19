<template>
  <div class="app-container search-table-box aidex-table">
    <el-card shadow="never" ref="queryRef" style="margin-bottom: 12px;" class="search_card" v-show="showSearch">
      <div class="filter-container">
        <div class="search_box">
          <el-form :model="queryParams" ref="queryForm" label-width="80px">
            <el-row :gutter="16">
              <el-col :md="6">
                <el-form-item label="任务名称" prop="jobName">
                  <el-input
                    v-model="queryParams.jobName"
                    placeholder="请输入任务名称"
                    style="width: 100%"
                    clearable
                    @keyup.enter.native="handleQuery"
                  />
                </el-form-item>
              </el-col>
              <el-col :md="6">
                <el-form-item label="任务组名" prop="jobGroup">
                  <el-select
                    v-model="queryParams.jobGroup"
                    placeholder="请选择任务组名"
                    style="width: 100%"
                    clearable
                  >
                    <el-option
                      v-for="dict in dict.type.sys_job_group"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :md="6">
                <el-form-item label="执行状态" prop="status">
                  <el-select
                    v-model="queryParams.status"
                    placeholder="请选择执行状态"
                    style="width: 100%"
                    clearable
                  >
                    <el-option
                      v-for="dict in dict.type.sys_common_status"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :md="6">
                <el-form-item v-if="advanced" label="执行时间">
                  <el-date-picker
                    v-model="dateRange"
                    style="width: 100%"
                    clearable
                    value-format="yyyy-MM-dd"
                    type="daterange"
                    range-separator="-"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期"
                  ></el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :md="6" align="right" style="float: right;">
                <el-form-item>
                  <el-button class="filter-item" type="primary" @click="handleQuery">搜索</el-button>
                  <el-button class="filter-item" style="margin-left: 8px" @click="resetQuery">重置</el-button>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>
      </div>
    </el-card>

    <el-card shadow="never" >
      <template #header>
        <el-row>
          <el-col :span="8">
            <div class="card-header">
              <el-button disabled type="text">日志数据</el-button>
            </div>
          </el-col>
          <el-col :span="16">
            <div class="btn_box" align="right" style="float: right;">
              <el-button
                class="filter-item" style="margin-left: 8px;" v-if="!multiple"
                type="danger"
                :disabled="multiple"
                @click="handleDelete"
                v-hasPermi="['monitor:job:remove']"
              >删除</el-button>
              <el-button
                class="filter-item" style="margin-left: 8px;"
                @click="handleClean"
                type="danger"
                v-hasPermi="['monitor:job:remove']"
              >清空</el-button>
              <el-button
                class="filter-item" style="margin-left: 8px;"
                @click="handleExport"
                icon="el-icon-download"
                v-hasPermi="['monitor:job:export']"
              >导出</el-button>
              <el-button
                class="filter-item" style="margin-left: 8px;"
                icon="el-icon-close"
                @click="handleClose"
              >关闭</el-button>
              <right-toolbar @showSearchFun="showSearchFun" :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
            </div>
          </el-col>
        </el-row>
      </template>
      <el-table v-loading="loading" ref="tableRef" :data="jobLogList" @selection-change="handleSelectionChange"
        highlight-current-row style="width: 100%;"
        :height="tableHeight"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="日志编号" width="80" align="center" prop="jobLogId" />
        <el-table-column label="任务名称" align="center" prop="jobName" :show-overflow-tooltip="true" />
        <el-table-column label="任务组名" align="center" prop="jobGroup" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_job_group" :value="scope.row.jobGroup"/>
          </template>
        </el-table-column>
        <el-table-column label="调用目标字符串" align="center" prop="invokeTarget" :show-overflow-tooltip="true" />
        <el-table-column label="日志信息" align="center" prop="jobMessage" :show-overflow-tooltip="true" />
        <el-table-column label="执行状态" align="center" prop="status">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_common_status" :value="scope.row.status"/>
          </template>
        </el-table-column>
        <el-table-column label="执行时间" align="center" prop="createTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              type="text"
              @click="handleView(scope.row)"
              v-hasPermi="['monitor:job:query']"
            >详细</el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />

      <!-- 调度日志详细 -->
      <el-dialog title="调度日志详细" :visible.sync="open" width="700px" append-to-body>
        <div class="dialog_box">
          <el-form ref="form" :model="form" label-position="top">
            <el-row :gutter="16">
              <el-col :span="12">
                <el-form-item label="日志序号">
                  <el-input v-model="form.jobLogId" disabled/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="任务名称">
                  <el-input v-model="form.jobName" disabled/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="任务分组">
                  <el-input v-model="form.jobGroup" disabled/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="执行时间">
                  <el-input v-model="form.createTime" disabled/>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="调用方法">
                  <el-input v-model="form.invokeTarget" disabled/>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="日志信息">
                  <el-input v-model="form.jobMessage" disabled/>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="执行状态">
                  <el-select v-model="form.status" disabled
                    style="width: 100%"
                    clearable
                  >
                    <el-option
                      v-for="dict in dict.type.sys_common_status"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="异常信息" v-if="form.status == 1">{{ form.exceptionInfo }}</el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button @click="open = false">关 闭</el-button>
        </div>
      </el-dialog>
    </el-card>
  </div>
</template>

<script>
import { getJob} from "@/api/monitor/job";
import { listJobLog, delJobLog, cleanJobLog } from "@/api/monitor/jobLog";

export default {
  name: "JobLog",
  dicts: ['sys_common_status', 'sys_job_group','sys_job_status'],
  data() {
    return {
      advanced: false,
      tableHeight: this.getInitTableHeight(),
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 调度日志表格数据
      jobLogList: [],
      // 是否显示弹出层
      open: false,
      // 日期范围
      dateRange: [],
      // 表单参数
      form: {},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        jobName: undefined,
        jobGroup: undefined,
        status: undefined
      }
    };
  },
  created() {
    const jobId = this.$route.query.jobId;
    if (jobId !== undefined && jobId != 0) {
      getJob(jobId).then(response => {
        this.queryParams.jobName = response.data.jobName;
        this.queryParams.jobGroup = response.data.jobGroup;
        this.getList();
      });
    } else {
      this.getList();
    }
  },
  methods: {
    showSearchFun(isShowSearch){
      this.showSearch = isShowSearch
      let oldHeight = this.$refs.queryRef.$el.offsetHeight
      if(!isShowSearch){
          //当前是显示状态
          oldHeight = oldHeight + 12
      }else{
          oldHeight = oldHeight - 12
      }
      this.$nextTick(() => (
         this.tableHeight = this.$refs.tableRef.$el.offsetHeight - (this.$refs.queryRef.$el.offsetHeight-oldHeight)
      ))
    },
    toggleAdvanced() {
      const oldHeight = this.$refs.queryRef.$el.offsetHeight
      this.advanced = !this.advanced
      this.$nextTick(() => (
         this.tableHeight = this.$refs.tableRef.$el.offsetHeight - (this.$refs.queryRef.$el.offsetHeight-oldHeight)
      ))
    },
    /** 查询调度日志列表 */
    getList() {
      this.loading = true;
      listJobLog(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.jobLogList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    // 返回按钮
    handleClose() {
      const obj = { path: "/monitor/job" };
      this.$tab.closeOpenPage(obj);
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.jobLogId);
      this.multiple = !selection.length;
    },
    /** 详细按钮操作 */
    handleView(row) {
      this.open = true;
      this.form = row;
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const jobLogIds = this.ids;
      this.$modal.confirm('是否确认删除调度日志编号为"' + jobLogIds + '"的数据项？').then(function() {
        return delJobLog(jobLogIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 清空按钮操作 */
    handleClean() {
      this.$modal.confirm('是否确认清空所有调度日志数据项？').then(function() {
        return cleanJobLog();
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("清空成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('/monitor/jobLog/export', {
        ...this.queryParams
      }, `log_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
