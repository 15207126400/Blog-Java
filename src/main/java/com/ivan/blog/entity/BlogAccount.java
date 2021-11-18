package com.ivan.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ivan.blog.validation.Save;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 *  @Author: Ivan
 *  @Description:   博客账号
 *  @Date: 2021/10/27 14:33
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BlogAccount implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Size(min = 5, message = "账号长度不允许少于5个字符")
    @NotBlank(message = "账号不允许为空", groups = {Save.class})
    private String username;

    @Size(min = 5, message = "密码长度不允许少于6个字符")
    @NotBlank(message = "密码不允许为空", groups = {Save.class})
    private String password;

    @NotBlank(message = "昵称不允许为空", groups = {Save.class})
    private String nickname;

    private String avatar;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date lastLoginTime;

    @JsonIgnore
    @TableLogic
    private String delFlag;
}