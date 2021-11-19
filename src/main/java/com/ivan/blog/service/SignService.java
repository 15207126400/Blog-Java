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

    //注册或更新账号信息
    R registerOrUpdateAccount(BlogAccount blogAccount);

    //账密登录
    R login(BlogAccount blogAccount);

}
