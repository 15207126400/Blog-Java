package com.ivan.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ivan.blog.entity.SysPermission;
import org.apache.ibatis.annotations.Param;

public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    SysPermission findPermissionByUrl(@Param("url") String url, @Param("roleId") String roleId);

}