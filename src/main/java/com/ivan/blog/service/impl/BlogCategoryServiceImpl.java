package com.ivan.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ivan.blog.dao.BlogCategoryMapper;
import com.ivan.blog.model.BlogCategory;
import com.ivan.blog.service.BlogCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*
 *  @Author: Ivan
 *  @Description:
 *  @Date: 2019/12/9 15:28
 */
@Service
@AllArgsConstructor
public class BlogCategoryServiceImpl extends ServiceImpl<BlogCategoryMapper, BlogCategory> implements BlogCategoryService {
    private final BlogCategoryMapper blogCategoryMapper;
    private final RedisTemplate redisTemplate;

    @Override
    public List<BlogCategory> categoryList() {
        /*String key = RedisUtil.getKeyWithMethod("selectListByCategory");
        Boolean hasflag = redisTemplate.hasKey(key);
        ValueOperations<String, List<BlogCategory>> redis = redisTemplate.opsForValue();
        if(hasflag){
            return redis.get(key);
        } else {
            redis.set(key,blogCategoryMapper.categoryList());
            return redis.get(key);
        }*/

        return blogCategoryMapper.categoryList();
    }

    @Override
    public List<BlogCategory> selectCategoryByArticel(Integer id) {
        return blogCategoryMapper.selectCategoryByArticel(id);
    }

    /**
     * 提取文章标签名称
     * @return
     */
    public List<String> extCategory(List<BlogCategory> categorys){
        List<String> list = new ArrayList<>();
        for(BlogCategory item : categorys){
            list.add(item.getName());
        }

        return list;
    }
}
