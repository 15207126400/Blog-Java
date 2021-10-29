package com.ivan.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ivan.blog.entity.SysUser;
import com.ivan.blog.entity.vo.SysUserVO;

import java.util.List;

public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 根据账号查询用户信息
     * @param username
     * @return
     */
    SysUser findUserByName(String username);

    /**
     * 根据ID查询用户信息
     * @param id
     * @return
     */
    SysUserVO findUserById(Integer id);

    /**
     * 查询用户列表(关联角色)
     * @return
     */
    List<SysUserVO> selectUserAndRoleList();

}