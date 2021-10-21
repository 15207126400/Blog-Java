package com.ivan.blog.service;

import com.ivan.blog.model.SysRole;
import com.ivan.blog.model.SysUser;
import com.ivan.blog.model.tool.TreeModel;

import java.util.List;

/*
 *  @Author: Ivan
 *  @Description:
 *  @Date: 2019/10/31 09:37
 */
public interface SysRoleService {
    List<SysRole> list();

    List<TreeModel> getTreeByRole();

    List<TreeModel> getTreeByRole(String role_id);

    int deleteByPrimaryKey(Integer id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);
}
