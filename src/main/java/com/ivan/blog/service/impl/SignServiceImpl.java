package com.ivan.blog.service.impl;

import com.ivan.blog.service.SignService;
import com.ivan.blog.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 *  @Author: Ivan
 *  @Description:
 *  @Date: 2019/12/17 21:16
 */
@Service
@Slf4j
public class SignServiceImpl implements SignService {

    //github获取access_token的请求url
    private final String GET_GITHUB_TOKEN = "https://github.com/login/oauth/access_token";
    //github的client_id
    private final String GITHUB_CLIENT_ID = "6f44473f8efbb96f16fc";
    //github的client_secret
    private final String GITHUB_CLIENT_SECRET = "3552444809655c7d47b60a2a6b3f8a63742339d3";

    @Override
    public String githubSign(String code) {
        String userinfo = "";

        //获取access_token
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("client_id",GITHUB_CLIENT_ID);
        tokenMap.put("client_secret",GITHUB_CLIENT_SECRET);
        tokenMap.put("code",code);
        String access_token = HttpUtil.doGet(GET_GITHUB_TOKEN,tokenMap);
        //如果access_token存在
        if(StringUtils.isNotBlank(access_token)){
            //请求用户信息
            String path = "https://api.github.com/user?" + access_token;
            userinfo = HttpUtil.doGet(path);
        }
        log.info("userinfo: " + userinfo);

        return userinfo;
    }
}
