package com.ivan.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ivan.blog.entity.BlogArticle;
import com.ivan.blog.entity.dto.BlogArticleDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogArticleMapper extends BaseMapper<BlogArticle> {

    /**
     * 查询文章详情
     * @param id
     * @return
     */
    BlogArticleDTO selectById(Integer id);

    /**
     * 随机查询三条文章信息(博客列表页)
     * @return
     */
    List<BlogArticle> selectListByRand();

    /**
     * 根据文章分类查询文章列表
     * @param categoryId
     * @return
     */
    List<BlogArticleDTO> selectListByCategory(@Param("categoryId") Integer categoryId);
}