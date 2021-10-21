package com.ivan.blog.dao;

import com.ivan.blog.model.SysPermission;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysPermissionMapper {
    @Select("select * from sys_permission")
    List<SysPermission> list();

    @Select("select sp.* from sys_permission sp left join sys_role_permission srp on sp.id = srp.permission_id where srp.role_id = #{role_id}")
    List<SysPermission> findPermissionByRoleId(Integer role_id);

    //根据类型去重查询
    @Select("select * from sys_permission group by type")
    List<SysPermission> listByDeWeight();

    @Select("select * from sys_permission sp left join sys_role_permission srp on sp.id = srp.permission_id  where url = #{url} and srp.role_id = #{role_id}")
    SysPermission findPermissionByUrl(String url, String role_id);

    @Select("SELECT count(*) from sys_permission where type = #{type}")
    int selectPermissionCount(String type);

    int deleteByPrimaryKey(Integer id);

    int insert(SysPermission record);

    int insertSelective(SysPermission record);

    SysPermission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysPermission record);

    int updateByPrimaryKey(SysPermission record);
}