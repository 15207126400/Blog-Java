package com.ivan.blog.dao;

import com.ivan.blog.model.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysRoleMapper {
    @Select("select name from sys_permission RIGHT JOIN(SELECT permission_id from sys_role_permission RIGHT  JOIN(SELECT * from sys_role where name=#{name})b on b.id=sys_role_permission.role_id)c on c.permission_id=sys_permission.id")
    List<String> findPermissionsByRole(String name);

    @Select("select * from sys_role")
    List<SysRole> list();

    int deleteByPrimaryKey(Integer id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);
}