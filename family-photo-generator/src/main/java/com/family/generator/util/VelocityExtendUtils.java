package com.family.generator.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.family.common.constant.GenExtendConstants;
import com.family.common.exception.ServiceException;
import com.family.common.utils.DateUtils;
import com.family.common.utils.StringUtils;
import com.family.generator.domain.GenTableColumnExtend;
import com.family.generator.domain.GenTableExtend;
import org.apache.velocity.VelocityContext;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 模板处理工具类
 * 
 * @author ruoyi
 */
public class VelocityExtendUtils
{
    /** 项目空间路径 */
    private static final String PROJECT_PATH = "main/java";

    /** mybatis空间路径 */
    private static final String MYBATIS_PATH = "main/resources/mapper";

    /** 默认上级菜单，系统工具 */
    private static final String DEFAULT_PARENT_MENU_ID = "3";

    /**
     * 默认是否有停用启用配置
     */
    private static final String DEFAULT_DISABLE_ENABLE_SET = "0";


    /**
     * 默认是否保存并继续添加配置
     */
    private static final String DEFAULT_SAVE_AND_ADD_SET = "0";

    /**
     * 默认是否有复制功能配置
     */
    private static final String DEFAULT_COPY_RECORD_SET = "0";

    /**
     * 设置模板变量信息
     *
     * @return 模板列表
     */
    public static VelocityContext prepareContext(GenTableExtend genTable)
    {

        boolean isSub = isSub(genTable);
        String tip = "该表";
        if(isSub){
            tip = "子表：" + genTable.getTableName();
        }
        //生成模板
        String tplCategory = genTable.getTplCategory();
        if (StringUtils.isEmpty(tplCategory)) {
            throw new ServiceException("请先选择" + tip + "的生成信息页签中【生成模板】");
        }
        //作者
        String functionAuthor = genTable.getFunctionAuthor();
        if (StringUtils.isEmpty(functionAuthor)) {
            throw new ServiceException(tip + "的生成信息页签中【作者】不允许为空");
        }
        //邮箱
        String functionAuthorEmail = genTable.getFunctionAuthorEmail();
        if (StringUtils.isEmpty(functionAuthorEmail)) {
            throw new ServiceException(tip + "的生成信息页签中【邮箱】不允许为空");
        }
        //工作空间路径
        String workspacePath = genTable.getWorkspacePath();
        if (StringUtils.isEmpty(workspacePath)) {
            throw new ServiceException(tip + "的生成信息页签中【工作空间路径】不允许为空");
        }
        //前端工作空间路径
        String webWorkspacePath = genTable.getWebWorkspacePath();
        if (StringUtils.isEmpty(webWorkspacePath)) {
            throw new ServiceException(tip + "的生成信息页签中【前端工作空间路径】不允许为空");
        }
        //生成模块名
        String moduleName = genTable.getModuleName();
        if (StringUtils.isEmpty(moduleName)) {
            throw new ServiceException(tip + "的生成信息页签中【生成模块名】不允许为空");
        }
        //包路径
        String packageName = genTable.getPackageName();
        if (StringUtils.isEmpty(packageName)) {
            throw new ServiceException(tip + "的生成信息页签中【包路径】不允许为空");
        }
        //生成功能名
        String functionName = genTable.getFunctionName();
        if (StringUtils.isEmpty(functionName)) {
            throw new ServiceException(tip + "的生成信息页签中【生成功能名】不允许为空");
        }
        // 生成业务名：小写
        String businessName = genTable.getBusinessName();
        if (StringUtils.isEmpty(businessName)) {
            throw new ServiceException("该表的生成信息页签中【生成业务名】不允许为空");
        }

        VelocityContext velocityContext = new VelocityContext();
        velocityContext.put("tplCategory", genTable.getTplCategory());
        velocityContext.put("tableName", genTable.getTableName());
        velocityContext.put("functionName", StringUtils.isNotEmpty(functionName) ? functionName : "【请填写功能名称】");
        velocityContext.put("ClassName", genTable.getClassName());
        velocityContext.put("className", StringUtils.uncapitalize(genTable.getClassName()));
        velocityContext.put("moduleName", genTable.getModuleName());
        velocityContext.put("BusinessName", StringUtils.capitalize(genTable.getBusinessName()));
        velocityContext.put("businessName", genTable.getBusinessName());
        velocityContext.put("basePackage", getPackagePrefix(packageName));
        velocityContext.put("packageName", packageName);
        velocityContext.put("author", genTable.getFunctionAuthor());
        velocityContext.put("functionAuthorEmail", genTable.getFunctionAuthorEmail());
        velocityContext.put("datetime", DateUtils.getDate());
        velocityContext.put("pkColumn", genTable.getPkColumn());
        velocityContext.put("importList", getImportList(genTable));
        velocityContext.put("permissionPrefix", getPermissionPrefix(moduleName, businessName));
        velocityContext.put("columns", genTable.getColumns());

        velocityContext.put("listColumns", genTable.getColumns().stream().filter(column -> column.isList()).collect(Collectors.toList()));
        velocityContext.put("requireColumns", genTable.getColumns().stream().filter(column -> column.isRequired()).collect(Collectors.toList()));
        // 如果是行编辑，则必填校验和正则校验必须是页面展示的列
        if(genTable.getTplCategory().equals(GenExtendConstants.TPL_CRUD_ROW_EDIT)){
            velocityContext.put("requireAndPatternColumns", genTable.getColumns().stream().filter(column -> (column.isList() && (column.isRequired() || StringUtils.isNotEmpty(column.getColCheck())))).collect(Collectors.toList()));
        }else{
            velocityContext.put("requireAndPatternColumns", genTable.getColumns().stream().filter(column -> (column.isRequired() || StringUtils.isNotEmpty(column.getColCheck()) )).collect(Collectors.toList()));
        }
        velocityContext.put("dictColumns", genTable.getColumns().stream().filter(column -> StringUtils.isNotEmpty(column.getDictType())).collect(Collectors.toList()));
        velocityContext.put("queryColumns", genTable.getColumns().stream().filter(column -> (column.isQuery())).collect(Collectors.toList()));
        velocityContext.put("dateColumns", genTable.getColumns().stream().filter(column -> column.getJavaType().equals(GenExtendConstants.TYPE_DATE)).collect(Collectors.toList()));
        velocityContext.put("editColumns", genTable.getColumns().stream().filter(column -> column.isEdit()).collect(Collectors.toList()));

        velocityContext.put("table", genTable);
        //列属性
        velocityContext.put("colSpans",genTable.getColSpans());
        //是否有停用启用
        velocityContext.put("hasDisableEnable", genTable.hasDisableEnable());
        //是否保存并继续添加
        velocityContext.put("hasSaveAndAddOption", genTable.hasSaveAndAddOption());
        //是否有复制功能
        velocityContext.put("hasCopyRecordOption", genTable.hasCopyRecordOption());

        //如果是主子表或者单表，设置弹出页面的尺寸
        String colSpanString = genTable.getColSpans();
        if(StringUtils.isEmpty(colSpanString)){
            colSpanString = "1";
        }
        int colspan = Integer.parseInt(colSpanString);
        if (colspan == 1) {
            velocityContext.put("modalWidth", 560);
            velocityContext.put("modalHeight", 500);
        } else if (colspan == 2) {
            velocityContext.put("modalWidth", 760);
            velocityContext.put("modalHeight", 500);
        }
        if (colspan == 3) {
            velocityContext.put("modalWidth", 960);
            velocityContext.put("modalHeight", 500);
        }
        if (colspan == 4) {
            velocityContext.put("modalWidth", 1280);
            velocityContext.put("modalHeight", 600);
        }

        //子表页面的尺寸
        if (isSub) {
            colspan = Integer.parseInt(genTable.getColSpans());
            if (colspan == 1) {
                velocityContext.put("subModalWidth", 560);
                velocityContext.put("subModalHeight", 500);
            } else if (colspan == 2) {
                velocityContext.put("subModalWidth", 760);
                velocityContext.put("subModalHeight", 500);
            }
            if (colspan == 3) {
                velocityContext.put("subModalWidth", 960);
                velocityContext.put("subModalHeight", 500);
            }
            if (colspan == 4) {
                velocityContext.put("subModalWidth", 1280);
                velocityContext.put("subModalHeight", 600);
            }
        }

        velocityContext.put("dicts", getDicts(genTable));
        setMenuVelocityContext(velocityContext, genTable);
        //转换option中信息到对象
        setTableFromOptions(genTable);
        if (GenExtendConstants.TPL_TREE.equals(tplCategory))
        {
            setTreeVelocityContext(velocityContext, genTable);
        }
        if (GenExtendConstants.TPL_SUB.equals(tplCategory))
        {
            setSubVelocityContext(velocityContext, genTable);
        }
        return velocityContext;
    }

    public static void setMenuVelocityContext(VelocityContext context, GenTableExtend genTable)
    {
        String options = genTable.getOptions();
        JSONObject paramsObj = JSON.parseObject(options);
        String parentMenuId = getParentMenuId(paramsObj);
        context.put("parentMenuId", parentMenuId);
    }

    /**
     * 设置代码生成其他选项值
     *
     * @param genTable 设置后的生成对象
     */
    public static void setTableFromOptions(GenTableExtend genTable)
    {
        JSONObject paramsObj = JSON.parseObject(genTable.getOptions());
        if (StringUtils.isNotNull(paramsObj))
        {

            String menuIcon = paramsObj.getString(GenExtendConstants.MENU_ICON);
            String disableEnableOption = paramsObj.getString(GenExtendConstants.DISABLE_ENABLE_OPTION);
            String saveAndAddOption = paramsObj.getString(GenExtendConstants.SAVE_AND_ADD_OPTION);
            String copyRecordOption = paramsObj.getString(GenExtendConstants.COPY_RECORD_OPTION);

            genTable.setMenuIcon(menuIcon);
            genTable.setDisableEnableOption(disableEnableOption);
            genTable.setSaveAndAddOption(saveAndAddOption);
            genTable.setCopyRecordOption(copyRecordOption);
        }
    }

    public static void setTreeVelocityContext(VelocityContext context, GenTableExtend genTable)
    {
        String options = genTable.getOptions();
        JSONObject paramsObj = JSON.parseObject(options);
        String treeCode = getTreecode(paramsObj);
        String treeParentCode = getTreeParentCode(paramsObj);
        String treeName = getTreeName(paramsObj);

        context.put("treeCode", treeCode);
        context.put("treeParentCode", treeParentCode);
        context.put("treeName", treeName);
        context.put("expandColumn", getExpandColumn(genTable));
        if (paramsObj.containsKey(GenExtendConstants.TREE_PARENT_CODE))
        {
            context.put("tree_parent_code", paramsObj.getString(GenExtendConstants.TREE_PARENT_CODE));
        }
        if (paramsObj.containsKey(GenExtendConstants.TREE_NAME))
        {
            context.put("tree_name", paramsObj.getString(GenExtendConstants.TREE_NAME));
        }
    }

    public static void setSubVelocityContext(VelocityContext context, GenTableExtend genTable)
    {
        GenTableExtend subTable = genTable.getSubTable();
        String subTableName = genTable.getSubTableName();
        String subTableFkName = genTable.getSubTableFkName();
        String subClassName = genTable.getSubTable().getClassName();
        String subTableFkClassName = StringUtils.convertToCamelCase(subTableFkName);

        context.put("subTable", subTable);
        context.put("subTableName", subTableName);
        context.put("subTableFkName", subTableFkName);
        context.put("subTableFkClassName", subTableFkClassName);
        context.put("subTableFkclassName", StringUtils.uncapitalize(subTableFkClassName));
        context.put("subClassName", subClassName);
        context.put("subclassName", StringUtils.uncapitalize(subClassName));
        context.put("subImportList", getImportList(genTable.getSubTable()));
    }

    /**
     * 获取模板信息
     *
     * @return 模板列表
     */
    public static List<String> getTemplateList(String tplCategory)
    {
        List<String> templates = new ArrayList<String>();
        if (GenExtendConstants.TPL_CRUD.equals(tplCategory))
        {
            templates.add("vmextend/crud/java/domain.java.vm");
            templates.add("vmextend/crud/java/mapper.java.vm");
            templates.add("vmextend/crud/java/service.java.vm");
            templates.add("vmextend/crud/java/serviceImpl.java.vm");
            templates.add("vmextend/crud/java/controller.java.vm");
            templates.add("vmextend/crud/xml/mapper.xml.vm");
            templates.add("vmextend/crud/sql/sql.vm");
            templates.add("vmextend/crud/js/api.js.vm");
            templates.add("vmextend/crud/vue/index.vue.vm");
        }
        else if (GenExtendConstants.TPL_TREE.equals(tplCategory))
        {
            templates.add("vm/vue/index-tree.vue.vm");
        }
        else if (GenExtendConstants.TPL_SUB.equals(tplCategory))
        {
            templates.add("vm/vue/index.vue.vm");
            templates.add("vm/java/sub-domain.java.vm");
        }
        return templates;
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, GenTableExtend genTable)
    {
        // 文件名称
        String fileName = "";
        // 包路径
        String packageName = genTable.getPackageName();
        // 模块名
        String moduleName = genTable.getModuleName();
        // 大写类名
        String className = genTable.getClassName();
        // 业务名称
        String businessName = genTable.getBusinessName();
        //该字段用于控制模块名（主子表时，子表的该值为主表的）
        String businessNameLowerCase = genTable.getBusinessName().toLowerCase();

        String BusinessName = StringUtils.capitalize(vueFileNameFormat(genTable.getTableName()));

        boolean isSub = VelocityExtendUtils.isSub(genTable);

        String workSpace = genTable.getWorkspacePath();
        String javaPath = workSpace + File.separator + PROJECT_PATH + File.separator + StringUtils.replace(packageName, ".", File.separator);
        String vueWorkspacePath = genTable.getWebWorkspacePath();
        if (template.contains("domain.java.vm")) {
            fileName = StringUtils.format("{}/domain/{}.java", javaPath, className);
        }
        if (template.contains("sub-domain.java.vm") && (StringUtils.equals(GenExtendConstants.TPL_SUB, genTable.getTplCategory()) || StringUtils.equals(GenExtendConstants.TPL_SUB_LEFT_RIGHT, genTable.getTplCategory()))) {
            fileName = StringUtils.format("{}/domain/{}.java", javaPath, genTable.getSubTable().getClassName());
        } else if (template.contains("mapper.java.vm")) {
            fileName = StringUtils.format("{}/mapper/{}Mapper.java", javaPath, className);
        } else if (template.contains("service.java.vm")) {
            fileName = StringUtils.format("{}/service/{}Service.java", javaPath, className);
        } else if (template.contains("serviceImpl.java.vm")) {
            fileName = StringUtils.format("{}/service/impl/{}ServiceImpl.java", javaPath, className);
        } else if (template.contains("controller.java.vm")) {
            fileName = StringUtils.format("{}/controller/{}Controller.java", javaPath, className);
        } else if (template.contains("mapper.xml.vm")) {
            fileName = StringUtils.format("{}/mapper/{}Mapper.xml", javaPath, className);
        } else if (template.contains("sql.vm")) {
            fileName = "sql" + "/" + BusinessName + "Menu.sql";
        } else if (template.contains("api.js.vm")) {
            fileName = StringUtils.format("{}/src/api/{}/{}.js", vueWorkspacePath, moduleName, StringUtils.uncapitalize(vueFileNameFormat(genTable.getTableName())));
        } else if (template.contains("sub_api.js.vm")) {
            fileName = StringUtils.format("{}/src/api/{}/{}.js", vueWorkspacePath, moduleName, StringUtils.uncapitalize(vueFileNameFormat(genTable.getTableName())));
        } else if (template.contains("tree-index.vue.vm")) {
            fileName = StringUtils.format("{}/src/views/{}/{}/index.vue", vueWorkspacePath, moduleName, businessNameLowerCase);
        } else if (template.contains("tree.vue.vm")) {
            fileName = StringUtils.format("{}/src/views/{}/{}/modules/{}Tree.vue", vueWorkspacePath, moduleName, businessNameLowerCase, BusinessName);
        } else if (template.contains("sub-index.vue.vm")) {
            fileName = StringUtils.format("{}/src/views/{}/{}/{}Index.vue", vueWorkspacePath, moduleName, businessNameLowerCase, BusinessName);
        } else if (template.contains("index.vue.vm") || template.contains("sub_index.vue.vm")) {
            //左树右边特殊处理
            if (isSub && !genTable.getTplCategory().equals(GenExtendConstants.TPL_TREE_TABLE)) {
                fileName = StringUtils.format("{}/src/views/{}/{}/{}Index.vue", vueWorkspacePath, moduleName, businessNameLowerCase, BusinessName);
            } else {
                fileName = StringUtils.format("{}/src/views/{}/{}/index.vue", vueWorkspacePath, moduleName, businessNameLowerCase);
            }
        } else if (template.contains("addForm.vue.vm")) {
            fileName = StringUtils.format("{}/src/views/{}/{}/modules/{}AddForm.vue", vueWorkspacePath, moduleName, businessNameLowerCase, BusinessName);
        } else if (template.contains("editForm.vue.vm")) {
            fileName = StringUtils.format("{}/src/views/{}/{}/modules/{}EditForm.vue", vueWorkspacePath, moduleName, businessNameLowerCase, BusinessName);
        } else if (template.contains("detailForm.vue.vm")) {
            fileName = StringUtils.format("{}/src/views/{}/{}/modules/{}DetailForm.vue", vueWorkspacePath, moduleName, businessNameLowerCase, BusinessName);
        } else if (template.contains("form.js.vm")) {
            fileName = StringUtils.format("{}/src/views/{}/{}/modules/{}Form.js", vueWorkspacePath, moduleName, businessNameLowerCase, BusinessName);
        } else if (template.contains("index-tree.vue.vm")) {
            fileName = StringUtils.format("{}/views/{}/{}/index.vue", vueWorkspacePath, moduleName, BusinessName);
        } else if (template.contains("tree.vue.vm")) {
            fileName = StringUtils.format("{}/views/{}/{}/Tree.vue", vueWorkspacePath, moduleName, BusinessName);
        }
        return fileName;
    }

    /**
     * 是否是主子表
     * @param genTable
     * @return
     */
    public static boolean isSub(GenTableExtend genTable) {
        GenTableExtend sub = genTable.getSubTable();
        String tplCategory = genTable.getTplCategory();
        if (sub == null && (GenExtendConstants.TPL_SUB.equals(tplCategory) || GenExtendConstants.TPL_SUB_LEFT_RIGHT.equals(tplCategory) || GenExtendConstants.TPL_TREE_TABLE.equals(tplCategory) || GenExtendConstants.TPL_FORM_INNER_TABLE.equals(tplCategory))) {
            return true;
        }
        return false;
    }

    /**
     * 将表名称转化为驼峰格式名称的名字，类似将user_data转化为userData
     *
     * @param name
     * @return
     */
    public static String vueFileNameFormat(String name) {
        String patternExp = "_([a-zA-Z])";
        Pattern p = Pattern.compile(patternExp);
        Matcher m = p.matcher(name);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, m.group(1).toUpperCase());
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * 获取包前缀
     *
     * @param packageName 包名称
     * @return 包前缀名称
     */
    public static String getPackagePrefix(String packageName)
    {
        int lastIndex = packageName.lastIndexOf(".");
        return StringUtils.substring(packageName, 0, lastIndex);
    }

    /**
     * 根据列类型获取导入包
     * 
     * @param genTable 业务表对象
     * @return 返回需要导入的包列表
     */
    public static HashSet<String> getImportList(GenTableExtend genTable)
    {
        List<GenTableColumnExtend> columns = genTable.getColumns();
        GenTableExtend subGenTable = genTable.getSubTable();
        HashSet<String> importList = new HashSet<String>();
        if (StringUtils.isNotNull(subGenTable))
        {
            importList.add("java.util.List");
        }
        for (GenTableColumnExtend column : columns)
        {
            if (!column.isSuperColumn() && GenExtendConstants.TYPE_DATE.equals(column.getJavaType()))
            {
                importList.add("java.util.Date");
                importList.add("com.fasterxml.jackson.annotation.JsonFormat");
            }
            else if (!column.isSuperColumn() && GenExtendConstants.TYPE_BIGDECIMAL.equals(column.getJavaType()))
            {
                importList.add("java.math.BigDecimal");
            }
        }
        return importList;
    }

    /**
     * 根据列类型获取字典组
     * 
     * @param genTable 业务表对象
     * @return 返回字典组
     */
    public static String getDicts(GenTableExtend genTable)
    {
        List<GenTableColumnExtend> columns = genTable.getColumns();
        Set<String> dicts = new HashSet<String>();
        addDicts(dicts, columns);
        if (StringUtils.isNotNull(genTable.getSubTable()))
        {
            List<GenTableColumnExtend> subColumns = genTable.getSubTable().getColumns();
            addDicts(dicts, subColumns);
        }
        return StringUtils.join(dicts, ", ");
    }

    /**
     * 添加字典列表
     * 
     * @param dicts 字典列表
     * @param columns 列集合
     */
    public static void addDicts(Set<String> dicts, List<GenTableColumnExtend> columns)
    {
        for (GenTableColumnExtend column : columns)
        {
            if (!column.isSuperColumn() && StringUtils.isNotEmpty(column.getDictType()) && StringUtils.equalsAny(
                    column.getHtmlType(),
                    new String[] { GenExtendConstants.HTML_SELECT, GenExtendConstants.HTML_RADIO, GenExtendConstants.HTML_CHECKBOX }))
            {
                dicts.add("'" + column.getDictType() + "'");
            }
        }
    }

    /**
     * 获取权限前缀
     *
     * @param moduleName 模块名称
     * @param businessName 业务名称
     * @return 返回权限前缀
     */
    public static String getPermissionPrefix(String moduleName, String businessName)
    {
        return StringUtils.format("{}:{}", moduleName, businessName);
    }

    /**
     * 获取上级菜单ID字段
     *
     * @param paramsObj 生成其他选项
     * @return 上级菜单ID字段
     */
    public static String getParentMenuId(JSONObject paramsObj)
    {
        if (StringUtils.isNotEmpty(paramsObj) && paramsObj.containsKey(GenExtendConstants.PARENT_MENU_ID)
                && StringUtils.isNotEmpty(paramsObj.getString(GenExtendConstants.PARENT_MENU_ID)))
        {
            return paramsObj.getString(GenExtendConstants.PARENT_MENU_ID);
        }
        return DEFAULT_PARENT_MENU_ID;
    }

    /**
     * 获取树编码
     *
     * @param paramsObj 生成其他选项
     * @return 树编码
     */
    public static String getTreecode(JSONObject paramsObj)
    {
        if (paramsObj.containsKey(GenExtendConstants.TREE_CODE))
        {
            return StringUtils.toCamelCase(paramsObj.getString(GenExtendConstants.TREE_CODE));
        }
        return StringUtils.EMPTY;
    }

    /**
     * 获取树父编码
     *
     * @param paramsObj 生成其他选项
     * @return 树父编码
     */
    public static String getTreeParentCode(JSONObject paramsObj)
    {
        if (paramsObj.containsKey(GenExtendConstants.TREE_PARENT_CODE))
        {
            return StringUtils.toCamelCase(paramsObj.getString(GenExtendConstants.TREE_PARENT_CODE));
        }
        return StringUtils.EMPTY;
    }

    /**
     * 获取树名称
     *
     * @param paramsObj 生成其他选项
     * @return 树名称
     */
    public static String getTreeName(JSONObject paramsObj)
    {
        if (paramsObj.containsKey(GenExtendConstants.TREE_NAME))
        {
            return StringUtils.toCamelCase(paramsObj.getString(GenExtendConstants.TREE_NAME));
        }
        return StringUtils.EMPTY;
    }

    /**
     * 获取需要在哪一列上面显示展开按钮
     *
     * @param genTable 业务表对象
     * @return 展开按钮列序号
     */
    public static int getExpandColumn(GenTableExtend genTable)
    {
        String options = genTable.getOptions();
        JSONObject paramsObj = JSON.parseObject(options);
        String treeName = paramsObj.getString(GenExtendConstants.TREE_NAME);
        int num = 0;
        for (GenTableColumnExtend column : genTable.getColumns())
        {
            if (column.isList())
            {
                num++;
                String columnName = column.getColumnName();
                if (columnName.equals(treeName))
                {
                    break;
                }
            }
        }
        return num;
    }

    /**
     * 获取是否有停用启用配置
     *
     * @param paramsObj 生成其他选项
     * @return 获取是否有停用启用配置
     */
    public static String getDisableEnableOption(JSONObject paramsObj) {
        if (StringUtils.isNotEmpty(paramsObj) && paramsObj.containsKey(GenExtendConstants.DISABLE_ENABLE_OPTION)) {
            return paramsObj.getString(GenExtendConstants.DISABLE_ENABLE_OPTION);
        }
        return DEFAULT_DISABLE_ENABLE_SET;
    }

    /**
     * 是否保存并继续添加
     *
     * @param paramsObj 生成其他选项
     * @return 是否保存并继续添加配置
     */
    public static String getSaveAndAddOption(JSONObject paramsObj) {
        if (StringUtils.isNotEmpty(paramsObj) && paramsObj.containsKey(GenExtendConstants.SAVE_AND_ADD_OPTION)) {
            return paramsObj.getString(GenExtendConstants.SAVE_AND_ADD_OPTION);
        }
        return DEFAULT_SAVE_AND_ADD_SET;
    }

    /**
     * 获取是否有复制功能配置
     *
     * @param paramsObj 生成其他选项
     * @return 获取是否有复制功能配置
     */
    public static String getCopyRecordOption(JSONObject paramsObj) {
        if (StringUtils.isNotEmpty(paramsObj) && paramsObj.containsKey(GenExtendConstants.COPY_RECORD_OPTION)) {
            return paramsObj.getString(GenExtendConstants.COPY_RECORD_OPTION);
        }
        return DEFAULT_COPY_RECORD_SET;
    }

}
