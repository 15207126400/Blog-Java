package com.ivan.blog.entity.vo;

import com.ivan.blog.entity.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysUserVO extends SysUser {

    private String roleName;
}