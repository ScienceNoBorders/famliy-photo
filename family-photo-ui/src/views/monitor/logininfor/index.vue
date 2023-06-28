<template>
  <div class="app-container search-table-box aidex-table">
    <el-card shadow="never" ref="queryRef" style="margin-bottom: 12px;" class="search_card" v-show="showSearch">
      <div class="filter-container">
        <div class="search_box">
          <el-form :model="queryParams" ref="queryForm" label-width="80px">
            <el-row :gutter="16">
              <el-col :md="6">
                <el-form-item label="登录地址" prop="ipaddr">
                  <el-input
                    v-model="queryParams.ipaddr"
                    placeholder="请输入登录地址"
                    style="width: 100%"
                    clearable
                    @keyup.enter.native="handleQuery"
                  />
                </el-form-item>
              </el-col>
              <el-col :md="6">
                <el-form-item label="用户名称" prop="userName">
                  <el-input
                    v-model="queryParams.userName"
                    placeholder="请输入用户名称"
                    style="width: 100%"
                    clearable
                    @keyup.enter.native="handleQuery"
                  />
                </el-form-item>
              </el-col>
              <el-col :md="6">
                <el-form-item label="状态" prop="status">
                  <el-select
                    v-model="queryParams.status"
                    placeholder="登录状态"
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
                <el-form-item v-if="advanced" label="登录时间">
                  <el-date-picker
                    v-model="dateRange"
                    style="width: 100%"
                    clearable
                    value-format="yyyy-MM-dd HH:mm:ss"
                    type="daterange"
                    range-separator="-"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期"
                    :default-time="['00:00:00', '23:59:59']"
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
              <el-button disabled type="text">登录日志</el-button>
            </div>
          </el-col>
          <el-col :span="16">
            <div class="btn_box" align="right" style="float: right;">
              <el-button
                v-if="!multiple"
                class="filter-item" style="margin-left: 8px;"
                type="danger"
                icon="el-icon-delete"
                :disabled="multiple"
                @click="handleDelete"
                v-hasPermi="['monitor:logininfor:remove']"
              >删除</el-button>
              <el-button
                class="filter-item" style="margin-left: 8px;"
                type="danger"
                icon="el-icon-delete"
                @click="handleClean"
                v-hasPermi="['monitor:logininfor:remove']"
              >清空</el-button>
              <el-button
                class="filter-item" style="margin-left: 8px;"
                icon="el-icon-unlock"
                :disabled="single"
                @click="handleUnlock"
                v-hasPermi="['monitor:logininfor:unlock']"
              >解锁</el-button>
              <el-button
                class="filter-item" style="margin-left: 8px;"
                icon="el-icon-download"
                @click="handleExport"
                v-hasPermi="['monitor:logininfor:export']"
              >导出</el-button>
              <right-toolbar @showSearchFun="showSearchFun" :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
            </div>
          </el-col>
        </el-row>
      </template>

      <el-table ref="tables" v-loading="loading" :data="list" @selection-change="handleSelectionChange" :default-sort="defaultSort" @sort-change="handleSortChange"         highlight-current-row style="width: 100%;"
        :height="tableHeight">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="访问编号" align="center" prop="infoId" />
        <el-table-column label="用户名称" align="center" prop="userName" :show-overflow-tooltip="true" sortable="custom" :sort-orders="['descending', 'ascending']" />
        <el-table-column label="登录地址" align="center" prop="ipaddr" width="130" :show-overflow-tooltip="true" />
        <el-table-column label="登录地点" align="center" prop="loginLocation" :show-overflow-tooltip="true" />
        <el-table-column label="浏览器" align="center" prop="browser" :show-overflow-tooltip="true" />
        <el-table-column label="操作系统" align="center" prop="os" />
        <el-table-column label="登录状态" align="center" prop="status">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_common_status" :value="scope.row.status"/>
          </template>
        </el-table-column>
        <el-table-column label="操作信息" align="center" prop="msg" :show-overflow-tooltip="true" />
        <el-table-column label="登录日期" align="center" prop="loginTime" sortable="custom" :sort-orders="['descending', 'ascending']" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.loginTime) }}</span>
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
    </el-card>
  </div>
</template>

<script>
import { list, delLogininfor, cleanLogininfor, unlockLogininfor } from "@/api/monitor/logininfor";

export default {
  name: "Logininfor",
  dicts: ['sys_common_status'],
  data() {
    return {
      advanced: false,
      tableHeight: this.getInitTableHeight(),
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 选择用户名
      selectName: "",
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 表格数据
      list: [],
      // 日期范围
      dateRange: [],
      // 默认排序
      defaultSort: {prop: 'loginTime', order: 'descending'},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        ipaddr: undefined,
        userName: undefined,
        status: undefined
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
		/** 隐藏搜索按钮操作 */
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
		/** 展开按钮操作 */
    toggleAdvanced() {
      const oldHeight = this.$refs.queryRef.$el.offsetHeight
      this.advanced = !this.advanced
      this.$nextTick(() => (
         this.tableHeight = this.$refs.tables.$el.offsetHeight - (this.$refs.queryRef.$el.offsetHeight-oldHeight)
      ))
    },
    /** 查询登录日志列表 */
    getList() {
      this.loading = true;
      list(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.list = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
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
      this.ids = selection.map(item => item.infoId)
      this.single = selection.length!=1
      this.multiple = !selection.length
      this.selectName = selection.map(item => item.userName);
    },
    /** 排序触发事件 */
    handleSortChange(column, prop, order) {
      this.queryParams.orderByColumn = column.prop;
      this.queryParams.isAsc = column.order;
      this.getList();
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const infoIds = row.infoId || this.ids;
      this.$modal.confirm('是否确认删除访问编号为"' + infoIds + '"的数据项？').then(function() {
        return delLogininfor(infoIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 清空按钮操作 */
    handleClean() {
      this.$modal.confirm('是否确认清空所有登录日志数据项？').then(function() {
        return cleanLogininfor();
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("清空成功");
      }).catch(() => {});
    },
    /** 解锁按钮操作 */
    handleUnlock() {
      const username = this.selectName;
      this.$modal.confirm('是否确认解锁用户"' + username + '"数据项?').then(function() {
        return unlockLogininfor(username);
      }).then(() => {
        this.$modal.msgSuccess("用户" + username + "解锁成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('monitor/logininfor/export', {
        ...this.queryParams
      }, `logininfor_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
