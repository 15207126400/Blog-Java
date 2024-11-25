package com.ivan.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ivan.blog.entity.BlogComment;
import com.ivan.blog.entity.vo.BlogCommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogCommentMapper extends BaseMapper<BlogComment> {

    /**
     * 查询评论列表
     * @return
     */
    List<BlogComment> selectList();

    /**
     * 查询评论详情
     * @param id
     * @return
     */
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
    IPage<BlogComment> mySelectPage(Page page, @Param("articleId") Integer articleId);

    /**
     * 查询最新的五条留言
     * @return
     */
    List<BlogComment> getCommentListByNew();
}