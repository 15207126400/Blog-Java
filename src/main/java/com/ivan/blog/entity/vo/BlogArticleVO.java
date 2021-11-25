package com.ivan.blog.entity.vo;

import com.ivan.blog.entity.BlogArticle;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *  @Author: Ivan
 *  @Description:
 *  @Date: 2019/11/29 18:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BlogArticleVO extends BlogArticle {

    private String category;

    private String content;

    private BlogArticle beforeArticle;

    private BlogArticle afterArticle;
}
