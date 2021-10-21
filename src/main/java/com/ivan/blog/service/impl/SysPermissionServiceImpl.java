package com.ivan.blog.service.impl;

import com.ivan.blog.dao.SysPermissionMapper;
import com.ivan.blog.model.SysPermission;
import com.ivan.blog.service.SysPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/*
 *  @Author: Ivan
 *  @Description:
 *  @Date: 2019/11/6 20:04
 */
@Service
public class SysPermissionServiceImpl implements SysPermissionService {
    @Resource
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public List<SysPermission> list() {
        return sysPermissionMapper.list();
    }

    @Override
    public List<SysPermission> findPermissionByRoleId(Integer role_id) {
        return sysPermissionMapper.findPermissionByRoleId(role_id);
    }

    @Override
    public List<SysPermission> listByDeWeight() {
        return sysPermissionMapper.listByDeWeight();
    }

    @Override
    public SysPermission findPermissionByUrl(String url, String role_id) {
        return sysPermissionMapper.findPermissionByUrl(url,role_id);
    }

    @Override
    public int selectPermissionCount(String type) {
        return sysPermissionMapper.selectPermissionCount(type);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return sysPermissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SysPermission record) {
        return sysPermissionMapper.insert(record);
    }

    @Override
    public int insertSelective(SysPermission record) {
        return sysPermissionMapper.insertSelective(record);
    }

    @Override
    public SysPermission selectByPrimaryKey(Integer id) {
        return sysPermissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SysPermission record) {
        return sysPermissionMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysPermission record) {
        return sysPermissionMapper.updateByPrimaryKey(record);
    }
}
