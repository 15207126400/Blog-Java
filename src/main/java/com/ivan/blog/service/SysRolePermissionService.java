package com.ivan.blog.service;

import com.ivan.blog.model.SysRolePermission;

import java.util.List;

/*
 *  @Author: Ivan
 *  @Description:
 *  @Date: 2019/11/6 16:19
 */
public interface SysRolePermissionService {
    List<SysRolePermission> getPermissions(int role_id);

    List<SysRolePermission> findPermissionsByRole(int role_id);

    int deleteByPrimaryKey(Integer id);

    int insert(SysRolePermission record);

    int insertSelective(SysRolePermission record);

    SysRolePermission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRolePermission record);

    int updateByPrimaryKey(SysRolePermission record);
}
