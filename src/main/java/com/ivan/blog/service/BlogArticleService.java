package com.ivan.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ivan.blog.model.BlogArticle;
import com.ivan.blog.model.BlogArticlePicture;
import com.ivan.blog.model.dto.BlogArticleDTO;

import java.util.List;
import java.util.Map;

/*
 *  @Author: Ivan
 *  @Description:
 *  @Date: 2019/11/28 16:14
 */
public interface BlogArticleService extends IService<BlogArticle> {
    boolean saveByArticle(BlogArticleDTO blogArticleDTO);

    boolean updateByArticle(BlogArticleDTO blogArticleDTO);

    BlogArticleDTO selectById(Integer id);

    /**
     * 轮播图片获取请求 ---- 博客首页
     * @return
     */
    List<BlogArticleDTO> getArticleList();

    /**
     * 根据文章分类查询文章列表
     * @param categoryId
     * @return
     */
    List<BlogArticleDTO> selectListByCategory(Integer categoryId);

    /**
     * 随机查询三条文章信息 ---- 博客列表页
     * @return
     */
    List<BlogArticle> selectListByRand();

    /**
     * 获取上一篇博文
     * @param id
     * @return
     */
    BlogArticle selectArticleByBefore(Integer id);

    /**
     * 获取下一篇博文
     * @param id
     * @return
     */
    BlogArticle selectArticleByAfter(Integer id);
}
