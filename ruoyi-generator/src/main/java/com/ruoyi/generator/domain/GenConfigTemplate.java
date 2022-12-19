package com.ruoyi.generator.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 模板配置对象 gen_config_template
 * 
 * @author ruoyi
 * @date 2022-12-14
 */
public class GenConfigTemplate extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private String id;

    /** 模板名称 */
    @Excel(name = "模板名称")
    private String templateName;

    /** 作者 */
    @Excel(name = "作者")
    private String functionAuthor;

    /** 邮箱 */
    @Excel(name = "邮箱")
    private String functionAuthorEmail;

    /** 后端工作空间 */
    @Excel(name = "后端工作空间")
    private String workspacePath;

    /** 前端工作空间 */
    @Excel(name = "前端工作空间")
    private String webWorkspacePath;

    /** 模块名 */
    @Excel(name = "模块名")
    private String moduleName;

    /** 包路径 */
    @Excel(name = "包路径")
    private String packageName;

    /** 排序 */
    @Excel(name = "排序")
    private Integer sort;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setTemplateName(String templateName) 
    {
        this.templateName = templateName;
    }

    public String getTemplateName() 
    {
        return templateName;
    }
    public void setFunctionAuthor(String functionAuthor) 
    {
        this.functionAuthor = functionAuthor;
    }

    public String getFunctionAuthor() 
    {
        return functionAuthor;
    }
    public void setFunctionAuthorEmail(String functionAuthorEmail) 
    {
        this.functionAuthorEmail = functionAuthorEmail;
    }

    public String getFunctionAuthorEmail() 
    {
        return functionAuthorEmail;
    }
    public void setWorkspacePath(String workspacePath) 
    {
        this.workspacePath = workspacePath;
    }

    public String getWorkspacePath() 
    {
        return workspacePath;
    }
    public void setWebWorkspacePath(String webWorkspacePath) 
    {
        this.webWorkspacePath = webWorkspacePath;
    }

    public String getWebWorkspacePath() 
    {
        return webWorkspacePath;
    }
    public void setModuleName(String moduleName) 
    {
        this.moduleName = moduleName;
    }

    public String getModuleName() 
    {
        return moduleName;
    }
    public void setPackageName(String packageName) 
    {
        this.packageName = packageName;
    }

    public String getPackageName() 
    {
        return packageName;
    }
    public void setSort(Integer sort)
    {
        this.sort = sort;
    }

    public Integer getSort() 
    {
        return sort;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("templateName", getTemplateName())
            .append("functionAuthor", getFunctionAuthor())
            .append("functionAuthorEmail", getFunctionAuthorEmail())
            .append("workspacePath", getWorkspacePath())
            .append("webWorkspacePath", getWebWorkspacePath())
            .append("moduleName", getModuleName())
            .append("packageName", getPackageName())
            .append("sort", getSort())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
