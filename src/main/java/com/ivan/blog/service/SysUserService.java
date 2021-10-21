package com.ivan.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ivan.blog.model.SysUser;
import com.ivan.blog.model.dto.SysUserDTO;

import java.util.List;

/*
 *  @Author: Ivan
 *  @Description:
 *  @Date: 2019/10/31 09:37
 */
public interface SysUserService {
    SysUser userLogin(String username , String password);

    SysUser findUserByName(String username);

    List<String> findRolesByUsername(String username);

    List<SysUserDTO> list();

    int deleteByPrimaryKey(Integer id);

    int insert(SysUserDTO record);

    int insertSelective(SysUserDTO record);

    SysUserDTO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUserDTO record);

    int updateByPrimaryKey(SysUserDTO record);
}
