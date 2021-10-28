package com.ivan.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ivan.blog.entity.SysUser;
import com.ivan.blog.entity.SysUserRole;
import com.ivan.blog.entity.dto.SysUserDTO;
import com.ivan.blog.entity.vo.SysUserVO;
import com.ivan.blog.mapper.SysUserMapper;
import com.ivan.blog.mapper.SysUserRoleMapper;
import com.ivan.blog.service.SysUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Ivan
 * @Description:
 * @Date: 2019/10/31 09:38
 */
@Service
@AllArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserMapper sysUserMapper;
    private final SysUserRoleMapper sysUserRoleMapper;

    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    @Override
    public SysUser findUserByName(String username) {
        return sysUserMapper.findUserByName(username);
    }

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    @Override
    public SysUserVO findUserById(Integer id) {
        return sysUserMapper.findUserById(id);
    }

    /**
     * 用户列表查询(关联角色信息)
     *
     * @return
     */
    @Override
    public List<SysUserVO> selectUserAndRoleList() {
        return sysUserMapper.selectUserAndRoleList();
    }

    /**
     * 新增
     *
     * @param sysUserDTO
     * @return
     */
    @Override
    @Transactional
    public boolean insertByUserId(SysUserDTO sysUserDTO) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserDTO, sysUser);
        //保存用户信息
        boolean bol = sysUserMapper.insert(sysUser) > 0;
        if (bol) {
            //保存用户角色关联表信息
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(sysUser.getId());
            sysUserRole.setRoleId(sysUserDTO.getRoleId());
            sysUserRoleMapper.insert(sysUserRole);
        }

        return bol;
    }

    /**
     * 修改
     *
     * @param sysUserDTO
     * @return
     */
    @Override
    @Transactional
    public boolean updateByUserId(SysUserDTO sysUserDTO) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserDTO, sysUser);

        boolean bol = sysUserMapper.updateById(sysUserDTO) > 0;
        if (bol) {
            //获取用户角色表对象信息
            SysUserRole userRole = sysUserRoleMapper.selectOne(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, sysUserDTO.getId()));
            if(userRole != null){
                userRole.setRoleId(sysUserDTO.getRoleId());
                //更新对象角色表信息
                sysUserRoleMapper.updateById(userRole);
            }
        }

        return bol;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public boolean deleteByUserId(Integer id) {
        boolean bol = sysUserMapper.deleteById(id) > 0;
        if (bol) {
            //删除该用户的用户角色关联信息
            SysUserRole userRole = sysUserRoleMapper.selectOne(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, id));
            if(userRole != null){
                sysUserRoleMapper.deleteById(userRole);
            }
        }

        return bol;
    }
}
