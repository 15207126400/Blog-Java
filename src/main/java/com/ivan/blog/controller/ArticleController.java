package com.ivan.blog.controller;

import com.ivan.blog.annotation.MyLog;
import com.ivan.blog.dao.BlogArticlePictureMapper;
import com.ivan.blog.model.BlogArticlePicture;
import com.ivan.blog.model.BlogCategory;
import com.ivan.blog.model.SysDict;
import com.ivan.blog.model.dto.BlogArticleDTO;
import com.ivan.blog.service.BlogArticleService;
import com.ivan.blog.service.BlogCategoryService;
import com.ivan.blog.service.SysDictService;
import com.ivan.blog.utils.PicUploudUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/*
 *  @Author: Ivan
 *  @Description:   文章信息控制类
 *  @Date: 2019/10/31 09:38
 */
@Controller
@RequestMapping("/article")
@AllArgsConstructor
@Slf4j
public class ArticleController {

    private final BlogArticleService blogArticleService;
    private final SysDictService sysDictService;
    private final BlogArticlePictureMapper blogArticlePictureMapper;
    private final BlogCategoryService blogCategoryService;

    /**
     * 文章信息列表查询.
     * @return
     */
    @MyLog("文章信息列表查询")
    @RequestMapping("/articleList")
    @RequiresPermissions("article:list")
    public String articleList(Model model){
        model.addAttribute("articles",blogArticleService.list());
        log.info("【执行文章信息列表查询操作: /article/articleList】");

        return "article/articleList";
    }

    /**
     * 新增 页面跳转
     * @return
     */
    @RequestMapping("/articleAdd")
    @RequiresPermissions("article:add")
    public String articleAdd(Model model){
        model.addAttribute("categorys",blogCategoryService.list());

        return "article/articleAdd";
    }

    /**
     * 修改 页面跳转
     * @return
     */
    @RequestMapping("/articlePut/{id}")
    @RequiresPermissions("article:put")
    public String articlePut(Model model ,@PathVariable("id") Integer id){
        model.addAttribute("article",blogArticleService.selectById(id));
        model.addAttribute("categorys",blogCategoryService.list());
        List<Integer> cates = new ArrayList<>();
        List<BlogCategory> categoryList = blogCategoryService.selectCategoryByArticel(id);
        for(BlogCategory item : categoryList){
            cates.add(item.getId());
        }
        model.addAttribute("cates",cates);
        List<SysDict> dicts = sysDictService.findListByType(1001);
        model.addAttribute("dicts",dicts);

        return "article/articlePut";
    }

    @PostMapping(value="insertOrUpdate")
    @ResponseBody
    public Map<String,Object> insertOrUpdate(BlogArticleDTO blogArticleDto, String op, @RequestParam(value = "file") MultipartFile file){
        if(!file.isEmpty()){
            String path = PicUploudUtil.uploadOriginalImageBySftp(file);
            blogArticleDto.setImg(path);
        }
        Map<String,Object> map = new HashMap<>();
        if(op.equals("1")){
            boolean result = blogArticleService.saveByArticle(blogArticleDto);
            if(result){
                map.put("status",200);
            }
        } else {
            boolean result = blogArticleService.updateByArticle(blogArticleDto);
            if(result){
                map.put("status",200);
            }
        }

        return map;
    }

    /**
     * 文章信息删除;
     * @return
     */
    @RequestMapping("/articleDel")
    @ResponseBody
    @RequiresPermissions("article:del")
    public Map<String, Integer> articleDel(Integer id){
        Map<String, Integer> map = new HashMap<>();
        boolean result = blogArticleService.removeById(id);
        if(result){
            map.put("status",200);
        }

        return map;
    }

    /**
     * 富文本文件上传
     * @return
     */
    @RequestMapping("/upload")
    @ResponseBody
    public Map<String, Object> upload(@RequestParam(value = "upload_file") MultipartFile upload_file, Integer articleId){
        Map<String, Object> map = new HashMap<>();
        if(upload_file != null){
            String path = PicUploudUtil.uploadOriginalImageBySftp(upload_file);
            BlogArticlePicture blogArticlePicture = new BlogArticlePicture();
            blogArticlePicture.setArticleId(articleId);
            blogArticlePicture.setPictureUrl(path);
            blogArticlePicture.setCreateTime(new Date());
            blogArticlePictureMapper.insert(blogArticlePicture);

            map.put("success",true);
            map.put("file_path",path);
            log.info("富文本图片上传路径:" + path);
        }

        return map;
    }

}