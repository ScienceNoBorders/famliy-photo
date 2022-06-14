<template>
  <div class="app-container search-table-box aidex-table">
    <el-row :gutter="20">
      <!--部门数据-->
      <el-col :span="4" :xs="24">
        <el-card shadow="never" class="aidex-tree-box">
          <div style="padding:12px 12px 20px;">
            <el-input v-model="deptName" placeholder="请输入部门名称" clearable prefix-icon="el-icon-search"/>
          </div>
          <div style="padding:0px 0 0px 12px;">
            <el-tree :data="deptOptions" :props="defaultProps" :expand-on-click-node="false"
              :filter-node-method="filterNode" ref="tree" default-expand-all highlight-current @node-click="handleNodeClick"
              :style="{height:treeHeight}"
              style="width: 100%;overflow-y: auto;"
            />
          </div>
        </el-card>
      </el-col>
      <!--用户数据-->
      <el-col :span="20" :xs="24" style="padding:0 12px 0 0">
        <el-card shadow="never" style="margin-bottom: 12px;border: 0;" class="search_card">
          <el-form :model="queryParams" ref="queryForm" label-width="80px" v-show="showSearch">
            <div class="search_box">
              <el-row>
                <el-col :md="8">
                  <el-form-item label="用户名称" prop="userName">
                    <el-input v-model="queryParams.userName" placeholder="请输入用户名称" style="width: 100%" clearable
                      @keyup.enter.native="handleQuery" />
                  </el-form-item>
                </el-col>
                <el-col :md="8">
                  <el-form-item label="手机号码" prop="phonenumber">
                    <el-input v-model="queryParams.phonenumber" placeholder="请输入手机号码" style="width: 100%" clearable
                      @keyup.enter.native="handleQuery" />
                  </el-form-item>
                </el-col>
                <el-col :md="8" v-if="advanced">
                  <el-form-item label="状态" prop="status">
                    <el-select @change="handleQuery" v-model="queryParams.status" placeholder="用户状态" size="small" style="width: 100%"
                      clearable>
                      <el-option v-for="dict in dict.type.sys_normal_disable" :key="dict.value" :label="dict.label"
                        :value="dict.value" />
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :md="8" v-if="advanced">
                  <el-form-item label="创建时间">
                    <el-date-picker v-model="dateRange" style="width: 100%" clearable value-format="yyyy-MM-dd"
                      type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期">
                    </el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :md="8" align="right" style="float: right;">
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
            </div>
          </el-form>
        </el-card>
        <el-card shadow="never" class="aidex-tool-box">
          <div class="aidex-table-box">
            <el-row :gutter="8" class="mb8" style="">
              <el-col :span="12">
               <div class="card-header">
                 <el-button type="text">用户列表</el-button>
               </div>
              </el-col>
              <el-col :span="12" >
                 <div class="btn_box" align="right" style="float: right;">
                  <el-button type="primary" icon="el-icon-plus" @click="handleAdd" v-hasPermi="['system:user:add']">新增
                  </el-button>
                  <el-button type="danger" icon="el-icon-delete" :disabled="multiple" v-if="!multiple"
                    @click="handleDelete" v-hasPermi="['system:user:remove']">删除</el-button>
                  <el-button icon="el-icon-upload2" @click="handleImport" v-hasPermi="['system:user:import']">导入
                  </el-button>
                  <el-button icon="el-icon-download" @click="handleExport" v-hasPermi="['system:user:export']">导出
                  </el-button>
                  <right-toolbar @showSearchFun="showSearchFun" :showSearch.sync="showSearch" @queryTable="getList" :columns.sync="columns" :tableKey.sync="tableKey">
                  </right-toolbar>
                </div>
              </el-col>
            </el-row>

            <el-table :key="tableKey" v-loading="loading" ref="tableRef" :height="tableHeight" :data="userList" @selection-change="handleSelectionChange" highlight-current-row style="width: 100%;">
              <el-table-column type="selection" width="50" align="center" />
              <template v-for="item in columns">
                <el-table-column label="用户编号" align="center" key="userId" prop="userId" v-if="item.visible && item.label=='用户编号'" />
                <el-table-column label="用户名称" align="center" key="userName" prop="userName" v-if="item.visible && item.label=='用户名称'"
                  :show-overflow-tooltip="true" />
                <el-table-column label="用户昵称" align="center" key="nickName" prop="nickName" v-if="item.visible && item.label=='用户昵称'"
                  :show-overflow-tooltip="true" />
                <el-table-column label="部门" align="center" key="deptName" prop="dept.deptName"
                  v-if="item.visible && item.label=='部门'" :show-overflow-tooltip="true" />
                <el-table-column label="手机号码" align="center" key="phonenumber" prop="phonenumber"
                  v-if="item.visible && item.label=='手机号码'" width="120" />
                <el-table-column label="状态" align="center" key="status" v-if="item.visible && item.label=='状态'">
                  <template slot-scope="scope">
                    <el-switch v-model="scope.row.status" active-value="0" inactive-value="1"
                      @change="handleStatusChange(scope.row)"></el-switch>
                  </template>
                </el-table-column>
              </template>
              <el-table-column label="创建时间" align="center" prop="createTime" v-if="columns[6].visible" width="160">
                <template slot-scope="scope">
                  <span>{{ parseTime(scope.row.createTime) }}</span>
                </template>
              </el-table-column>
              <el-table-column label="操作" align="center" width="180" class-name="small-padding fixed-width">
                <template slot-scope="scope" v-if="scope.row.userId !== 1">
                  <el-button type="text" @click="handleUpdate(scope.row)" v-hasPermi="['system:user:edit']">修改
                  </el-button>
                  <el-divider direction="vertical"></el-divider>
                  <el-button type="text" style="color: red;" @click="handleDelete(scope.row)"
                    v-hasPermi="['system:user:remove']">删除</el-button>
                  <el-divider direction="vertical"></el-divider>
                  <el-dropdown @command="(command) => handleCommand(command, scope.row)"
                    v-hasPermi="['system:user:resetPwd', 'system:user:edit']">
                    <span class="el-dropdown-link">
                      <el-button type="text">更多</el-button><i class="el-icon-arrow-down el-icon--right"></i>
                    </span>
                    <el-dropdown-menu slot="dropdown">
                      <el-dropdown-item command="handleResetPwd" v-hasPermi="['system:user:resetPwd']">重置密码
                      </el-dropdown-item>
                      <el-dropdown-item command="handleAuthRole" v-hasPermi="['system:user:edit']">分配角色
                      </el-dropdown-item>
                    </el-dropdown-menu>
                  </el-dropdown>
                </template>
              </el-table-column>
              <div slot="empty">
                <svg-icon icon-class="search-none" style="font-size: 64px;" />
                <p>暂无数据</p>
              </div>
            </el-table>
            <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum"
              :limit.sync="queryParams.pageSize" @pagination="getList" />
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 添加或修改用户配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="720px" append-to-body>
      <div class="dialog_box" style="height:500px;">
        <el-form ref="form" :model="form" :rules="rules" label-position="top">
          <el-row :gutter="16">
            <el-col :span="12">
              <el-form-item label="用户昵称" prop="nickName">
                <el-input v-model="form.nickName" placeholder="请输入用户昵称" maxlength="30" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="归属部门" prop="deptId">
                <treeselect v-model="form.deptId" :options="deptOptions" :show-count="true" placeholder="请选择归属部门" />
              </el-form-item>
            </el-col>

            <el-col :span="12">
              <el-form-item label="手机号码" prop="phonenumber">
                <el-input v-model="form.phonenumber" placeholder="请输入手机号码" maxlength="11" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="邮箱" prop="email">
                <el-input v-model="form.email" placeholder="请输入邮箱" maxlength="50" />
              </el-form-item>
            </el-col>

            <el-col :span="12">
              <el-form-item v-if="form.userId == undefined" label="用户名称" prop="userName">
                <el-input v-model="form.userName" placeholder="请输入用户名称" maxlength="30" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item v-if="form.userId == undefined" label="用户密码" prop="password">
                <el-input v-model="form.password" placeholder="请输入用户密码" type="password" maxlength="20" show-password />
              </el-form-item>
            </el-col>

            <el-col :span="12">
              <el-form-item label="用户性别">
                <el-select v-model="form.sex" placeholder="请选择性别" style="width: 100%;">
                  <el-option v-for="dict in dict.type.sys_user_sex" :key="dict.value" :label="dict.label"
                    :value="dict.value"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="状态">
                <el-radio-group v-model="form.status">
                  <el-radio-button v-for="dict in dict.type.sys_normal_disable" :key="dict.value" :label="dict.value">
                    {{dict.label}}
                  </el-radio-button>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="岗位">
                <el-select v-model="form.postIds" multiple placeholder="请选择岗位" style="width: 100%;">
                  <el-option v-for="item in postOptions" :key="item.postId" :label="item.postName" :value="item.postId"
                    :disabled="item.status == 1"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="角色">
                <el-select v-model="form.roleIds" multiple placeholder="请选择角色" style="width: 100%;">
                  <el-option v-for="item in roleOptions" :key="item.roleId" :label="item.roleName" :value="item.roleId"
                    :disabled="item.status == 1"></el-option>
                </el-select>
              </el-form-item>
            </el-col>

            <el-col :span="24">
              <el-form-item label="备注">
                <el-input v-model="form.remark" type="textarea" :rows="3" placeholder="请输入内容"></el-input>
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

    <!-- 用户导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload class="upload-model" ref="upload" :limit="1" accept=".xlsx, .xls" :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport" :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress" :on-success="handleFileSuccess" :auto-upload="false" drag>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip" style="margin-bottom: 10px;">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport" /> 是否更新已经存在的用户数据
          </div>
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;"
            @click="importTemplate">下载模板</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {
    listUser,
    getUser,
    delUser,
    addUser,
    updateUser,
    resetUserPwd,
    changeUserStatus
  } from "@/api/system/user";
  import {
    getToken
  } from "@/utils/auth";
  import {
    treeselect
  } from "@/api/system/dept";
  import Treeselect from "@riophae/vue-treeselect";
  import "@riophae/vue-treeselect/dist/vue-treeselect.css";

  export default {
    name: "User",
    dicts: ['sys_normal_disable', 'sys_user_sex'],
    components: {
      Treeselect
    },
    data() {
      return {
        advanced: false,
        tableKey: 1,
        tableHeight: this.getInitTableHeight(8),
        treeHeight: this.getInitTableHeight(90),
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
        // 用户表格数据
        userList: null,
        // 弹出层标题
        title: "",
        // 部门树选项
        deptOptions: undefined,
        // 是否显示弹出层
        open: false,
        // 部门名称
        deptName: undefined,
        // 默认密码
        initPassword: undefined,
        // 日期范围
        dateRange: [],
        // 岗位选项
        postOptions: [],
        // 角色选项
        roleOptions: [],
        // 表单参数
        form: {},
        defaultProps: {
          children: "children",
          label: "label"
        },
        // 用户导入参数
        upload: {
          // 是否显示弹出层（用户导入）
          open: false,
          // 弹出层标题（用户导入）
          title: "",
          // 是否禁用上传
          isUploading: false,
          // 是否更新已经存在的用户数据
          updateSupport: 0,
          // 设置上传的请求头部
          headers: {
            Authorization: "Bearer " + getToken()
          },
          // 上传的地址
          url: process.env.VUE_APP_BASE_API + "/system/user/importData"
        },
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          userName: undefined,
          phonenumber: undefined,
          status: undefined,
          deptId: undefined
        },
        // 列信息
        columns: [{
            key: 0,
            label: `用户编号`,
            visible: true
          },
          {
            key: 1,
            label: `用户名称`,
            visible: true
          },
          {
            key: 2,
            label: `用户昵称`,
            visible: true
          },
          {
            key: 3,
            label: `部门`,
            visible: true
          },
          {
            key: 4,
            label: `手机号码`,
            visible: true
          },
          {
            key: 5,
            label: `状态`,
            visible: true
          },
          {
            key: 6,
            label: `创建时间`,
            visible: true
          }
        ],
        // 表单校验
        rules: {
          userName: [{
              required: true,
              message: "用户名称不能为空",
              trigger: "blur"
            },
            {
              min: 2,
              max: 20,
              message: '用户名称长度必须介于 2 和 20 之间',
              trigger: 'blur'
            }
          ],
          nickName: [{
            required: true,
            message: "用户昵称不能为空",
            trigger: "blur"
          }],
          password: [{
              required: true,
              message: "用户密码不能为空",
              trigger: "blur"
            },
            {
              min: 5,
              max: 20,
              message: '用户密码长度必须介于 5 和 20 之间',
              trigger: 'blur'
            }
          ],
          email: [{
            type: "email",
            message: "请输入正确的邮箱地址",
            trigger: ["blur", "change"]
          }],
          phonenumber: [{
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur"
          }]
        }
      };
    },
    watch: {
      // 根据名称筛选部门树
      deptName(val) {
        this.$refs.tree.filter(val);
      }
    },
    created() {
      this.getList();
      this.getTreeselect();
      this.getConfigKey("sys.user.initPassword").then(response => {
        this.initPassword = response.msg;
      });
    },
    methods: {
      showSearchFun(isShowSearch){
        this.showSearch = isShowSearch
        let oldHeight = this.$refs.queryForm.$el.offsetHeight
        if(!isShowSearch){
            //当前是显示状态
            oldHeight = oldHeight + 12
        }else{
            oldHeight = oldHeight - 12
        }
        this.$nextTick(() => (
           this.tableHeight = this.$refs.tableRef.$el.offsetHeight - (this.$refs.queryForm.$el.offsetHeight-oldHeight)
        ))
      },
      toggleAdvanced() {
        const oldHeight = this.$refs.queryForm.$el.offsetHeight
        this.advanced = !this.advanced
        this.$nextTick(() => (
           this.tableHeight = this.$refs.tableRef.$el.offsetHeight - (this.$refs.queryForm.$el.offsetHeight-oldHeight)
        ))
      },
      /** 查询用户列表 */
      getList() {
        this.loading = true;
        listUser(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.userList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      /** 查询部门下拉树结构 */
      getTreeselect() {
        treeselect().then(response => {
          this.deptOptions = response.data;
        });
      },
      // 筛选节点
      filterNode(value, data) {
        if (!value) return true;
        return data.label.indexOf(value) !== -1;
      },
      // 节点单击事件
      handleNodeClick(data) {
        this.queryParams.deptId = data.id;
        this.handleQuery();
      },
      // 用户状态修改
      handleStatusChange(row) {
        let text = row.status === "0" ? "启用" : "停用";
        this.$modal.confirm('确认要"' + text + '""' + row.userName + '"用户吗？').then(function() {
          return changeUserStatus(row.userId, row.status);
        }).then(() => {
          this.$modal.msgSuccess(text + "成功");
        }).catch(function() {
          row.status = row.status === "0" ? "1" : "0";
        });
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          userId: undefined,
          deptId: undefined,
          userName: undefined,
          nickName: undefined,
          password: undefined,
          phonenumber: undefined,
          email: undefined,
          sex: undefined,
          status: "0",
          remark: undefined,
          postIds: [],
          roleIds: []
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
        this.dateRange = [];
        this.resetForm("queryForm");
        this.handleQuery();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.userId);
        this.single = selection.length != 1;
        this.multiple = !selection.length;
      },
      // 更多操作触发
      handleCommand(command, row) {
        switch (command) {
          case "handleResetPwd":
            this.handleResetPwd(row);
            break;
          case "handleAuthRole":
            this.handleAuthRole(row);
            break;
          default:
            break;
        }
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.getTreeselect();
        getUser().then(response => {
          this.postOptions = response.posts;
          this.roleOptions = response.roles;
          this.open = true;
          this.title = "添加用户";
          this.form.password = this.initPassword;
        });
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        this.getTreeselect();
        const userId = row.userId || this.ids;
        getUser(userId).then(response => {
          this.form = response.data;
          this.postOptions = response.posts;
          this.roleOptions = response.roles;
          this.form.postIds = response.postIds;
          this.form.roleIds = response.roleIds;
          this.open = true;
          this.title = "修改用户";
          this.form.password = "";
        });
      },
      /** 重置密码按钮操作 */
      handleResetPwd(row) {
        this.$prompt('请输入"' + row.userName + '"的新密码', "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          closeOnClickModal: false,
          inputPattern: /^.{5,20}$/,
          inputErrorMessage: "用户密码长度必须介于 5 和 20 之间"
        }).then(({
          value
        }) => {
          resetUserPwd(row.userId, value).then(response => {
            this.$modal.msgSuccess("修改成功，新密码是：" + value);
          });
        }).catch(() => {});
      },
      /** 分配角色操作 */
      handleAuthRole: function(row) {
        const userId = row.userId;
        this.$router.push("/system/user-auth/role/" + userId);
      },
      /** 提交按钮 */
      submitForm: function() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.userId != undefined) {
              updateUser(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addUser(this.form).then(response => {
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
        const userIds = row.userId || this.ids;
        this.$modal.confirm('是否确认删除用户编号为"' + userIds + '"的数据项？').then(function() {
          return delUser(userIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('system/user/export', {
          ...this.queryParams
        }, `user_${new Date().getTime()}.xlsx`)
      },
      /** 导入按钮操作 */
      handleImport() {
        this.upload.title = "用户导入";
        this.upload.open = true;
      },
      /** 下载模板操作 */
      importTemplate() {
        this.download('system/user/importTemplate', {}, `user_template_${new Date().getTime()}.xlsx`)
      },
      // 文件上传中处理
      handleFileUploadProgress(event, file, fileList) {
        this.upload.isUploading = true;
      },
      // 文件上传成功处理
      handleFileSuccess(response, file, fileList) {
        this.upload.open = false;
        this.upload.isUploading = false;
        this.$refs.upload.clearFiles();
        this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response
          .msg + "</div>", "导入结果", {
            dangerouslyUseHTMLString: true
          });
        this.getList();
      },
      // 提交上传文件
      submitFileForm() {
        this.$refs.upload.submit();
      }
    }
  };
</script>
<style lang="scss">
 .aidex-tree-box{
   height: calc(100vh - 112px);
   border: 0;
 }
.aidex-tool-box{
   border: 0!important;
 }
 .upload-model{
   text-align: center;
   padding-top: 10px;
 }
 .search-table-box .aidex-tree-box .el-card__body{
 	padding:12px 0;
 }
</style>
