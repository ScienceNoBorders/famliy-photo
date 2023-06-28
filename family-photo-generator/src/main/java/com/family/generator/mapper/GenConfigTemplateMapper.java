package com.family.generator.mapper;

import java.util.List;
import com.family.generator.domain.GenConfigTemplate;

/**
 * 模板配置Mapper接口
 * 
 * @author ruoyi
 * @date 2022-12-14
 */
public interface GenConfigTemplateMapper 
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
     * 删除模板配置
     * 
     * @param id 模板配置主键
     * @return 结果
     */
    public int deleteGenConfigTemplateById(String id);

    /**
     * 批量删除模板配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGenConfigTemplateByIds(String[] ids);

    /**
     * 查询最大编号
     * @param
     * @return Integer
     */
    public Integer findMaxSort(GenConfigTemplate genConfigTemplate);
}
