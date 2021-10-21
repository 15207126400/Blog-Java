package com.ivan.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ivan.blog.dao.*;
import com.ivan.blog.model.BlogArticle;
import com.ivan.blog.model.BlogArticleCategory;
import com.ivan.blog.model.BlogArticleContent;
import com.ivan.blog.model.dto.BlogArticleDTO;
import com.ivan.blog.service.BlogArticleService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 *  @Author: Ivan
 *  @Description:
 *  @Date: 2019/11/28 16:15
 */
@Service
@AllArgsConstructor
public class BlogArticleServiceImpl extends ServiceImpl<BlogArticleMapper, BlogArticle> implements BlogArticleService {

    private final BlogArticleMapper blogArticleMapper;
    private final BlogArticleContentMapper blogArticleContentMapper;
    private final BlogArticleCategoryMapper blogArticleCategoryMapper;
    private final RedisTemplate redisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveByArticle(BlogArticleDTO blogArticleDTO){
        BlogArticle blogArticle = new BlogArticle();
        BeanUtils.copyProperties(blogArticleDTO,blogArticle);
        int num = blogArticleMapper.insert(blogArticle);

        //批量插入
        if(StringUtils.isNotBlank(blogArticleDTO.getCategory())){
            insertBatchArticleCategory(blogArticleDTO.getCategory(),blogArticle.getId());
        }

        return num > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateByArticle(BlogArticleDTO blogArticleDTO) {
        //如果不存在文章信息则增加新的文章信息
        LambdaQueryWrapper<BlogArticleContent> lambdaQuery = Wrappers.<BlogArticleContent>lambdaQuery()
                .eq(BlogArticleContent::getArticleId,blogArticleDTO.getId());
        BlogArticleContent articleContent = blogArticleContentMapper.selectOne(lambdaQuery);
        if(articleContent == null){
            BlogArticleContent blogArticleContent = new BlogArticleContent();
            blogArticleContent.setContent(blogArticleDTO.getContent());
            blogArticleContent.setArticleId(blogArticleDTO.getId());
            blogArticleContent.setCreateTime(new Date());
            blogArticleContentMapper.insert(blogArticleContent);
        } else {
            articleContent.setContent(blogArticleDTO.getContent());
            articleContent.setUpdateTime(new Date());
            blogArticleContentMapper.updateById(articleContent);
        }

        BlogArticle blogArticle = new BlogArticle();
        BeanUtils.copyProperties(blogArticleDTO,blogArticle);
        blogArticle.setUpdateTime(new Date());

        //删除该文章分类信息
        LambdaQueryWrapper<BlogArticleCategory> lambdaQueryCategory = Wrappers.<BlogArticleCategory>lambdaQuery()
                .eq(BlogArticleCategory::getArticleId,blogArticleDTO.getId());
        blogArticleCategoryMapper.delete(lambdaQueryCategory);
        //批量插入
        if(StringUtils.isNotBlank(blogArticleDTO.getCategory())){
            insertBatchArticleCategory(blogArticleDTO.getCategory(),blogArticle.getId());
        }

        return blogArticleMapper.updateById(blogArticle) > 0;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BlogArticleDTO selectById(Integer id) {
        BlogArticleDTO blogArticleDTO = blogArticleMapper.selectById(id);
        if(blogArticleDTO != null){
            //更新文章访问量
            BlogArticle blogArticle = new BlogArticle();
            blogArticle.setId(id);
            blogArticle.setTraffic(blogArticleDTO.getTraffic() + 1);
            blogArticle.setUpdateTime(new Date());
            blogArticleMapper.updateById(blogArticle);
        }

        return blogArticleMapper.selectById(id);
    }

    @Override
    public List<BlogArticleDTO> getArticleList() {
        LambdaQueryWrapper<BlogArticle> lambdaQueryCategory = Wrappers.<BlogArticle>lambdaQuery()
                .orderByAsc(BlogArticle::getIsTop)
                .orderByDesc(BlogArticle::getCreateTime);

        List<BlogArticle> articles = blogArticleMapper.selectList(lambdaQueryCategory);
        List<BlogArticleDTO> result = new ArrayList<>();
        for(BlogArticle item : articles){
            BlogArticleDTO blogArticleDTO = new BlogArticleDTO();
            BeanUtils.copyProperties(item, blogArticleDTO);
            result.add(blogArticleDTO);
        }

        return result;
    }

    @Override
    public List<BlogArticleDTO> selectListByCategory(Integer categoryId) {
        return blogArticleMapper.selectListByCategory(categoryId);
    }

    @Override
    public List<BlogArticle> selectListByRand() {

        return blogArticleMapper.selectListByRand();
    }

    @Override
    public BlogArticle selectArticleByBefore(Integer id) {
        LambdaQueryWrapper<BlogArticle> queryWrapper = Wrappers.<BlogArticle>lambdaQuery()
                .lt(BlogArticle::getId,id)
                .orderByDesc(BlogArticle::getCreateTime)
                .last("limit 1");

        return blogArticleMapper.selectOne(queryWrapper);
    }

    @Override
    public BlogArticle selectArticleByAfter(Integer id) {
        LambdaQueryWrapper<BlogArticle> queryWrapper = Wrappers.<BlogArticle>lambdaQuery()
                .gt(BlogArticle::getId,id)
                .last("limit 1");

        return blogArticleMapper.selectOne(queryWrapper);
    }


}
