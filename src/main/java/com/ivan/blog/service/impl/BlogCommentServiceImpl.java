package com.ivan.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ivan.blog.dao.BlogArticleMapper;
import com.ivan.blog.dao.BlogCommentMapper;
import com.ivan.blog.model.BlogArticle;
import com.ivan.blog.model.BlogComment;
import com.ivan.blog.model.vo.BlogCommentVO;
import com.ivan.blog.service.BlogCommentService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/*
 *  @Author: Ivan
 *  @Description:
 *  @Date: 2019/12/17 21:16
 */
@Service
@AllArgsConstructor
public class BlogCommentServiceImpl extends ServiceImpl<BlogCommentMapper, BlogComment> implements BlogCommentService {

    private final BlogCommentMapper blogCommentMapper;
    private final BlogArticleMapper blogArticleMapper;

    @Override
    public List<BlogComment> selectList() {
        return blogCommentMapper.selectList();
    }

    @Override
    public BlogComment selectById(Integer id) {
        return blogCommentMapper.selectById(id);
    }

    /**
     * 发表评论
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void postComment(BlogComment blogComment){
        //获取文章信息并存储
        BlogArticle article = blogArticleMapper.selectById(blogComment.getArticleId());
        blogComment.setArticleTitle(article.getTitle());

        //如果是发表评论,获取该文章评论数量,设置该评论当前楼层
        if(blogComment.getParentId() == 0){
            LambdaQueryWrapper<BlogComment> queryWrapper = Wrappers.<BlogComment>lambdaQuery()
                    .eq(BlogComment::getArticleId,blogComment.getArticleId())
                    .eq(BlogComment::getParentId,0);
            Integer count = blogCommentMapper.selectCount(queryWrapper);
            blogComment.setLadder(count + 1);
        }

        //如果没有昵称,则设置默认昵称
        if(StringUtils.isBlank(blogComment.getName())){
            blogComment.setName("匿名用户");
        }

        //保存评论
        blogCommentMapper.insert(blogComment);
    }

    /**
     * 根据文章id查找对应的评论
     * @param articleId
     * @return
     */
    @Override
    public IPage<BlogCommentVO> selectPage(Page page, Integer articleId) {
        LambdaQueryWrapper<BlogComment> queryWrapper = Wrappers.<BlogComment>lambdaQuery()
                .eq(StringUtils.isNotBlank(String.valueOf(articleId)),BlogComment::getArticleId, articleId);
        IPage<BlogComment> iPage = blogCommentMapper.selectPage(page, queryWrapper);
        IPage<BlogCommentVO> iPageVo = new Page();
        BeanUtils.copyProperties(iPage, iPageVo);

        List<BlogCommentVO> records = iPage.getRecords().stream().map(e -> {
            BlogCommentVO blogCommentVO = new BlogCommentVO();
            BeanUtils.copyProperties(e, blogCommentVO);

            return blogCommentVO;
        }).collect(Collectors.toList());

        //递归
        recursiveComments(records);

        //有父元素的集合list需要从原list中抹除
        Iterator<BlogCommentVO> it = records.iterator();
        while(it.hasNext()){
            BlogCommentVO x = it.next();
            if(x.getParentId() != 0) it.remove();
        }

        iPageVo.setRecords(records);

        return iPageVo;
    }

    //递归
    private void recursiveComments(List<BlogCommentVO> blogCommentList){
        if (blogCommentList.size() > 0) {
            for (BlogCommentVO blogComment: blogCommentList) {
                //通过父编号查询
                List<BlogCommentVO> blogComments = blogCommentMapper.selectByParent(blogComment.getId());
                blogComment.setBlogCommentList(blogComments);
                recursiveComments(blogComments);
            }
        }
    }

}
