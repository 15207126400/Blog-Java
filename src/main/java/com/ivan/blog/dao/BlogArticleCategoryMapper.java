package com.ivan.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ivan.blog.model.BlogArticleCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogArticleCategoryMapper extends BaseMapper<BlogArticleCategory> {

    /**
     * 批量创建文章与分类关联关系
     * @param list           文章分类id集合
     * @param articleId     文章id
     */
    void insertBatch(@Param("list") List<Integer> list, @Param("articleId") Integer articleId);
}