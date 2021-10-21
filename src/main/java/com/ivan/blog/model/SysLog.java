package com.ivan.blog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysLog implements Serializable {
    private static final long serialVersionUID = -1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String username;

    private String operation;

    private String method;

    private String ip;

    private String address;

    private String browser;

    private String system;

    private Date createTime;

}