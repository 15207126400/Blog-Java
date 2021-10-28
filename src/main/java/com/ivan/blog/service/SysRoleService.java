package com.ivan.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ivan.blog.entity.SysRole;
import com.ivan.blog.entity.tool.TreeModel;

import java.util.List;

/**
 *  @Author: Ivan
 *  @Description:
 *  @Date: 2019/10/31 09:37
 */
public interface SysRoleService extends IService<SysRole> {

    List<TreeModel> getTreeByRole();

    List<TreeModel> getTreeByRole(String roleId);

    boolean insertByRoleId(SysRole sysRole);

    boolean updateByRoleId(SysRole sysRole);

    boolean deleteByRoleId(Integer roleId);

}
