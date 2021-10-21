package com.ivan.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ivan.blog.model.BlogCategory;
import java.util.List;

/*
 *  @Author: Ivan
 *  @Description:
 *  @Date: 2019/12/9 15:27
 */
public interface BlogCategoryService extends IService<BlogCategory> {
    List<BlogCategory> categoryList();

    List<BlogCategory> selectCategoryByArticel(Integer id);

    List<String> extCategory(List<BlogCategory> categorys);
}
