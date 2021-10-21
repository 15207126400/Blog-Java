package com.ivan.blog.controller;

import com.ivan.blog.model.BlogComment;
import com.ivan.blog.service.BlogCommentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/*
 *  @Author: Ivan
 *  @Description:   评论信息
 *  @Date: 2019/11/6 20:07
 */
@Controller
@RequestMapping("/comment")
@Slf4j
public class CommentController {
    @Autowired
    private BlogCommentService blogCommentService;

    /**
     * 评论列表查询.
     * @return
     */
    @RequestMapping("/commentList")
    @RequiresPermissions("comment:list")
    public String commentList(Model model){
        model.addAttribute("comments",blogCommentService.selectList());

        return "comment/commentList";
    }

    /**
     * 回复 页面跳转
     * @return
     */
    @RequestMapping("/commentReply/{id}")
    @RequiresPermissions("comment:reply")
    public String commentReply(Model model ,@PathVariable("id") Integer id){
        model.addAttribute("comment",blogCommentService.selectById(id));

        return "comment/commentReply";
    }

    /**
     * 回复
     * @return
     */
    @RequestMapping("/reply")
    @ResponseBody
    public Map<String,Object> reply(BlogComment blogComment){
        Map<String,Object> map = new HashMap<>();
        if(blogCommentService.save(blogComment)){
            map.put("status",200);
        }

        return map;
    }

    /**
     * 评论删除;
     * @return
     */
    @RequestMapping("/commentDel")
    @ResponseBody
    @RequiresPermissions("comment:del")
    public Map<String, Integer> commentDel(Integer id){
        Map<String, Integer> map = new HashMap<>();
        if(blogCommentService.removeById(id)){
            map.put("status",200);
        }

        return map;
    }
}
