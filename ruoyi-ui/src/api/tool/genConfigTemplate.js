import request from '@/utils/request'

// 查询模板配置列表
export function listGenConfigTemplate(query) {
  return request({
    url: '/tool/genConfigTemplate/list',
    method: 'get',
    params: query
  })
}

// 查询模板配置详细
export function getGenConfigTemplate(id) {
  return request({
    url: '/tool/genConfigTemplate/' + id,
    method: 'get'
  })
}

// 新增模板配置
export function addGenConfigTemplate(data) {
  return request({
    url: '/tool/genConfigTemplate',
    method: 'post',
    data: data
  })
}

// 修改模板配置
export function updateGenConfigTemplate(data) {
  return request({
    url: '/tool/genConfigTemplate',
    method: 'put',
    data: data
  })
}

// 删除模板配置
export function delGenConfigTemplate(id) {
  return request({
    url: '/tool/genConfigTemplate/' + id,
    method: 'delete'
  })
}

// 查询最大编号
export function findMaxSort () {
  return request({
    url: '/tool/genConfigTemplate/findMaxSort',
    method: 'get'
  })
}
