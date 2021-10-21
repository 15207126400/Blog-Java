package com.ivan.blog.service.impl;

import com.ivan.blog.dao.SysRolePermissionMapper;
import com.ivan.blog.model.SysRolePermission;
import com.ivan.blog.service.SysRolePermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/*
 *  @Author: Ivan
 *  @Description:
 *  @Date: 2019/11/6 16:19
 */
@Service
public class SysRolePermissionServiceImpl implements SysRolePermissionService {
    @Resource
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Override
    public List<SysRolePermission> getPermissions(int role_id) {
        return sysRolePermissionMapper.getPermissions(role_id);
    }

    @Override
    public List<SysRolePermission> findPermissionsByRole(int role_id) {
        return sysRolePermissionMapper.findPermissionsByRole(role_id);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return sysRolePermissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SysRolePermission record) {
        return sysRolePermissionMapper.insert(record);
    }

    @Override
    public int insertSelective(SysRolePermission record) {
        return sysRolePermissionMapper.insertSelective(record);
    }

    @Override
    public SysRolePermission selectByPrimaryKey(Integer id) {
        return sysRolePermissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SysRolePermission record) {
        return sysRolePermissionMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysRolePermission record) {
        return sysRolePermissionMapper.updateByPrimaryKey(record);
    }
}
