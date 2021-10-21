package com.ivan.blog.service;

import com.ivan.blog.model.SysPermission;

import java.util.List;

/*
 *  @Author: Ivan
 *  @Description:
 *  @Date: 2019/11/6 20:04
 */
public interface SysPermissionService {
    List<SysPermission> list();

    List<SysPermission> findPermissionByRoleId(Integer role_id);

    List<SysPermission> listByDeWeight();

    SysPermission findPermissionByUrl(String url, String role_id);

    int selectPermissionCount(String type);

    int deleteByPrimaryKey(Integer id);

    int insert(SysPermission record);

    int insertSelective(SysPermission record);

    SysPermission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysPermission record);

    int updateByPrimaryKey(SysPermission record);
}
