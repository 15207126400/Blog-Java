package com.ivan.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ivan.blog.entity.*;
import com.ivan.blog.entity.vo.BlogArticleVO;
import com.ivan.blog.mapper.*;
import com.ivan.blog.service.BlogArticleService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  @Author: Ivan
 *  @Description:
 *  @Date: 2019/11/28 16:15
 */
@Service
@AllArgsConstructor
public class BlogArticleServiceImpl extends ServiceImpl<BlogArticleMapper, BlogArticle> implements BlogArticleService {

    private final BlogArticleMapper blogArticleMapper;
    private final BlogCategoryMapper blogCategoryMapper;
    private final BlogArticleContentMapper blogArticleContentMapper;
    private final BlogArticleCategoryMapper blogArticleCategoryMapper;
    private final BlogCommentMapper blogCommentMapper;

    /**
     * 新增文章
     * @param blogArticleVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveByArticle(BlogArticleVO blogArticleVO){
        BlogArticle blogArticle = new BlogArticle();
        BeanUtils.copyProperties(blogArticleVO,blogArticle);
        int num = blogArticleMapper.insert(blogArticle);

        //批量插入
        if(StringUtils.isNotBlank(blogArticleVO.getCategory())){
            insertBatchArticleCategory(blogArticleVO.getCategory(),blogArticle.getId());
        }

        return num > 0;
    }

    /**
     * 编辑文章
     * @param blogArticleVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateByArticle(BlogArticleVO blogArticleVO) {
        //如果不存在文章信息则增加新的文章信息
        LambdaQueryWrapper<BlogArticleContent> lambdaQuery = Wrappers.<BlogArticleContent>lambdaQuery()
                .eq(BlogArticleContent::getArticleId, blogArticleVO.getId());
        BlogArticleContent articleContent = blogArticleContentMapper.selectOne(lambdaQuery);
        if(articleContent == null){
            BlogArticleContent blogArticleContent = new BlogArticleContent();
            blogArticleContent.setContent(blogArticleVO.getContent());
            blogArticleContent.setArticleId(blogArticleVO.getId());
            blogArticleContent.setCreateTime(new Date());
            blogArticleContentMapper.insert(blogArticleContent);
        } else {
            articleContent.setContent(blogArticleVO.getContent());
            articleContent.setUpdateTime(new Date());
            blogArticleContentMapper.updateById(articleContent);
        }

        BlogArticle blogArticle = new BlogArticle();
        BeanUtils.copyProperties(blogArticleVO,blogArticle);
        blogArticle.setUpdateTime(new Date());

        //删除该文章分类信息
        LambdaQueryWrapper<BlogArticleCategory> lambdaQueryCategory = Wrappers.<BlogArticleCategory>lambdaQuery()
                .eq(BlogArticleCategory::getArticleId, blogArticleVO.getId());
        blogArticleCategoryMapper.delete(lambdaQueryCategory);
        //批量插入
        if(StringUtils.isNotBlank(blogArticleVO.getCategory())){
            insertBatchArticleCategory(blogArticleVO.getCategory(),blogArticle.getId());
        }

        return blogArticleMapper.updateById(blogArticle) > 0;
    }

    /**
     * 删除文章
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByArticle(Integer id) {
        boolean bol = blogArticleMapper.deleteById(id) > 0;
        if(bol){
            //删除标签关联数据
            blogArticleCategoryMapper.delete(new LambdaQueryWrapper<BlogArticleCategory>().eq(BlogArticleCategory::getArticleId, id));
            //删除文章内容关联数据
            blogArticleContentMapper.delete(new LambdaQueryWrapper<BlogArticleContent>().eq(BlogArticleContent::getArticleId, id));
            //删除文章评论关联数据
            blogCommentMapper.delete(new LambdaQueryWrapper<BlogComment>().eq(BlogComment::getArticleId, id));
        }

        return bol;
    }

    /**
     * 批量插入文章-标签关联信息
     * @param category
     * @param articleId
     */
    public void insertBatchArticleCategory(String category, Integer articleId){
        List<Integer> list = new ArrayList<>();
        String[] categoryIds = category.split(",");
        for(String item : categoryIds){
            list.add(Integer.valueOf(item));
        }
        //保存分类信息
        blogArticleCategoryMapper.insertBatch(list,articleId);
    }

    /**
     * 随机查找文章列表(轮播图)
     * @return
     */
    @Override
    public List<BlogArticle> selectListByRand() {

        return blogArticleMapper.selectListByRand();
    }

    /**
     * 文章列表(分页)
     * @param page
     * @return
     */
    @Override
    public IPage<BlogArticleVO> getArticleList(Page page) {
        IPage<BlogArticleVO> articleVOPage = blogArticleMapper.selectPageForIndex(page);
        List<BlogArticleVO> articleList = articleVOPage.getRecords();

        if(!CollectionUtils.isEmpty(articleList)){
            //查询文章分类
            for (BlogArticleVO articleVO : articleList) {
                List<BlogCategory> categoryList = blogCategoryMapper.selectCategoryByArticel(articleVO.getId());
                articleVO.setCategory(extCategory(categoryList).toString());
            }
        }

        articleVOPage.setRecords(articleList);

        return articleVOPage;
    }

    /**
     * 根据标签查询文章列表分页
     * @param categoryId
     * @return
     */
    @Override
    public IPage<BlogArticleVO> selectListByCategory(Page page, Integer categoryId) {
        IPage<BlogArticleVO> articleVOPage = blogArticleMapper.selectListByCategory(page, categoryId);
        List<BlogArticleVO> articleList = articleVOPage.getRecords();

        if(!CollectionUtils.isEmpty(articleList)){
            //查询文章分类
            for (BlogArticleVO articleVO : articleList) {
                List<BlogCategory> categoryList = blogCategoryMapper.selectCategoryByArticel(articleVO.getId());
                articleVO.setCategory(extCategory(categoryList).toString());
            }
        }

        articleVOPage.setRecords(articleList);

        return articleVOPage;
    }

    /**
     * 查询详情
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BlogArticleVO selectById(Integer id) {
        BlogArticleVO blogArticleVO = blogArticleMapper.selectById(id);
        if(blogArticleVO != null){
            //更新文章访问量
            BlogArticle blogArticle = new BlogArticle();
            blogArticle.setId(id);
            blogArticle.setTraffic(blogArticleVO.getTraffic() + 1);
            blogArticle.setUpdateTime(new Date());
            blogArticleMapper.updateById(blogArticle);
        }

        //分类集合
        List<BlogCategory> categoryList = blogCategoryMapper.selectCategoryByArticel(id);
        blogArticleVO.setCategory(extCategory(categoryList).toString());
        //上一篇博文
        blogArticleVO.setBeforeArticle(selectArticleByBefore(id));
        //下一篇博文
        blogArticleVO.setAfterArticle(selectArticleByAfter(id));

        return blogArticleVO;
    }

    //查找上一篇
    private BlogArticle selectArticleByBefore(Integer id) {
        LambdaQueryWrapper<BlogArticle> queryWrapper = Wrappers.<BlogArticle>lambdaQuery()
                .lt(BlogArticle::getId,id)
                .orderByDesc(BlogArticle::getCreateTime)
                .last("limit 1");

        return blogArticleMapper.selectOne(queryWrapper);
    }

    //查找下一篇
    private BlogArticle selectArticleByAfter(Integer id) {
        LambdaQueryWrapper<BlogArticle> queryWrapper = Wrappers.<BlogArticle>lambdaQuery()
                .gt(BlogArticle::getId,id)
                .last("limit 1");

        return blogArticleMapper.selectOne(queryWrapper);
    }

    //提取文章标签名称
    private List<String> extCategory(List<BlogCategory> categoryList) {
        List<String> result = new ArrayList<>();
        for (BlogCategory category : categoryList) {
            result.add(category.getName());
        }

        return result;
    }
}
