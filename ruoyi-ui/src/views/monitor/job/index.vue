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
                  <el-select v-model="queryParams.jobGroup" placeholder="请选择任务组名"
                    style="width: 100%"
                    clearable
                    @change="handleQuery"
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
                <el-form-item label="任务状态" prop="status">
                  <el-select v-model="queryParams.status" placeholder="请选择任务状态"
                    style="width: 100%"
                    clearable
                    @change="handleQuery"
                  >
                    <el-option
                      v-for="dict in dict.type.sys_job_status"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    />
                  </el-select>
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
              <el-button disabled type="text">定时任务</el-button>
            </div>
          </el-col>
          <el-col :span="16">
            <div class="btn_box" align="right" style="float: right;">
              <el-button
                class="filter-item" style="margin-left: 8px;"
                type="primary"
                icon="el-icon-plus"
                @click="handleAdd"
                v-hasPermi="['monitor:job:add']"
              >新增</el-button>
              <el-button
                class="filter-item" style="margin-left: 8px;"
                type="danger"
                icon="el-icon-delete"
                v-if="!multiple"
                :disabled="multiple"
                @click="handleDelete"
                v-hasPermi="['monitor:job:remove']"
              >删除</el-button>
              <el-button
                class="filter-item" style="margin-left: 8px;"
                icon="el-icon-download"
                @click="handleExport"
                v-hasPermi="['monitor:job:export']"
              >导出</el-button>
              <el-button
                class="filter-item" style="margin-left: 8px;"
                icon="el-icon-s-operation"
                @click="handleJobLog"
                v-hasPermi="['monitor:job:query']"
              >日志</el-button>
              <right-toolbar :showSearch.sync="showSearch"
               @showSearchFun="showSearchFun" @queryTable="getList"></right-toolbar>
            </div>
          </el-col>
        </el-row>
      </template>

      <el-table ref="tableRef" v-loading="loading" :data="jobList" @selection-change="handleSelectionChange"
        highlight-current-row style="width: 100%;"
        :height="tableHeight">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="任务编号" width="100" align="center" prop="jobId" />
        <el-table-column label="任务名称" align="center" prop="jobName" :show-overflow-tooltip="true" />
        <el-table-column label="任务组名" align="center" prop="jobGroup">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_job_group" :value="scope.row.jobGroup"/>
          </template>
        </el-table-column>
        <el-table-column label="调用目标字符串" align="center" prop="invokeTarget" :show-overflow-tooltip="true" />
        <el-table-column label="cron执行表达式" align="center" prop="cronExpression" :show-overflow-tooltip="true" />
        <el-table-column label="状态" align="center">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.status"
              active-value="0"
              inactive-value="1"
              @change="handleStatusChange(scope.row)"
            ></el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              type="text"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['monitor:job:edit']"
            >修改</el-button>
            <el-divider direction="vertical"></el-divider>
            <el-button
              type="text"
              style="color: red;"
              @click="handleDelete(scope.row)"
              v-hasPermi="['monitor:job:remove']"
            >删除</el-button>
            <el-divider direction="vertical"></el-divider>
            <el-dropdown @command="(command) => handleCommand(command, scope.row)" v-hasPermi="['monitor:job:changeStatus', 'monitor:job:query']">
              <span class="el-dropdown-link">
                更多<i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="handleRun"
                  v-hasPermi="['monitor:job:changeStatus']">执行一次</el-dropdown-item>
                <el-dropdown-item command="handleView"
                  v-hasPermi="['monitor:job:query']">任务详细</el-dropdown-item>
                <el-dropdown-item command="handleJobLog"
                  v-hasPermi="['monitor:job:query']">调度日志</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </template>
        </el-table-column>
        <div slot="empty">
          <svg-icon icon-class="search-none" style="font-size: 64px;" />
          <p>暂无数据</p>
        </div>
      </el-table>

      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />

      <!-- 添加或修改定时任务对话框 -->
      <el-dialog :title="title" :visible.sync="open" width="720px" append-to-body>
        <div class="dialog_box">
          <el-form ref="form" :model="form" :rules="rules" label-position="top">
            <el-row :gutter="24">
              <el-col :span="12">
                <el-form-item label="任务名称" prop="jobName">
                  <el-input v-model="form.jobName" placeholder="请输入任务名称" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="任务分组" prop="jobGroup">
                  <el-select v-model="form.jobGroup" placeholder="请选择任务分组" style="width: 100%;">
                    <el-option
                      v-for="dict in dict.type.sys_job_group"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item prop="invokeTarget">
                  <span slot="label">
                    调用方法
                    <el-tooltip placement="top">
                      <div slot="content">
                        Bean调用示例：ryTask.ryParams('ry')
                        <br />Class类调用示例：com.ruoyi.quartz.task.RyTask.ryParams('ry')
                        <br />参数说明：支持字符串，布尔类型，长整型，浮点型，整型
                      </div>
                      <svg-icon icon-class="question-line" style="font-size:16px;vertical-align:-0.2em" />
                    </el-tooltip>
                  </span>
                  <el-input v-model="form.invokeTarget" placeholder="请输入调用目标字符串" />
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="cron表达式" prop="cronExpression">
                  <el-input v-model="form.cronExpression" placeholder="请输入cron执行表达式">
                    <template slot="append">
                      <el-button type="primary" @click="handleShowCron">
                        生成表达式
                        <i class="el-icon-time el-icon--right"></i>
                      </el-button>
                    </template>
                  </el-input>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="执行策略" prop="misfirePolicy">
                  <el-radio-group v-model="form.misfirePolicy">
                    <el-radio-button label="1">立即执行</el-radio-button>
                    <el-radio-button label="2">执行一次</el-radio-button>
                    <el-radio-button label="3">放弃执行</el-radio-button>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="是否并发" prop="concurrent">
                  <el-radio-group v-model="form.concurrent">
                    <el-radio-button label="0">允许</el-radio-button>
                    <el-radio-button label="1">禁止</el-radio-button>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="状态">
                  <el-radio-group v-model="form.status">
                    <el-radio-button
                      v-for="dict in dict.type.sys_job_status"
                      :key="dict.value"
                      :label="dict.value"
                    >{{dict.label}}</el-radio-button>
                  </el-radio-group>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>

      <el-dialog title="Cron表达式生成器" :visible.sync="openCron" append-to-body destroy-on-close class="scrollbar">
        <crontab @hide="openCron=false" @fill="crontabFill" :expression="expression"></crontab>
      </el-dialog>

      <!-- 任务日志详细 -->
      <el-dialog title="任务详细" :visible.sync="openView" width="720px" append-to-body>
        <div class="dialog_box">
          <el-form ref="form" :model="form" label-position="top">
            <el-row>
              <el-col :span="12">
                <el-form-item label="任务编号：">{{ form.jobId }}</el-form-item>
                <el-form-item label="任务名称：">{{ form.jobName }}</el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="任务分组：">{{ jobGroupFormat(form) }}</el-form-item>
                <el-form-item label="创建时间：">{{ form.createTime }}</el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="cron表达式：">{{ form.cronExpression }}</el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="下次执行时间：">{{ parseTime(form.nextValidTime) }}</el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="调用目标方法：">{{ form.invokeTarget }}</el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="任务状态：">
                  <div v-if="form.status == 0">正常</div>
                  <div v-else-if="form.status == 1">失败</div>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="是否并发：">
                  <div v-if="form.concurrent == 0">允许</div>
                  <div v-else-if="form.concurrent == 1">禁止</div>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="执行策略：">
                  <div v-if="form.misfirePolicy == 0">默认策略</div>
                  <div v-else-if="form.misfirePolicy == 1">立即执行</div>
                  <div v-else-if="form.misfirePolicy == 2">执行一次</div>
                  <div v-else-if="form.misfirePolicy == 3">放弃执行</div>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button @click="openView = false">关 闭</el-button>
        </div>
      </el-dialog>
    </el-card>
  </div>
</template>

<script>
import { listJob, getJob, delJob, addJob, updateJob, runJob, changeJobStatus } from "@/api/monitor/job";
import Crontab from '@/components/Crontab'

export default {
  components: { Crontab },
  name: "Job",
  dicts: ['sys_job_group', 'sys_job_status'],
  data() {
    return {
      tableHeight: this.getInitTableHeight(),
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 定时任务表格数据
      jobList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示详细弹出层
      openView: false,
      // 是否显示Cron表达式弹出层
      openCron: false,
      // 传入的表达式
      expression: "",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        jobName: undefined,
        jobGroup: undefined,
        status: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        jobName: [
          { required: true, message: "任务名称不能为空", trigger: "blur" }
        ],
        invokeTarget: [
          { required: true, message: "调用目标字符串不能为空", trigger: "blur" }
        ],
        cronExpression: [
          { required: true, message: "cron执行表达式不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
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
    /** 查询定时任务列表 */
    getList() {
      this.loading = true;
      listJob(this.queryParams).then(response => {
        this.jobList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 任务组名字典翻译
    jobGroupFormat(row, column) {
      return this.selectDictLabel(this.dict.type.sys_job_group, row.jobGroup);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        jobId: undefined,
        jobName: undefined,
        jobGroup: undefined,
        invokeTarget: undefined,
        cronExpression: undefined,
        misfirePolicy: 1,
        concurrent: 1,
        status: "0"
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.jobId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    // 更多操作触发
    handleCommand(command, row) {
      switch (command) {
        case "handleRun":
          this.handleRun(row);
          break;
        case "handleView":
          this.handleView(row);
          break;
        case "handleJobLog":
          this.handleJobLog(row);
          break;
        default:
          break;
      }
    },
    // 任务状态修改
    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "停用";
      this.$modal.confirm('确认要"' + text + '""' + row.jobName + '"任务吗？').then(function() {
        return changeJobStatus(row.jobId, row.status);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        row.status = row.status === "0" ? "1" : "0";
      });
    },
    /* 立即执行一次 */
    handleRun(row) {
      this.$modal.confirm('确认要立即执行一次"' + row.jobName + '"任务吗？').then(function() {
        return runJob(row.jobId, row.jobGroup);
      }).then(() => {
        this.$modal.msgSuccess("执行成功");
      }).catch(() => {});
    },
    /** 任务详细信息 */
    handleView(row) {
      getJob(row.jobId).then(response => {
        this.form = response.data;
        this.openView = true;
      });
    },
    /** cron表达式按钮操作 */
    handleShowCron() {
      this.expression = this.form.cronExpression;
      this.openCron = true;
    },
    /** 确定后回传值 */
    crontabFill(value) {
      this.form.cronExpression = value;
    },
    /** 任务日志列表查询 */
    handleJobLog(row) {
      const jobId = row.jobId || 0;
      this.$router.push({ path: '/monitor/job-log/index', query: { jobId: jobId } })
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加任务";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const jobId = row.jobId || this.ids;
      getJob(jobId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改任务";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.jobId != undefined) {
            updateJob(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addJob(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const jobIds = row.jobId || this.ids;
      this.$modal.confirm('是否确认删除定时任务编号为"' + jobIds + '"的数据项？').then(function() {
        return delJob(jobIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('monitor/job/export', {
        ...this.queryParams
      }, `job_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
