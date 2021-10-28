package com.ivan.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 *  @Author: Ivan
 *  @Description:   评论
 *  @Date: 2021/10/27 14:33
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BlogComment implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String content;

    private String email;

    private String name;

    private String avatarUrl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    private Integer articleId;

    private Integer parentId;

    private String articleTitle;

    private Integer ladder;

}