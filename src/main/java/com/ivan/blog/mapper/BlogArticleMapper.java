package com.ivan.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ivan.blog.entity.BlogArticle;
import com.ivan.blog.entity.vo.BlogArticleVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogArticleMapper extends BaseMapper<BlogArticle> {

    /**
     * 随机查询三条文章信息(首页轮播图)
     * @return
     */
    List<BlogArticle> selectListByRand();

    /**
     * 博客列表页
     * @return
     */
    IPage<BlogArticleVO> selectPageForIndex(Page page);

    /**
     * 查询文章详情
     * @param id
     * @return
     */
    BlogArticleVO selectById(Integer id);

    /**
     * 根据文章分类查询文章列表
     * @param categoryId
     * @return
     */
    IPage<BlogArticleVO> selectListByCategory(Page page, @Param("categoryId") Integer categoryId);
}