package com.ivan.blog.service;

import com.ivan.blog.entity.BlogAccount;
import com.ivan.blog.utils.R;

/**
 *  @Author: Ivan
 *  @Description:   博客Github登录
 *  @Date: 2019/12/25 22:59
 */
public interface SignService {

    //github三方登录
    R githubSign(String code);

    //注册账号
    R register(BlogAccount blogAccount);

    //修改用户信息
    R updateAccount(BlogAccount blogAccount);

    //账密登录
    R login(BlogAccount blogAccount);

    //获取用户信息
    R getUserinfo(String username);

}
