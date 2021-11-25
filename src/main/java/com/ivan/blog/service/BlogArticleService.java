package com.ivan.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ivan.blog.entity.BlogArticle;
import com.ivan.blog.entity.vo.BlogArticleVO;

import java.util.List;

/**
 *  @Author: Ivan
 *  @Description:
 *  @Date: 2019/11/28 16:14
 */
public interface BlogArticleService extends IService<BlogArticle> {

    /**
     * 新增文章
     * @param blogArticleVO
     * @return
     */
    boolean saveByArticle(BlogArticleVO blogArticleVO);

    /**
     * 编辑文章
     * @param blogArticleVO
     * @return
     */
    boolean updateByArticle(BlogArticleVO blogArticleVO);

    /**
     * 删除文章
     * @param id
     * @return
     */
    boolean deleteByArticle(Integer id);

    /**
     * 随机查询三条文章信息 ---- 轮播图
     * @return
     */
    List<BlogArticle> selectListByRand();

    /**
     * 博客列表(分页)
     * @return
     */
    IPage<BlogArticleVO> getArticleList(Page page);

    /**
     * 查看文章详情
     * @param id
     * @return
     */
    BlogArticleVO selectById(Integer id);

    /**
     * 根据文章分类查询文章列表
     * @param page
     * @param categoryId
     * @return
     */
    IPage<BlogArticleVO> selectListByCategory(Page page, Integer categoryId);


}
