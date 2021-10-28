package com.ivan.blog.service;

/**
 *  @Author: Ivan
 *  @Description:   博客Github登录
 *  @Date: 2019/12/25 22:59
 */
public interface SignService {

    //github三方登录
    String githubSign(String code);

}
