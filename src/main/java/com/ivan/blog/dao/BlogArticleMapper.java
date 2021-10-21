package com.ivan.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ivan.blog.model.BlogArticle;
import com.ivan.blog.model.dto.BlogArticleDTO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BlogArticleMapper extends BaseMapper<BlogArticle> {

    @Select("select ba.*,bac.content from blog_article ba left join blog_article_content bac on ba.id = bac.article_id where ba.id = #{id}")
    BlogArticleDTO selectById(Integer id);

    /**
     * 随机查询三条文章信息 ---- 博客列表页
     * @return
     */
    @Select("select * from blog_article order by rand() limit 3")
    List<BlogArticle> selectListByRand();

    /**
     * 根据文章分类查询文章列表
     * @param categoryId
     * @return
     */
    List<BlogArticleDTO> selectListByCategory(@Param("categoryId") Integer categoryId);
}