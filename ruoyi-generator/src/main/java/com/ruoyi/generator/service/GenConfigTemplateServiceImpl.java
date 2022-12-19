package com.ruoyi.generator.service;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.generator.mapper.GenConfigTemplateMapper;
import com.ruoyi.generator.domain.GenConfigTemplate;
import com.ruoyi.generator.service.IGenConfigTemplateService;

/**
 * 模板配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-12-14
 */
@Service
public class GenConfigTemplateServiceImpl implements IGenConfigTemplateService 
{
    @Autowired
    private GenConfigTemplateMapper genConfigTemplateMapper;

    /**
     * 查询模板配置
     * 
     * @param id 模板配置主键
     * @return 模板配置
     */
    @Override
    public GenConfigTemplate selectGenConfigTemplateById(String id)
    {
        return genConfigTemplateMapper.selectGenConfigTemplateById(id);
    }

    /**
     * 查询模板配置列表
     * 
     * @param genConfigTemplate 模板配置
     * @return 模板配置
     */
    @Override
    public List<GenConfigTemplate> selectGenConfigTemplateList(GenConfigTemplate genConfigTemplate)
    {
        return genConfigTemplateMapper.selectGenConfigTemplateList(genConfigTemplate);
    }

    /**
     * 新增模板配置
     * 
     * @param genConfigTemplate 模板配置
     * @return 结果
     */
    @Override
    public int insertGenConfigTemplate(GenConfigTemplate genConfigTemplate)
    {
        genConfigTemplate.setId(IdUtils.randomUUID());
        genConfigTemplate.setCreateTime(DateUtils.getNowDate());
        return genConfigTemplateMapper.insertGenConfigTemplate(genConfigTemplate);
    }

    /**
     * 修改模板配置
     * 
     * @param genConfigTemplate 模板配置
     * @return 结果
     */
    @Override
    public int updateGenConfigTemplate(GenConfigTemplate genConfigTemplate)
    {
        genConfigTemplate.setUpdateTime(DateUtils.getNowDate());
        return genConfigTemplateMapper.updateGenConfigTemplate(genConfigTemplate);
    }

    /**
     * 批量删除模板配置
     * 
     * @param ids 需要删除的模板配置主键
     * @return 结果
     */
    @Override
    public int deleteGenConfigTemplateByIds(String[] ids)
    {
        return genConfigTemplateMapper.deleteGenConfigTemplateByIds(ids);
    }

    /**
     * 删除模板配置信息
     * 
     * @param id 模板配置主键
     * @return 结果
     */
    @Override
    public int deleteGenConfigTemplateById(String id)
    {
        return genConfigTemplateMapper.deleteGenConfigTemplateById(id);
    }

    /**
     * 获取最大编号
     * @param genConfigTemplate
     * @return
     */
    @Override
    public int findMaxSort(GenConfigTemplate genConfigTemplate) {
        Integer order = genConfigTemplateMapper.findMaxSort(genConfigTemplate);
        if(order == null){
            order = 0;
        }
        return order / 10 * 10 + 10;
    }
}
