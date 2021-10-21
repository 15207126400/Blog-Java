package com.ivan.blog.model.dto;

import com.ivan.blog.model.SysRole;
import com.ivan.blog.model.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUserDTO extends SysUser {

    private String rolename;

    private List<SysRole> roleList;


}