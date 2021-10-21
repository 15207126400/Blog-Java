package com.ivan.blog.model;

import java.io.Serializable;

public class SysPermission implements Serializable {
    private static final long serialVersionUID = -1L;

    private Integer id;

    private String type;

    private String name;

    private String permission;

    private String url;

    public SysPermission(Integer id, String type, String name, String permission, String url) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.permission = permission;
        this.url = url;
    }

    public SysPermission() {
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

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission == null ? null : permission.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}