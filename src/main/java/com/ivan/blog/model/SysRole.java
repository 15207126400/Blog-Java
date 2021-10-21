package com.ivan.blog.model;

import java.io.Serializable;
import java.util.List;

public class SysRole implements Serializable {
    private static final long serialVersionUID = -1L;

    private Integer id;

    private String name;

    private String description;

    private String permission_ids;

    //角色 -- 权限关系：多对多关系;
    private List<SysPermission> permissions;

    // 一个角色对应多个用户
    private List<SysUser> userInfos;

    public List<SysPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<SysPermission> permissions) {
        this.permissions = permissions;
    }

    public List<SysUser> getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(List<SysUser> userInfos) {
        this.userInfos = userInfos;
    }

    public SysRole(Integer id, String name, String description, String permission_ids) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.permission_ids = permission_ids;
    }

    public SysRole() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getPermission_ids() {
        return permission_ids;
    }

    public void setPermission_ids(String permission_ids) {
        this.permission_ids = permission_ids == null ? null : permission_ids.trim();
    }
}