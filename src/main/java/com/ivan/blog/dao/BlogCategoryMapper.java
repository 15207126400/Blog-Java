package com.ivan.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ivan.blog.model.BlogCategory;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface BlogCategoryMapper extends BaseMapper<BlogCategory> {

    @Select("select bc.id,bc.name,(select count(*) from blog_article_category bac where bc.id = bac.category_id) as number,bc.create_time,bc.update_time FROM blog_category bc")
    List<BlogCategory> categoryList();

    /**
     * 通过文章id查询标签集合
     * @param id 文章id
     * @return
     */
    List<BlogCategory> selectCategoryByArticel(Integer id);
}