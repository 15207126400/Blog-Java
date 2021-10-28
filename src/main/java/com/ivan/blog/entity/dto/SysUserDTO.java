package com.ivan.blog.entity.dto;

import com.ivan.blog.entity.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SysUserDTO extends SysUser {

    private Integer roleId;

}