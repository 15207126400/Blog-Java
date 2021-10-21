package com.ivan.blog.controller;

import com.ivan.blog.annotation.MyLog;
import com.ivan.blog.model.BlogCategory;
import com.ivan.blog.service.BlogCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
 *  @Author: Ivan
 *  @Description:   分类信息
 *  @Date: 2019/11/6 20:07
 */
@Controller
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private BlogCategoryService blogCategoryService;

    /**
     * 分类列表查询.
     * @return
     */
    @MyLog("分类列表查询")
    @RequestMapping("/categoryList")
    @RequiresPermissions("category:list")
    public String categoryList(Model model){

        model.addAttribute("categorys",blogCategoryService.categoryList());

        return "category/categoryList";
    }

    /**
     * 新增 页面跳转
     * @return
     */
    @RequestMapping("/categoryAdd")
    @RequiresPermissions("category:add")
    public String categoryAdd(Model model){

        return "category/categoryAdd";
    }

    /**
     * 修改 页面跳转
     * @return
     */
    @RequestMapping("/categoryPut/{id}")
    @RequiresPermissions("category:put")
    public String categoryPut(Model model ,@PathVariable("id") Integer id){
        model.addAttribute("category",blogCategoryService.getById(id));

        return "category/categoryPut";
    }

    @RequestMapping("insertOrUpdate")
    @ResponseBody
    public Map<String,Object> insertOrUpdate(BlogCategory blogCategory, String op){
        Map<String,Object> map = new HashMap<>();
        if(op.equals("1")){
            if(blogCategoryService.save(blogCategory)){
                map.put("status",200);
            }
        } else {
            blogCategory.setUpdateTime(new Date());
            if(blogCategoryService.updateById(blogCategory)){
                map.put("status",200);
            }
        }

        return map;
    }

    /**
     * 分类删除;
     * @return
     */
    @RequestMapping("/categoryDel")
    @ResponseBody
    @RequiresPermissions("category:del")
    public Map<String, Integer> categoryDel(Integer id){
        Map<String, Integer> map = new HashMap<>();
        if(blogCategoryService.removeById(id)){
            map.put("status",200);
        }

        return map;
    }
}
