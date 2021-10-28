package com.ivan.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 *  @Author: Ivan
 *  @Description:   权限
 *  @Date: 2021/10/27 14:33
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysPermission implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String type;

    private String name;

    private String permission;

    private String url;

}