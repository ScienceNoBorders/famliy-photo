<template>
  <el-card>
    <el-tabs v-model="activeName">
      <el-tab-pane label="基本信息" name="basic">
        <basic-info-form ref="basicInfo" :info="info" />
      </el-tab-pane>
      <el-tab-pane label="字段信息" name="columnInfo">
        <el-table ref="dragTable" :data="columns" row-key="columnId" :max-height="tableHeight" style="width: 100%">
          <el-table-column type="index" min-width="50" />
          <el-table-column header-align="center" label="字段">
            <el-table-column type="index" min-width="50" class-name="allowDrag">
              <template slot-scope="scope">
                <i class="el-icon-rank"></i>
              </template>
            </el-table-column>
            <el-table-column
              label="字段列名"
              prop="columnName"
              min-width="130"
              :show-overflow-tooltip="true"
            />
            <el-table-column
              label="物理类型"
              prop="columnType"
              min-width="130"
              :show-overflow-tooltip="true"
            />
            <el-table-column label="Java类型" min-width="130">
              <template slot-scope="scope">
                <el-select v-model="scope.row.javaType" :disabled="checkDisabledColumn(scope.row,'')" @visible-change="javaTypeVisible(scope.row)"
                @change="javaTypeChange(scope.row)">
                  <el-option v-for="value in javaTypeList" :key="value" :label="value"
                    :value="value">
                  </el-option>
                </el-select>
              </template>
            </el-table-column>
            <el-table-column label="java属性" min-width="130">
              <template slot-scope="scope">
                <el-input v-model="scope.row.javaField" :disabled="checkDisabledColumn(scope.row,'')"></el-input>
              </template>
            </el-table-column>
            <el-table-column label="字段描述" min-width="140">
              <template slot-scope="scope">
                <el-input v-model="scope.row.columnComment" :disabled="checkDisabledColumn(scope.row,'')"></el-input>
              </template>
            </el-table-column>
          </el-table-column>

          <el-table-column header-align="center" label="表单">
            <el-table-column label="插入" min-width="50" align="center">
              <template slot-scope="scope">
                <el-checkbox true-label="1" v-model="scope.row.isInsert" :disabled="checkDisabledColumn(scope.row,'')"></el-checkbox>
              </template>
            </el-table-column>
            <el-table-column label="编辑" min-width="50" align="center">
              <template slot-scope="scope">
                <el-checkbox true-label="1" v-model="scope.row.isEdit" :disabled="checkDisabledColumn(scope.row,'')"></el-checkbox>
              </template>
            </el-table-column>
            <el-table-column label="唯一性" min-width="70" align="center">
              <template slot-scope="scope">
                <el-checkbox true-label="1" v-model="scope.row.isUnique" :disabled="checkDisabledColumn(scope.row,'')"></el-checkbox>
              </template>
            </el-table-column>
            <el-table-column label="必填" min-width="50" align="center">
              <template slot-scope="scope">
                <el-checkbox true-label="1" v-model="scope.row.isRequired" :disabled="checkDisabledColumn(scope.row,'')"></el-checkbox>
              </template>
            </el-table-column>
            <el-table-column label="显示类型" min-width="130">
              <template slot-scope="scope">
                <el-select v-model="scope.row.htmlType" @visible-change="htmlTypeVisible(scope.row)" :disabled="checkDisabledColumnType(scope.row,'')">
                  <el-option v-for="dict in htmlTypeList" :key="dict.value" :label="dict.label"
                    :value="dict.value">
                  </el-option>
                </el-select>
              </template>
            </el-table-column>
            <el-table-column label="列数" min-width="160">
              <template slot="header" slot-scope="scope">
                <span>列数</span>
                <el-tooltip placement="top">
                  <div slot="content">
                    该选项表示24栅格中<br>摆放控件个数,通过右侧选项可快速将列表列数进行统一修改
                  </div>
                  <i class="el-icon-question"></i>
                </el-tooltip>
                <el-select style="width: 80px;" v-model="colSpanValue">
                  <el-option :value="1" label="一列">一列</el-option>
                  <el-option :value="2" label="二列">二列</el-option>
                  <el-option :value="3" label="三列">三列</el-option>
                  <el-option :value="4" label="四列">四列</el-option>
                </el-select>
              </template>
              <template slot-scope="scope">
                <el-select v-model="scope.row.colSpan" filterable :disabled="checkDisabledColumn(scope.row,'')">
                  <el-option :value="1" label="一列/24栅格" v-if="colSpanValue>=1">一列/24栅格</el-option>
                  <el-option :value="2" label="二列/24栅格" v-if="colSpanValue>=2">二列/24栅格</el-option>
                  <el-option :value="3" label="三列/24栅格" v-if="colSpanValue>=3">三列/24栅格</el-option>
                  <el-option :value="4" label="四列/24栅格" v-if="colSpanValue>=4">四列/24栅格</el-option>
                </el-select>
              </template>
            </el-table-column>
            <el-table-column label="新行" min-width="50" align="center">
              <template slot-scope="scope">
                <el-checkbox true-label="1" v-model="scope.row.isNewRow" :disabled="checkDisabledColumn(scope.row,'')"></el-checkbox>
              </template>
            </el-table-column>
            <el-table-column label="字典类型" min-width="150">
              <template slot-scope="scope">
                <el-select v-model="scope.row.dictType" clearable filterable placeholder="请选择" :disabled="checkDisabledColumn(scope.row,'dictType')">
                  <el-option
                    v-for="dict in dictOptions"
                    :key="dict.dictType"
                    :label="dict.dictName"
                    :value="dict.dictType">
                    <span style="float: left">{{ dict.dictName }}</span>
                    <span style="float: right; color: #8492a6; font-size: 13px">{{ dict.dictType }}</span>
                </el-option>
                </el-select>
              </template>
            </el-table-column>
            <el-table-column label="字段校验" min-width="130">
              <template slot-scope="scope">
                <el-select v-model="scope.row.colCheck" filterable clearable placeholder="请选择" :disabled="checkDisabledColumn(scope.row,'colCheck')">
                  <el-option value="email" label="邮箱">邮箱</el-option>
                  <el-option value="idCard" label="身份证">身份证</el-option>
                  <el-option value="positiveInteger" label="正整数">正整数</el-option>
                  <el-option value="number" label="数值">数值</el-option>
                  <el-option value="abc123_" label="字母数字下划线">字母数字下划线</el-option>
                  <el-option value="phone" label="手机号">手机号</el-option>
                  <el-option value="tel" label="固定电话">固定电话</el-option>
                  <el-option value="telPhone" label="手机或电话">手机或电话</el-option>
                  <el-option value="integer" label="整数">整数</el-option>
                  <el-option value="postCode" label="邮编">邮编</el-option>
                  <el-option value="ipv4" label="邮编">IPv4</el-option>
                  <el-option value="ipv6" label="IPv6">IPv6</el-option>
                  <el-option value="qq" label="QQ号">QQ号</el-option>
                </el-select>
              </template>
            </el-table-column>
          </el-table-column>

          <el-table-column header-align="center" label="列表">
            <el-table-column label="列表" min-width="50">
              <template slot-scope="scope">
                <el-checkbox true-label="1" false-label="0" v-model="scope.row.isList" :disabled="checkDisabledColumn(scope.row,'')"></el-checkbox>
              </template>
            </el-table-column>
            <el-table-column label="导入导出" min-width="80" align="center">
              <template slot-scope="scope">
                <el-checkbox true-label="1" v-model="scope.row.isImportExport" :disabled="checkDisabledColumn(scope.row,'')"></el-checkbox>
              </template>
            </el-table-column>
            <el-table-column label="查询" min-width="50">
              <template slot-scope="scope">
                <el-checkbox true-label="1" v-model="scope.row.isQuery" :disabled="checkDisabledColumn(scope.row,'')"></el-checkbox>
              </template>
            </el-table-column>
            <el-table-column label="排序" min-width="70">
              <template slot="header" slot-scope="scope">
                <span>排序</span>
                <el-tooltip placement="top">
                  <div slot="content">
                    该选项表示是否在列上生成排序功能,树和树表格不支持该功能
                  </div>
                  <i class="el-icon-question"></i>
                </el-tooltip>
              </template>
              <template slot-scope="scope">
                <el-checkbox true-label="1" v-model="scope.row.isColumnSort" :disabled="checkDisabledColumn(scope.row,'')"></el-checkbox>
              </template>
            </el-table-column>
            <el-table-column label="查询方式" min-width="100">
              <template slot-scope="scope">
                <el-select v-model="scope.row.queryType" :disabled="checkDisabledColumn(scope.row,'')">
                  <el-option label="=" value="EQ" />
                  <el-option label="!=" value="NE" />
                  <el-option label=">" value="GT" />
                  <el-option label=">=" value="GTE" />
                  <el-option label="<" value="LT" />
                  <el-option label="<=" value="LTE" />
                  <el-option label="LIKE" value="LIKE" />
                  <el-option label="BETWEEN" value="BETWEEN" />
                </el-select>
              </template>
            </el-table-column>
            <el-table-column label="对齐方式" min-width="120" :show-overflow-tooltip="true">
              <template slot="header" slot-scope="scope">
                对齐方式<el-tooltip placement="top">
                  <div slot="content">自动包含规范，可根据以下规则自动设置：居中、居左、居右，列表页面遵循一下对齐格式：<br/>
                    居中显示：短小字符居中（用户名）、通用代码、日期时间、数值位数相等的字段（如：编码、编号）、整数型数字（如年龄，个数）<br>
                    居左：比较长的字符居左（备注等），部门名称、名称、标题<br>
                    居右：货币或带小数点的数字居右，如带小数点的数字，数量、百分数
                  </div>
                  <i class="el-icon-question"></i>
                </el-tooltip>
              </template>
              <template slot-scope="scope">
                <el-select v-model="scope.row.alignType" filterable clearable placeholder="请选择" :disabled="checkDisabledColumn(scope.row,'')">
                  <el-option value="left" label="居左">居左</el-option>
                  <el-option value="center" label="居中">居中</el-option>
                  <el-option value="right" label="居右">居右</el-option>
                </el-select>
              </template>
            </el-table-column>
          </el-table-column>

        </el-table>
      </el-tab-pane>
      <el-tab-pane label="生成信息" name="genInfo">
        <gen-info-form ref="genInfo" :info="info" :tables="tables" :menus="menus" :genConfigTemplateOptions="genConfigTemplateOptions"/>
      </el-tab-pane>
    </el-tabs>
    <el-form label-width="80px">
      <el-form-item style="text-align: center;margin-left:-100px;margin-top:10px;">
        <el-button type="primary" @click="submitForm()">提交</el-button>
        <el-button @click="close()">返回</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
import { getGenTable, updateGenTable } from "@/api/tool/genExtend";
import { listGenConfigTemplate } from '@/api/tool/genConfigTemplate'
import { optionselect as getDictOptionselect } from "@/api/system/dict/type";
import { listMenu as getMenuTreeselect } from "@/api/system/menu";
import basicInfoForm from "./basicInfoForm";
import genInfoForm from "./genInfoForm";
import Sortable from 'sortablejs'

export default {
  name: "GenEdit",
  components: {
    basicInfoForm,
    genInfoForm
  },
  data() {
    return {
      // 选中选项卡的 name
      activeName: "columnInfo",
      // 表格的高度
      tableHeight: document.documentElement.scrollHeight - 245 + "px",
      // 表信息
      tables: [],
      // 表列信息
      columns: [],
      // 字典信息
      dictOptions: [],
      // java字段类型
      javaTypeList: ['Long', 'String', 'Integer', 'Double', 'BigDecimal', 'Date', 'DateTime'],
      // html控件类型
      htmlTypeList: [
        { 'label': '文本框', 'value': 'input' },
        { 'label': '数字框', 'value': 'number' },
        { 'label': '文本域', 'value': 'textarea' },
        { 'label': '下拉框', 'value': 'select' },
        { 'label': '下拉框（多选）', 'value': 'selectMultiple' },
        { 'label': '单选框', 'value': 'radio' },
        { 'label': '单选按钮', 'value': 'radioButton' },
        { 'label': '复选框', 'value': 'checkbox' },
        { 'label': '日期控件', 'value': 'datetime' },
        { 'label': '时间', 'value': 'time' },
        { 'label': '图片上传', 'value': 'imageUpload' },
        { 'label': '文件上传', 'value': 'fileUpload' },
        { 'label': '富文本控件', 'value': 'editor' },
        { 'label': '用户控件', 'value': 'user' },
        { 'label': '部门控件', 'value': 'dept' }
      ],
      // 每行显示控件数
      colSpanValue: 2,
      // 个人模板列表
      genConfigTemplateOptions: [],
      // 菜单信息
      menus: [],
      // 表详细信息
      info: {}
    };
  },
  watch: {
    colSpanValue (val, oldVal) {
      this.columns.forEach(function (record) {
        if (record.colSpan !== 0) {
          record.colSpan = 1
        }
      })
    }
  },
  created() {
    const tableId = this.$route.params && this.$route.params.tableId;
    if (tableId) {
      // 获取表详细信息
      getGenTable(tableId).then(res => {
        this.columns = res.data.rows;
        this.info = res.data.info;
        if (this.info.attachOption === null) {
          this.info.attachOption = '0'
        }
        if (this.info.disableEnableOption === null) {
          this.info.disableEnableOption = '0'
        }
        if (this.info.saveAndAddOption === null) {
          this.info.saveAndAddOption = '0'
        }
        if (this.info.copyRecordOption === null) {
          this.info.copyRecordOption = '0'
        }
        this.tables = res.data.tables;
        this.colSpanValue = 2;
        if(null != res.data.info.colSpans){
          this.colSpanValue = res.data.info.colSpans
        }
      });
      /** 查询字典下拉列表 */
      getDictOptionselect().then(response => {
        this.dictOptions = response.data;
      });
      /** 查询菜单下拉列表 */
      getMenuTreeselect().then(response => {
        this.menus = this.handleTree(response.data, "menuId");
      });
      /** 个人模板列表 */
      listGenConfigTemplate().then(response => {
        this.genConfigTemplateOptions = response.rows;
      });
    }
  },
  methods: {
    /** 提交按钮 */
    submitForm() {
      const basicForm = this.$refs.basicInfo.$refs.basicInfoForm;
      const genForm = this.$refs.genInfo.$refs.genInfoForm;
      Promise.all([basicForm, genForm].map(this.getFormPromise)).then(res => {
        const validateResult = res.every(item => !!item);
        if (validateResult) {
          const genTable = Object.assign({}, basicForm.model, genForm.model);
          genTable.columns = this.columns;
          genTable.params = {
            treeCode: genTable.treeCode,
            treeName: genTable.treeName,
            treeParentCode: genTable.treeParentCode,
            parentMenuId: genTable.parentMenuId,
            menuIcon: genTable.menuIcon,
            disableEnableOption: genTable.disableEnableOption,
            saveAndAddOption: genTable.saveAndAddOption,
            copyRecordOption: genTable.copyRecordOption
          };
          genTable.colSpans = this.colSpanValue
          updateGenTable(genTable).then(res => {
            this.$modal.msgSuccess(res.msg);
            if (res.code === 200) {
              this.close();
            }
          });
        } else {
          this.$modal.msgError("表单校验未通过，请重新检查提交内容");
        }
      });
    },
    getFormPromise(form) {
      return new Promise(resolve => {
        form.validate(res => {
          resolve(res);
        });
      });
    },
    /** 关闭按钮 */
    close() {
      const obj = { path: "/tool/genExtend", query: { t: Date.now(), pageNum: this.$route.query.pageNum } };
      this.$tab.closeOpenPage(obj);
    },
    /** 校验行是否可以编辑 */
    checkDisabledColumn (record, type) {
      let superColumn = record.superColumn
      const htmlType = record.htmlType
      if (type === 'dictType') {
        if (htmlType !== 'select' && htmlType !== 'selectMultiple' && htmlType !== 'radio' && htmlType !== 'radioButton' && htmlType !== 'checkbox') {
          superColumn = true
          // 清除数据
          record.dictType = ''
        }
      } else if (type === 'colCheck') {
        if (htmlType !== 'input') {
          superColumn = true
          // 清除数据
          record.colCheck = ''
        }
      }
      return superColumn
    },
    /** 校验行是否可以编辑 */
    checkDisabledColumnType (record, type) {
       let superColumn = record.superColumn
       const htmlType = record.htmlType
       if (type === 'dictType') {
         if (htmlType !== 'select' && htmlType !== 'selectMultiple' && htmlType !== 'radio' && htmlType !== 'radioButton' && htmlType !== 'checkbox') {
           superColumn = true
           // 清除数据
           record.dictType = ''
         }
       }
       // 如果html类型不能与input，则需要将校验列只读
       if (htmlType !== 'input') {
          record.colCheck = ''
       }
       const queryType = record.queryType
       if (htmlType === 'input' || htmlType === 'textarea') {
         // 设置字段居中方式
         record.alignType = 'left'
         if (queryType !== 'EQ' && queryType !== 'LIKE') {
           record.queryType = 'LIKE'
         }
       } else if (htmlType === 'number') {
         // 设置字段居中方式
         record.alignType = 'right'
         if (queryType === 'LIKE') {
           record.queryType = 'EQ'
         }
       } else if (htmlType === 'select' || htmlType === 'radio' || htmlType === 'radioButton' || htmlType === 'checkbox' || htmlType === 'selectMultiple') {
         // 设置字段居中方式
         record.alignType = 'center'
         if (queryType === 'LIKE') {
           record.queryType = 'EQ'
         }
       } else if (htmlType === 'time' || htmlType === 'datetime') {
         if (queryType === 'LIKE') {
           record.queryType = 'EQ'
         }
         // 设置字段居中方式
         record.alignType = 'center'
       }
      return superColumn
    },
    /** java类型绑定 */
    javaTypeVisible (record) {
      // 根据页面加载的java类型初始化下拉框，页面加载的值为后台计算好的
      const javaType = record.javaType
      if (javaType === 'Long' || javaType === 'Integer' || javaType === 'Double' || javaType === 'BigDecimal') {
         this.javaTypeList = ['Long', 'Integer', 'Double', 'BigDecimal']
      } else if (javaType === 'Date' || javaType === 'DateTime') {
         this.javaTypeList = ['Date', 'DateTime']
      } else {
         this.javaTypeList = ['String']
      }
    },
    /** java类型与html控件类型绑定 */
    htmlTypeVisible (record) {
      const javaType = record.javaType
      if (javaType === 'Long' || javaType === 'Integer' || javaType === 'Double' || javaType === 'BigDecimal') {
         this.htmlTypeList = [{ 'label': '数字框', 'value': 'number' }]
      } else if (javaType === 'Date' || javaType === 'DateTime') {
         this.htmlTypeList = [{ 'label': '日期控件', 'value': 'datetime' }, { 'label': '时间控件','value': 'time' }]
      } else {
          this.htmlTypeList = [
            { 'label': '文本框', 'value': 'input' },
            { 'label': '文本域', 'value': 'textarea' },
            { 'label': '下拉框', 'value': 'select' },
            { 'label': '下拉框（多选）', 'value': 'selectMultiple' },
            { 'label': '单选框', 'value': 'radio' },
            { 'label': '单选按钮', 'value': 'radioButton' },
            { 'label': '复选框', 'value': 'checkbox' },
            { 'label': '图片上传', 'value': 'imageUpload' },
            { 'label': '文件上传', 'value': 'fileUpload' },
            { 'label': '富文本控件', 'value': 'editor' },
            { 'label': '用户控件', 'value': 'user' },
            { 'label': '部门控件', 'value': 'dept' }
          ]
      }
    },
    /** 设置数据字典是否可选择 */
    dictTypeVisible (record) {
        const htmlType = record.htmlType
        if (htmlType !== 'select' && htmlType !== 'selectMultiple' && htmlType !== 'radio' && htmlType !== 'radioButton' && htmlType !== 'checkbox') {
          this.$message.error('请选择')
          return false
        }
    }
  },
  mounted() {
    const el = this.$refs.dragTable.$el.querySelectorAll(".el-table__body-wrapper > table > tbody")[0];
    const sortable = Sortable.create(el, {
      handle: ".allowDrag",
      onEnd: evt => {
        const targetRow = this.columns.splice(evt.oldIndex, 1)[0];
        this.columns.splice(evt.newIndex, 0, targetRow);
        for (let index in this.columns) {
          this.columns[index].sort = parseInt(index) * 10;
        }
      }
    });
  }
};
</script>
