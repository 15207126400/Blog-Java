package com.ivan.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ivan.blog.entity.SysRolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRolePermissionMapper extends BaseMapper<SysRolePermission> {

    /**
     * 批量插入
     * @param list
     * @param roleId
     */
    void insertBatch(@Param("list") List<SysRolePermission> list, @Param("roleId") Integer roleId);

}