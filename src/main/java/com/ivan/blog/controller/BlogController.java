package com.ivan.blog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ivan.blog.Exception.Enum.CommonEnum;
import com.ivan.blog.annotation.MyLog;
import com.ivan.blog.annotation.RequestLimit;
import com.ivan.blog.constants.BlogConstants;
import com.ivan.blog.entity.BlogAccount;
import com.ivan.blog.entity.BlogArticle;
import com.ivan.blog.entity.BlogCategory;
import com.ivan.blog.entity.BlogComment;
import com.ivan.blog.entity.dto.BlogArticleDTO;
import com.ivan.blog.entity.vo.BlogCommentVO;
import com.ivan.blog.minio.MinioTemplate;
import com.ivan.blog.service.*;
import com.ivan.blog.utils.R;
import com.ivan.blog.validation.Save;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @Author: Ivan
 * @Description:
 * @Date: 2019/12/2 11:42
 */
@Controller
@CrossOrigin        //解决前端跨域问题
@RequestMapping("/blog")
@AllArgsConstructor
@Slf4j
public class BlogController {

    private final BlogArticleService blogArticleService;
    private final BlogCategoryService blogCategoryService;
    private final BlogCommentService blogCommentService;
    private final StatisticsService statisticsService;
    private final VisitService visitService;
    private final SignService signService;

    private final MinioTemplate minioTemplate;

    /**
     * 访问博客首页
     *
     * @return
     */
    @MyLog("访问博客首页")
    @RequestMapping("/blogIndex")
    @ResponseBody
    public R blogIndex() {
        List<BlogArticle> result = blogArticleService.selectListByRand();
        //计算访问量
        visitService.getVisitAndUpdate();
        if (CollectionUtils.isEmpty(result)) {
            return R.failed(CommonEnum.ARTICLE_NULL.getResultMsg());
        }

        return R.ok(result);
    }

    /**
     * 获取文章列表
     *
     * @return
     */
    @RequestMapping("/getArticleList")
    @ResponseBody
    public R getArticleList() {
        List<BlogArticleDTO> result = blogArticleService.getArticleList();
        if (CollectionUtils.isEmpty(result)) {
            return R.failed(CommonEnum.ARTICLE_NULL.getResultMsg());
        }
        //查询文章分类
        for (BlogArticleDTO item : result) {
            List<BlogCategory> categorys = blogCategoryService.selectCategoryByArticel(item.getId());
            item.setCategory(blogCategoryService.extCategory(categorys).toString());
        }

        return R.ok(result);
    }

    /**
     * 推荐文章 ---- 三条数据
     *
     * @return
     */
    @RequestMapping("/getListByRand")
    @ResponseBody
    public R getListByRand() {
        List<BlogArticle> result = blogArticleService.selectListByRand();
        if (CollectionUtils.isEmpty(result)) {
            return R.failed(CommonEnum.ARTICLE_NULL.getResultMsg());
        }

        return R.ok(result);
    }

    /**
     * 根据文章标签查询文章列表
     *
     * @return
     */
    @RequestMapping("/getArticleListByCategory")
    @ResponseBody
    public R getArticleListByCategory(Integer categoryId) {
        List<BlogArticleDTO> result = blogArticleService.selectListByCategory(categoryId);
        if (CollectionUtils.isEmpty(result)) {
            return R.failed(CommonEnum.ARTICLE_NULL.getResultMsg());
        }

        //查询文章分类
        for (BlogArticleDTO item : result) {
            List<BlogCategory> categorys = blogCategoryService.selectCategoryByArticel(item.getId());
            item.setCategory(blogCategoryService.extCategory(categorys).toString());
        }

        return R.ok(result);
    }

    /**
     * 获取文章详情
     *
     * @param id
     * @return
     */
    @RequestMapping("/getArticle")
    @ResponseBody
    public R getArticle(Integer id) {
        BlogArticleDTO result = blogArticleService.selectById(id);
        List<BlogCategory> categorys = blogCategoryService.selectCategoryByArticel(id);
        //分类集合
        result.setCategory(blogCategoryService.extCategory(categorys).toString());
        //上一篇博文
        result.setBeforeArticle(blogArticleService.selectArticleByBefore(id));
        //下一篇博文
        result.setAfterArticle(blogArticleService.selectArticleByAfter(id));

        return R.ok(result);
    }

    /**
     * 获取分类列表
     *
     * @return
     */
    @RequestMapping("/getCategoryList")
    @ResponseBody
    public R getCategoryList() {
        List<BlogCategory> result = blogCategoryService.list();
        if (CollectionUtils.isEmpty(result)) {
            return R.failed(CommonEnum.CATEGORY_NULL.getResultMsg());
        }

        return R.ok(result);
    }

    /**
     * 发表评论
     *
     * @return
     */
    @MyLog("发表评论")
    @RequestLimit(count = 5)
    @RequestMapping("/postComment")
    @ResponseBody
    public R postComment(BlogComment blogComment) {
        blogCommentService.postComment(blogComment);

        return R.ok();
    }

    /**
     * 获取评论列表数据
     *
     * @return
     */
    @RequestMapping("/getCommentList")
    @ResponseBody
    public R<IPage> getCommentList(@Param("page") Page page, @Param("articleId") Integer articleId) {
        IPage<BlogCommentVO> result = blogCommentService.selectPage(page, articleId);

        return R.ok(result);
    }

    /**
     * 统计各数量
     *
     * @return
     */
    @RequestMapping("/getStatistical")
    @ResponseBody
    public R getStatistical() {
        Map<String, Integer> result = statisticsService.blogOv();

        return R.ok(result);
    }

    /**
     * 获取github用户信息
     *
     * @param code
     * @return
     */
    @RequestMapping("/getToken")
    @ResponseBody
    public R<String> getToken(String code) {

        return signService.githubSign(code);
    }

    /**
     * 注册账号
     * @param blogAccount
     * @return
     */
    @RequestMapping("/register")
    @ResponseBody
    public R register(@Validated(Save.class) BlogAccount blogAccount) {

        return signService.register(blogAccount);
    }

    /**
     * 修改用户信息
     * @param blogAccount
     * @return
     */
    @RequestMapping("/updateAccount")
    @ResponseBody
    public R updateAccount(BlogAccount blogAccount) {

        return signService.updateAccount(blogAccount);
    }

    /**
     * 账密登录
     * @param blogAccount
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public R login(BlogAccount blogAccount) {

        return signService.login(blogAccount);
    }

    /**
     * 查询用户信息
     * @param username
     * @return
     */
    @RequestMapping("/getUserinfo")
    @ResponseBody
    public R getUserinfo(String username){
        return signService.getUserinfo(username);
    }

    /**
     * 上传头像
     * @param file
     * @return
     */
    @RequestMapping("/uploadAvatar")
    @ResponseBody
    public R uploadAvatar(MultipartFile file) {
        String avatar = null;
        if (!file.isEmpty()) {
            //上传
            String filename = minioTemplate.upload(BlogConstants.MINIO_ACCOUNT_BUCKET, file);
            avatar = BlogConstants.MINIO_MAIN_PATH + BlogConstants.MINIO_ACCOUNT_BUCKET + BlogConstants.ROOT + filename;
        }

        return R.ok(avatar);
    }
}
