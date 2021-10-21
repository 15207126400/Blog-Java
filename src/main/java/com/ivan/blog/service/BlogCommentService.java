package com.ivan.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ivan.blog.model.BlogComment;
import com.ivan.blog.model.vo.BlogCommentVO;

import java.util.List;

/*
 *  @Author: Ivan
 *  @Description:
 *  @Date: 2019/12/17 21:16
 */
public interface BlogCommentService extends IService<BlogComment> {

    List<BlogComment> selectList();

    BlogComment selectById(Integer id);

    void postComment(BlogComment blogComment);

    IPage<BlogCommentVO> selectPage(Page page, Integer articleId);

}
