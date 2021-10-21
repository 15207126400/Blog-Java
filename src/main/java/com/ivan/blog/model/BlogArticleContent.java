package com.ivan.blog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BlogArticleContent {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private Date createTime;

    private Date updateTime;

    private Integer articleId;

    private String content;
}