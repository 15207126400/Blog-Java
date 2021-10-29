package com.ivan.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ivan.blog.entity.BlogCategory;
import com.ivan.blog.mapper.BlogCategoryMapper;
import com.ivan.blog.service.BlogCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Ivan
 * @Description:
 * @Date: 2019/12/9 15:28
 */
@Service
@AllArgsConstructor
public class BlogCategoryServiceImpl extends ServiceImpl<BlogCategoryMapper, BlogCategory> implements BlogCategoryService {

    private final BlogCategoryMapper blogCategoryMapper;

    /**
     * 查询标签详情(关联文章数量)
     * @param id
     * @return
     */
    @Override
    public BlogCategory categoryById(Integer id) {
        return blogCategoryMapper.categoryById(id);
    }

    /**
     * 查询标签列表(关联文章数量)
     * @return
     */
    @Override
    public List<BlogCategory> categoryList() {
        return blogCategoryMapper.categoryList();
    }

    /**
     * 根据文章ID查询标签列表
     * @param id
     * @return
     */
    @Override
    public List<BlogCategory> selectCategoryByArticel(Integer id) {
        return blogCategoryMapper.selectCategoryByArticel(id);
    }

    /**
     * 提取文章标签名称
     *
     * @return
     */
    public List<String> extCategory(List<BlogCategory> categorys) {
        List<String> list = new ArrayList<>();
        for (BlogCategory item : categorys) {
            list.add(item.getName());
        }

        return list;
    }
}
