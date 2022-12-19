package com.ruoyi.generator.service;

import java.util.List;
import com.ruoyi.generator.domain.GenConfigTemplate;

/**
 * 模板配置Service接口
 * 
 * @author ruoyi
 * @date 2022-12-14
 */
public interface IGenConfigTemplateService 
{
    /**
     * 查询模板配置
     * 
     * @param id 模板配置主键
     * @return 模板配置
     */
    public GenConfigTemplate selectGenConfigTemplateById(String id);

    /**
     * 查询模板配置列表
     * 
     * @param genConfigTemplate 模板配置
     * @return 模板配置集合
     */
    public List<GenConfigTemplate> selectGenConfigTemplateList(GenConfigTemplate genConfigTemplate);

    /**
     * 新增模板配置
     * 
     * @param genConfigTemplate 模板配置
     * @return 结果
     */
    public int insertGenConfigTemplate(GenConfigTemplate genConfigTemplate);

    /**
     * 修改模板配置
     * 
     * @param genConfigTemplate 模板配置
     * @return 结果
     */
    public int updateGenConfigTemplate(GenConfigTemplate genConfigTemplate);

    /**
     * 批量删除模板配置
     * 
     * @param ids 需要删除的模板配置主键集合
     * @return 结果
     */
    public int deleteGenConfigTemplateByIds(String[] ids);

    /**
     * 删除模板配置信息
     * 
     * @param id 模板配置主键
     * @return 结果
     */
    public int deleteGenConfigTemplateById(String id);

    /**
     * 获取最大编号
     * @param genConfigTemplate
     * @return
     */
    public int findMaxSort(GenConfigTemplate genConfigTemplate);
}
