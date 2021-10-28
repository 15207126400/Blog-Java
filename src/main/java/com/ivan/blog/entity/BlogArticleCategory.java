package com.ivan.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 *  @Author: Ivan
 *  @Description:   文章-标签
 *  @Date: 2021/10/27 14:33
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BlogArticleCategory implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer categoryId;

    private Integer articleId;

}