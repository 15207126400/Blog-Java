package com.ivan.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ivan.blog.dao.BlogArticleContentMapper;
import com.ivan.blog.model.BlogArticleContent;
import com.ivan.blog.service.BlogArticleContentSerivce;
import org.springframework.stereotype.Service;

/*
 *  @Author: Ivan
 *  @Description:
 *  @Date: 2019/11/29 15:16
 */
@Service
public class BlogArticleContentSerivceImpl extends ServiceImpl<BlogArticleContentMapper, BlogArticleContent> implements BlogArticleContentSerivce {
}
