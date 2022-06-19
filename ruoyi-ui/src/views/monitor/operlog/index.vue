<template>
  <div class="app-container search-table-box aidex-table">
    <el-card shadow="never" ref="queryRef" style="margin-bottom: 12px;" class="search_card" v-show="showSearch">
      <div class="filter-container">
        <div class="search_box">
          <el-form :model="queryParams" ref="queryForm" label-width="80px">
            <el-row :gutter="16">
              <el-col :md="6">
                <el-form-item label="系统模块" prop="title">
                  <el-input
                    v-model="queryParams.title"
                    placeholder="请输入系统模块"
                    style="width: 100%"
                    clearable
                    @keyup.enter.native="handleQuery"
                  />
                </el-form-item>
              </el-col>
              <el-col :md="6">
                <el-form-item label="操作人员" prop="operName">
                  <el-input
                    v-model="queryParams.operName"
                    placeholder="请输入操作人员"
                    style="width: 100%"
                    clearable
                    @keyup.enter.native="handleQuery"
                  />
                </el-form-item>
              </el-col>
              <el-col :md="6">
                <el-form-item label="类型" prop="businessType">
                  <el-select
                    v-model="queryParams.businessType"
                    placeholder="操作类型"
                    style="width: 100%"
                    clearable
                    @change="handleQuery"
                  >
                    <el-option
                      v-for="dict in dict.type.sys_oper_type"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :md="6">
                <el-form-item v-if="advanced" label="状态" prop="status">
                  <el-select
                    v-model="queryParams.status"
                    placeholder="操作状态"
                    style="width: 100%"
                    @change="handleQuery"
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
                <el-form-item v-if="advanced" label="操作时间">
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
                  <a @click="toggleAdvanced" style="margin:0 4px 0 8px ;vertical-align: middle;">
                    {{ advanced ? '收起' : '展开' }}
                    <i :class="advanced ? 'el-icon-arrow-up' : 'el-icon-arrow-down'"></i>
                  </a>
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
              <el-button disabled type="text">操作日志</el-button>
            </div>
          </el-col>
          <el-col :span="16">
            <div class="btn_box" align="right" style="float: right;">
              <el-button
                class="filter-item" style="margin-left: 8px;"
                type="danger"
                icon="el-icon-delete"
                :disabled="multiple"
                v-if="!multiple"
                @click="handleDelete"
                v-hasPermi="['monitor:operlog:remove']"
              >删除</el-button>
              <el-button
                class="filter-item" style="margin-left: 8px;"
                type="danger"
                icon="el-icon-delete"
                @click="handleClean"
                v-hasPermi="['monitor:operlog:remove']"
              >清空</el-button>
              <el-button
                class="filter-item" style="margin-left: 8px;"
                icon="el-icon-download"
                @click="handleExport"
                v-hasPermi="['monitor:operlog:export']"
              >导出</el-button>
              <right-toolbar @showSearchFun="showSearchFun" :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
            </div>
          </el-col>
        </el-row>
      </template>

      <el-table ref="tables" v-loading="loading" :data="list" @selection-change="handleSelectionChange" :default-sort="defaultSort" @sort-change="handleSortChange"  highlight-current-row style="width: 100%;"
        :height="tableHeight">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="日志编号" align="center" prop="operId" />
        <el-table-column label="系统模块" align="center" prop="title" />
        <el-table-column label="操作类型" align="center" prop="businessType">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_oper_type" :value="scope.row.businessType"/>
          </template>
        </el-table-column>
        <el-table-column label="请求方式" align="center" prop="requestMethod" />
        <el-table-column label="操作人员" align="center" prop="operName" width="100" :show-overflow-tooltip="true" sortable="custom" :sort-orders="['descending', 'ascending']" />
        <el-table-column label="操作地址" align="center" prop="operIp" width="130" :show-overflow-tooltip="true" />
        <el-table-column label="操作地点" align="center" prop="operLocation" :show-overflow-tooltip="true" />
        <el-table-column label="操作状态" align="center" prop="status">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_common_status" :value="scope.row.status"/>
          </template>
        </el-table-column>
        <el-table-column label="操作日期" align="center" prop="operTime" sortable="custom" :sort-orders="['descending', 'ascending']" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.operTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              type="text"
              @click="handleView(scope.row,scope.index)"
              v-hasPermi="['monitor:operlog:query']"
            >详细</el-button>
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

      <!-- 操作日志详细 -->
      <el-dialog title="操作日志详细" :visible.sync="open" width="700px" append-to-body>
        <div class="dialog_box">
          <el-form ref="form" :model="form" label-position="top">
            <el-row :gutter="16">
              <el-col :span="12">
                <el-form-item label="操作模块">
                  <el-input v-model="form.title + '/' + typeFormat(form)" disabled/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="登录信息">
                  <el-input v-model="form.operName + '/' + form.operIp + '/' + form.operLocation" disabled/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="请求地址">
                  <el-input v-model="form.requestMethod" disabled/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="请求方式">
                  <el-input v-model="form.requestMethod" disabled/>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="操作方法">
                  <el-input v-model="form.method" disabled/>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="请求参数">
                  <el-input type="textarea" :rows="3" v-model="form.operParam" disabled/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="返回参数">
                  <el-input v-model="form.jsonResult" disabled/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="操作状态">
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
              <el-col :span="12">
                <el-form-item label="操作时间">
                  <el-input v-model="form.operTime" disabled/>
                </el-form-item>
              </el-col>
              <el-col :span="24">
                <el-form-item label="异常信息" v-if="form.status === 1">
                  <el-input type="textarea" :rows="3" v-model="form.errorMsg" disabled/>
                </el-form-item>
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
import { list, delOperlog, cleanOperlog } from "@/api/monitor/operlog";

export default {
  name: "Operlog",
  dicts: ['sys_oper_type', 'sys_common_status'],
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
      // 表格数据
      list: [],
      // 是否显示弹出层
      open: false,
      // 日期范围
      dateRange: [],
      // 默认排序
      defaultSort: {prop: 'operTime', order: 'descending'},
      // 表单参数
      form: {},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: undefined,
        operName: undefined,
        businessType: undefined,
        status: undefined
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
         this.tableHeight = this.$refs.tables.$el.offsetHeight - (this.$refs.queryRef.$el.offsetHeight-oldHeight)
      ))
    },
    toggleAdvanced() {
      const oldHeight = this.$refs.queryRef.$el.offsetHeight
      this.advanced = !this.advanced
      this.$nextTick(() => (
         this.tableHeight = this.$refs.tables.$el.offsetHeight - (this.$refs.queryRef.$el.offsetHeight-oldHeight)
      ))
    },
    /** 查询登录日志 */
    getList() {
      this.loading = true;
      list(this.addDateRange(this.queryParams, this.dateRange)).then( response => {
          this.list = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    // 操作日志类型字典翻译
    typeFormat(row, column) {
      return this.selectDictLabel(this.dict.type.sys_oper_type, row.businessType);
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
      this.$refs.tables.sort(this.defaultSort.prop, this.defaultSort.order)
      this.handleQuery();
    },
    /** 多选框选中数据 */
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.operId)
      this.multiple = !selection.length
    },
    /** 排序触发事件 */
    handleSortChange(column, prop, order) {
      this.queryParams.orderByColumn = column.prop;
      this.queryParams.isAsc = column.order;
      this.getList();
    },
    /** 详细按钮操作 */
    handleView(row) {
      this.open = true;
      this.form = row;
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const operIds = row.operId || this.ids;
      this.$modal.confirm('是否确认删除日志编号为"' + operIds + '"的数据项？').then(function() {
        return delOperlog(operIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 清空按钮操作 */
    handleClean() {
      this.$modal.confirm('是否确认清空所有操作日志数据项？').then(function() {
        return cleanOperlog();
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("清空成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('monitor/operlog/export', {
        ...this.queryParams
      }, `operlog_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
