package com.ivan.blog.model.dto;

import com.ivan.blog.model.BlogArticle;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/*
 *  @Author: Ivan
 *  @Description:
 *  @Date: 2019/11/29 18:18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BlogArticleDTO extends BlogArticle {

    private String category;

    private String content;

    private BlogArticle beforeArticle;

    private BlogArticle afterArticle;
}
