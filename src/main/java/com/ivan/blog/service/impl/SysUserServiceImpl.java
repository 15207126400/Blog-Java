package com.ivan.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ivan.blog.dao.SysUserMapper;
import com.ivan.blog.model.SysUser;
import com.ivan.blog.model.SysUserRole;
import com.ivan.blog.model.dto.SysUserDTO;
import com.ivan.blog.service.SysUserRoleService;
import com.ivan.blog.service.SysUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/*
 *  @Author: Ivan
 *  @Description:
 *  @Date: 2019/10/31 09:38
 */
@Service
@AllArgsConstructor
public class SysUserServiceImpl implements SysUserService {

    private final SysUserMapper sysUserMapper;
    private final SysUserRoleService sysUserRoleService;

    @Override
    public SysUser userLogin(String username , String password) {
        LambdaQueryWrapper<SysUser> lambdaQuery = Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getUsername,username)
                .eq(SysUser::getPassword,password);

        return sysUserMapper.selectOne(lambdaQuery);
    }

    @Override
    public SysUser findUserByName(String username) {

        return sysUserMapper.findUserByName(username);
    }

    @Override
    public List<String> findRolesByUsername(String username) {
        return sysUserMapper.findRolesByUsername(username);
    }

    @Override
    public List<SysUserDTO> list() {
        return sysUserMapper.list();
    }

    @Override
    @Transactional
    public int deleteByPrimaryKey(Integer id) {
        //删除该用户的用户角色关联信息
        List<SysUserRole> userRoles = sysUserRoleService.getRoles(id);
        if(userRoles.size() > 0){
            for(SysUserRole ur : userRoles){
                sysUserRoleService.deleteByPrimaryKey(ur.getId());
            }
        }

        return sysUserMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional
    public int insert(SysUserDTO record) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(record,sysUser);
        //保存用户信息
        sysUserMapper.insert(sysUser);

        //保存用户角色关联表信息
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(sysUser.getId());
        sysUserRole.setRoleId(Integer.valueOf(record.getRolename()));

        return sysUserRoleService.insert(sysUserRole);
    }

    @Override
    public int insertSelective(SysUserDTO record) {
        return sysUserMapper.insertSelective(record);
    }

    @Override
    public SysUserDTO selectByPrimaryKey(Integer id) {
        return sysUserMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public int updateByPrimaryKeySelective(SysUserDTO record) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(record,sysUser);
        //获取用户角色表对象信息
        List<SysUserRole> roles = sysUserRoleService.getRoles(sysUser.getId());
        SysUserRole role = roles.get(0);
        role.setRoleId(Integer.valueOf(record.getRolename()));
        //更新对象角色表信息
        sysUserRoleService.updateByPrimaryKeySelective(role);

        return sysUserMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysUserDTO record) {
        return sysUserMapper.updateByPrimaryKey(record);
    }
}
