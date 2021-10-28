package com.ivan.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ivan.blog.entity.SysUser;
import com.ivan.blog.entity.dto.SysUserDTO;
import com.ivan.blog.entity.vo.SysUserVO;

import java.util.List;

/**
 *  @Author: Ivan
 *  @Description:
 *  @Date: 2019/10/31 09:37
 */
public interface SysUserService extends IService<SysUser> {

    SysUser findUserByName(String username);

    SysUserVO findUserById(Integer id);

    List<SysUserVO> selectUserAndRoleList();

    boolean insertByUserId(SysUserDTO sysUserDTO);

    boolean updateByUserId(SysUserDTO sysUserDTO);

    boolean deleteByUserId(Integer id);

}
