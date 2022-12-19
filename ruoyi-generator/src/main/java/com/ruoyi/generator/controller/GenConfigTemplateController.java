package com.ruoyi.generator.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.generator.domain.GenConfigTemplate;
import com.ruoyi.generator.service.IGenConfigTemplateService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 模板配置Controller
 * 
 * @author ruoyi
 * @date 2022-12-14
 */
@RestController
@RequestMapping("/tool/genConfigTemplate")
public class GenConfigTemplateController extends BaseController
{
    @Autowired
    private IGenConfigTemplateService genConfigTemplateService;

    /**
     * 查询模板配置列表
     */
    @PreAuthorize("@ss.hasPermi('tool:genConfigTemplate:list')")
    @GetMapping("/list")
    public TableDataInfo list(GenConfigTemplate genConfigTemplate)
    {
        startPage();
        List<GenConfigTemplate> list = genConfigTemplateService.selectGenConfigTemplateList(genConfigTemplate);
        return getDataTable(list);
    }

    /**
     * 导出模板配置列表
     */
    @PreAuthorize("@ss.hasPermi('tool:genConfigTemplate:export')")
    @Log(title = "模板配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, GenConfigTemplate genConfigTemplate)
    {
        List<GenConfigTemplate> list = genConfigTemplateService.selectGenConfigTemplateList(genConfigTemplate);
        ExcelUtil<GenConfigTemplate> util = new ExcelUtil<GenConfigTemplate>(GenConfigTemplate.class);
        util.exportExcel(response, list, "模板配置数据");
    }

    /**
     * 获取模板配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('tool:genConfigTemplate:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(genConfigTemplateService.selectGenConfigTemplateById(id));
    }

    /**
     * 新增模板配置
     */
    @PreAuthorize("@ss.hasPermi('tool:genConfigTemplate:add')")
    @Log(title = "模板配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GenConfigTemplate genConfigTemplate)
    {
        return toAjax(genConfigTemplateService.insertGenConfigTemplate(genConfigTemplate));
    }

    /**
     * 修改模板配置
     */
    @PreAuthorize("@ss.hasPermi('tool:genConfigTemplate:edit')")
    @Log(title = "模板配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GenConfigTemplate genConfigTemplate)
    {
        return toAjax(genConfigTemplateService.updateGenConfigTemplate(genConfigTemplate));
    }

    /**
     * 删除模板配置
     */
    @PreAuthorize("@ss.hasPermi('tool:genConfigTemplate:remove')")
    @Log(title = "模板配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(genConfigTemplateService.deleteGenConfigTemplateByIds(ids));
    }

    /**
     * 获取当前最大编号
     * @return
     */
    @PreAuthorize("@ss.hasPermi('gen:template:edit')")
    @GetMapping("/findMaxSort")
    public AjaxResult findMaxSort() {
        return success(genConfigTemplateService.findMaxSort(new GenConfigTemplate()));
    }
}
