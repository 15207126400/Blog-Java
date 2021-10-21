package com.ivan.blog.dao;

import com.ivan.blog.model.SysRolePermission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysRolePermissionMapper {
    @Select("select * from sys_role_permission where role_id=#{role_id}")
    List<SysRolePermission> getPermissions(int role_id);

    @Select("select * from sys_role_permission where role_id = #{role_id}")
    List<SysRolePermission> findPermissionsByRole(int role_id);

    int deleteByPrimaryKey(Integer id);

    int insert(SysRolePermission record);

    int insertSelective(SysRolePermission record);

    SysRolePermission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRolePermission record);

    int updateByPrimaryKey(SysRolePermission record);
}