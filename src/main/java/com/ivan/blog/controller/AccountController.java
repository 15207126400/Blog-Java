package com.ivan.blog.controller;

import com.ivan.blog.annotation.MyLog;
import com.ivan.blog.service.BlogAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Ivan
 * @Description: 博客用户
 * @Date: 2019/10/31 09:38
 */
@Controller
@RequestMapping("/account")
@AllArgsConstructor
@Slf4j
public class AccountController {

    private final BlogAccountService blogAccountService;

    /**
     * 博客用户列表查询.
     *
     * @return
     */
    @MyLog("博客用户列表查询")
    @RequestMapping("/accountList")
    @RequiresPermissions("account:list")
    public String accountList(Model model) {
        model.addAttribute("accounts", blogAccountService.list());
        log.info("【执行博客用户列表查询操作: /account/accountList】");

        return "account/accountList";
    }

    /**
     * 博客用户删除
     *
     * @return
     */
    @RequestMapping("/accountDel")
    @ResponseBody
    @RequiresPermissions("account:del")
    public Map<String, Integer> accountDel(Integer id) {
        Map<String, Integer> map = new HashMap<>();
        boolean result = blogAccountService.removeById(id);
        if (result) {
            map.put("status", 200);
        }

        return map;
    }

}