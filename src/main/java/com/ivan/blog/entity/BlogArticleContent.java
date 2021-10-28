package com.ivan.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 *  @Author: Ivan
 *  @Description:   文章内容(富文本)
 *  @Date: 2021/10/27 14:33
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BlogArticleContent implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private Date createTime;

    private Date updateTime;

    private Integer articleId;

    private String content;
}