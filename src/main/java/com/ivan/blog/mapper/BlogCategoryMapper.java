package com.ivan.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ivan.blog.entity.BlogCategory;

import java.util.List;

public interface BlogCategoryMapper extends BaseMapper<BlogCategory> {

    /**
     * 查询标签详情(关联文章数量)
     * @param id
     * @return
     */
    BlogCategory categoryById(Integer id);

    /**
     * 查询标签列表(关联文章数量)
     * @return
     */
    List<BlogCategory> categoryList();

    /**
     * 通过文章id查询标签集合
     * @param id 文章id
     * @return
     */
    List<BlogCategory> selectCategoryByArticel(Integer id);
}