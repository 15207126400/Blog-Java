package com.ivan.blog.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.ImmutableMap;
import com.ivan.blog.entity.BlogAccount;
import com.ivan.blog.mapper.BlogAccountMapper;
import com.ivan.blog.service.SignService;
import com.ivan.blog.utils.MailUtil;
import com.ivan.blog.utils.R;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: Ivan
 * @Description:
 * @Date: 2019/12/17 21:16
 */
@Service
@Slf4j
@AllArgsConstructor
public class SignServiceImpl implements SignService {

    private final BlogAccountMapper blogAccountMapper;

    //Github授权地址
    private final String GITHUB_AUTH_PATH = "https://api.github.com/user";
    //Github获取Access_Token的请求url
    private final String GET_GITHUB_TOKEN = "https://github.com/login/oauth/access_token";
    //Github的Client_Id
    private final String GITHUB_CLIENT_ID = "6f44473f8efbb96f16fc";
    //Github的Client_Secret
    private final String GITHUB_CLIENT_SECRET = "3552444809655c7d47b60a2a6b3f8a63742339d3";

    @Override
    public R githubSign(String code) {
        String userinfo = "";
        BlogAccount account = null;

        //构造获取Token参数
        Map<String, Object> tokenMap = new ImmutableMap.Builder<String, Object>()
                .put("client_id", GITHUB_CLIENT_ID)
                .put("client_secret", GITHUB_CLIENT_SECRET)
                .put("code", code)
                .build();
        //请求Token
        String access_token = HttpUtil.get(GET_GITHUB_TOKEN, tokenMap);
        String token = access_token.split("&")[0].split("=")[1];
        log.info("截取处理后的access_token为: {}", token);

        //如果access_token存在
        if (StringUtils.isNotBlank(access_token)) {
            //请求用户信息(2020年2月份github官方更新获取用户信息API, 考虑到安全性问题将access_token挪到header里进行传递)
            HttpRequest userGetRequest = HttpUtil.createGet(GITHUB_AUTH_PATH);
            userGetRequest.addHeaders(ImmutableMap.of("Authorization", "token " + token));
            userinfo = userGetRequest.execute().body();
        }
        log.info("Github返回用户信息报文: " + userinfo);

        //同步创建账号, 并邮件通知博客用户
        JSONObject userObj = JSONObject.parseObject(userinfo);
        if (!Objects.isNull(userObj)) {
            //账号
            String username = userObj.getString("email");
            //密码
            String password = userObj.getString("id");
            //昵称
            String nickname = userObj.getString("name");
            //头像
            String avatar = userObj.getString("avatar_url");

            account = blogAccountMapper.selectOne(new LambdaQueryWrapper<BlogAccount>()
                    .eq(BlogAccount::getUsername, username)
                    .eq(BlogAccount::getDelFlag, "0"));
            if (Objects.isNull(account)) {
                //赋值
                account = new BlogAccount();
                account.setUsername(username);
                account.setPassword(password);
                account.setNickname(nickname);
                account.setAvatar(avatar);
                account.setLastLoginTime(new Date());
                boolean bol = blogAccountMapper.insert(account) > 0;
                if (bol) {
                    //邮件信息
                    StringBuilder sb = new StringBuilder();
                    String title = "登录信息推送";
                    sb.append("登录成功！");
                    sb.append("\r\n");
                    sb.append("已为您快捷生成登录账密，后续可直接通过该账密进行登录，信息如下：");
                    sb.append("\r\n");
                    sb.append("账号：" + username);
                    sb.append("\r\n");
                    sb.append("密码：" + password);
                    sb.append("\r\n");
                    sb.append("昵称：" + nickname);
                    sb.append("\r\n");
                    sb.append("头像：" + avatar);

                    MailUtil.sendMail(title, sb.toString(), username);
                }
            } else {
                account.setLastLoginTime(new Date());
                blogAccountMapper.updateById(account);
            }
        }

        log.info("返回数据信息为: {}", account);

        return R.ok(account);
    }

    /**
     * 注册或更新账号信息
     *
     * @param blogAccount
     * @return
     */
    @Override
    public R registerOrUpdateAccount(BlogAccount blogAccount) {
        //校验昵称
        String regexEmail = "/^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$/";
        Pattern patternEmail = Pattern.compile(regexEmail);
        Matcher matchEmail = patternEmail.matcher(blogAccount.getUsername());
        boolean bEmail = matchEmail.matches();
        if (bEmail) {
            return R.error("邮箱格式不规范!");
        }

        //校验昵称
        String regexNickname = "^[a-zA-Z0-9\u4E00-\u9FA5]+$";
        Pattern patternNickname = Pattern.compile(regexNickname);
        Matcher matchNickname = patternNickname.matcher(blogAccount.getNickname());
        boolean bNickname = matchNickname.matches();
        if (!bNickname) {
            return R.error("昵称只允许输入汉字、英文或者数字！");
        }

        //校验账号是否存在
        if(Objects.isNull(blogAccount.getId())){//表示注册
            BlogAccount account = blogAccountMapper.selectOne(new LambdaQueryWrapper<BlogAccount>()
                    .eq(BlogAccount::getUsername, blogAccount.getUsername()).eq(BlogAccount::getDelFlag, "0"));
            if (!Objects.isNull(account)) {
                return R.error("该账号已被注册！");
            }

            //头像处理
            if(StringUtils.isBlank(blogAccount.getAvatar())){
                blogAccount.setAvatar("http://1.117.251.254:9000/system/default_avatar.png");
            }

            //注册
            blogAccount.setLastLoginTime(new Date());
            boolean bol = blogAccountMapper.insert(blogAccount) > 0;
            if (bol) {
                //推送邮件
                StringBuilder sb = new StringBuilder();
                String title = "注册信息推送";
                sb.append("注册成功！");
                sb.append("\r\n");
                sb.append("您的账号信息如下：");
                sb.append("\r\n");
                sb.append("账号：" + blogAccount.getUsername());
                sb.append("\r\n");
                sb.append("密码：" + blogAccount.getPassword());
                sb.append("\r\n");
                sb.append("昵称：" + blogAccount.getNickname());
                MailUtil.sendMail(title, sb.toString(), blogAccount.getUsername());
            }
            log.info("注册用户信息为: {}", blogAccount);
        } else {//更新用户信息
            blogAccountMapper.updateById(blogAccount);
        }

        return R.ok(blogAccount);
    }

    /**
     * 账密登录
     *
     * @param blogAccount
     * @return
     */
    @Override
    public R login(BlogAccount blogAccount) {
        if (StringUtils.isBlank(blogAccount.getUsername())) {
            return R.error("账号不允许为空！");
        }
        if (StringUtils.isBlank(blogAccount.getUsername())) {
            return R.error("密码不允许为空！");
        }

        BlogAccount account = blogAccountMapper.selectOne(new LambdaQueryWrapper<BlogAccount>()
                .eq(BlogAccount::getUsername, blogAccount.getUsername()).eq(BlogAccount::getDelFlag, "0"));
        if (Objects.isNull(account)) {
            return R.error("账号输入有误，请重新输入！");
        }

        if (!StringUtils.equals(blogAccount.getPassword(), account.getPassword())) {
            return R.error("密码输入有误，请重新输入！");
        }

        //更新最后登录时间
        account.setLastLoginTime(new Date());
        blogAccountMapper.updateById(account);

        return R.ok(account);
    }
}
