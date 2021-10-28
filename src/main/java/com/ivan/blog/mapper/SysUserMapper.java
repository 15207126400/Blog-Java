package com.ivan.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ivan.blog.entity.SysUser;
import com.ivan.blog.entity.vo.SysUserVO;

import java.util.List;

public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser findUserByName(String username);

    SysUserVO findUserById(Integer id);

    List<SysUserVO> selectUserAndRoleList();

}