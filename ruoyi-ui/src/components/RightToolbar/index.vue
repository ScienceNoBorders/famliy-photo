<template>
  <div class="top-right-btn">
    <el-row >
      <el-tooltip class="item" effect="dark" :content="showSearch ? '隐藏搜索' : '显示搜索'" placement="top">
        <svg-icon icon-class="search-2-line" @click="toggleSearch()"/>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="刷新" placement="top">
        <svg-icon icon-class="loader-3-line"  @click="refresh()"/>
      </el-tooltip>
      <el-tooltip class="item" effect="dark" content="列设置" placement="top" v-if="columns">
        <el-dropdown trigger="click">
          <svg-icon icon-class="settings-3-line" />
          <el-dropdown-menu class="tableselect" slot="dropdown" style="width: 220px;">
            <span class="title" style="font-size:14px;display: block;padding: 8px 14px;border-bottom: 1px solid #eee;">列设置</span>
            <el-button type="text" class="tableselect_btn">重置</el-button>
            <el-tree draggable :data="columns"   :props="defaultProps" :allow-drop="allowDrop" @node-drop="handleDrop">
              <span class="tree-table-setting" slot-scope="{ node, data }">
                <svg-icon icon-class="drag-move-2-fill" class="drag-move" />
                <el-checkbox :checked="node.visible" @change="monitorChange($event,node.label)" :label="node.label"></el-checkbox>
              </span>
            </el-tree>
          </el-dropdown-menu>
        </el-dropdown>
      </el-tooltip>
    </el-row>
    <el-dialog :title="title" :visible.sync="open" append-to-body>
      <el-transfer
        :titles="['显示', '隐藏']"
        v-model="value"
        :data="columns"
        @change="dataChange"
      ></el-transfer>
    </el-dialog>
  </div>
</template>
<script>
export default {
  name: "RightToolbar",
  data() {
    return {
      // 显隐数据
      value: [],
      // 弹出层标题
      title: "显示/隐藏",
      // 是否显示弹出层
      open: false,
      // 列设置中 tree配置
      defaultProps: {
        children: 'children',
        label: 'label'
      }
    };
  },
  props: {
    showSearch: {
      type: Boolean,
      default: true,
    },
    columns: {
      type: Array,
    },
    tableKey: {
      type: Number
    }
  },
  mounted(){
    const localColumns = this.$cache.local.getJSON('jsonKey');
    if (null != localColumns && typeof (localColumns) != 'undefined'){
      //this.columns = localColumns;
      this.$emit("update:columns", this.$cache.local.getJSON('jsonKey'));
    }
  },
  created() {
    // 显隐列初始默认隐藏列
    for (let item in this.columns) {
      if (this.columns[item].visible === false) {
        this.value.push(parseInt(item));
      }
    }
  },
  methods: {
    // 搜索
    toggleSearch() {
      this.$emit("update:showSearch", !this.showSearch);
      this.$emit("showSearchFun", !this.showSearch);
    },
    // 刷新
    refresh() {
      this.$emit("queryTable");
    },
    // 右侧列表元素变化
    dataChange(data) {
      for (let item in this.columns) {
        const key = this.columns[item].key;
        this.columns[item].visible = !data.includes(key);
      }
    },
    monitorChange(event,label){
      this.columns.filter(item=>item.label==label)[0].visible=event
      this.$cache.local.setJSON('jsonKey', this.columns)
    },
    // 打开显隐列dialog
    showColumn() {
      this.open = true;
    },
    allowDrop(draggingNode, dropNode, type) {
      // 仅允许Tree节点上下拖动
      return type !== 'inner'
    },
    // Tree 拖动时更新表格
    handleDrop() {
      this.$emit("update:tableKey", Math.floor(Math.random()*3000));
      // 保存表格配置
      this.$cache.local.setJSON('jsonKey', this.columns)
      this.$emit("update:columns", this.columns);
    }
  }
};
</script>
<style lang="scss" scoped>
::v-deep .el-transfer__button {
  border-radius: 50%;
  padding: 12px;
  display: block;
  margin-left: 0px;
}
::v-deep .el-transfer__button:first-child {
  margin-bottom: 10px;
}
.el-row{
  padding-top: 6px;
   vertical-align: middle;
.svg-icon{
    font-size: 20px;
    margin: 0 4px;
    color: #202d40;
    cursor: pointer;
  }
}
</style>
