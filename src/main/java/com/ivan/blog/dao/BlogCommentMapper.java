package com.ivan.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ivan.blog.model.BlogComment;
import com.ivan.blog.model.vo.BlogCommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogCommentMapper extends BaseMapper<BlogComment> {

    List<BlogComment> selectList();

    BlogComment selectById(Integer id);

    /**
     * 通过父编号查询文章列表
     * @param parentId
     * @return
     */
    List<BlogCommentVO> selectByParent(Integer parentId);

    /**
     * 分页查询
     * @param page
     * @param articleId
     * @return
     */
    IPage<BlogComment> mySelectPage(Page page, @Param("blogComment") Integer articleId);
}