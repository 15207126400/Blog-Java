package com.ivan.blog.service;

import com.ivan.blog.model.SysUserRole;

import java.util.List;

/*
 *  @Author: Ivan
 *  @Description:
 *  @Date: 2019/11/5 15:27
 */
public interface SysUserRoleService {
    List<SysUserRole> getRoles(int user_id);

    int deleteByPrimaryKey(Integer id);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    SysUserRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUserRole record);

    int updateByPrimaryKey(SysUserRole record);
}
