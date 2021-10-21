package com.ivan.blog.service.impl;

import com.ivan.blog.dao.SysUserRoleMapper;
import com.ivan.blog.model.SysUserRole;
import com.ivan.blog.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/*
 *  @Author: Ivan
 *  @Description:
 *  @Date: 2019/11/5 15:28
 */
@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public List<SysUserRole> getRoles(int user_id) {
        return sysUserRoleMapper.getRoles(user_id);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return sysUserRoleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SysUserRole record) {
        return sysUserRoleMapper.insert(record);
    }

    @Override
    public int insertSelective(SysUserRole record) {
        return sysUserRoleMapper.insertSelective(record);
    }

    @Override
    public SysUserRole selectByPrimaryKey(Integer id) {
        return sysUserRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SysUserRole record) {
        return sysUserRoleMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysUserRole record) {
        return sysUserRoleMapper.updateByPrimaryKey(record);
    }
}
