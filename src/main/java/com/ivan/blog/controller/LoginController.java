package com.ivan.blog.controller;

import com.ivan.blog.annotation.MyLog;
import com.ivan.blog.constants.BlogConstants;
import com.ivan.blog.entity.SysUser;
import com.ivan.blog.service.StatisticsService;
import com.ivan.blog.service.SysUserService;
import com.ivan.blog.service.VisitService;
import com.ivan.blog.utils.SystemUserUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Ivan
 * @Description: 登录控制类
 * @Date: 2019/10/31 23:04
 */
@Controller
@AllArgsConstructor
@Slf4j
public class LoginController {

    private final StringRedisTemplate stringRedisTemplate;
    private final SysUserService sysUserService;
    private final StatisticsService statisticsService;
    private final VisitService visitService;

    /**
     * 首页
     * @param model
     * @return
     */
    @RequestMapping({"/", "/index"})
    public String index(Model model) {
        //获取当前登录用户信息
        SysUser user = SystemUserUtil.getCurrentUserinfo();
        model.addAttribute("userinfo", user);

        return "index";
    }

    /**
     * 主页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Model model) {
        //博客数据统计(博客数,标签数,评论数)
        model.addAttribute("blogOv", statisticsService.blogOv());
        model.addAttribute("blogBackOv", statisticsService.blogBackOv());

        //博客访问量统计
        Map<String, Object> resultMap = visitService.getVisit();
        model.addAttribute("dateList", resultMap.get("dateList"));
        model.addAttribute("totalCount", resultMap.get("totalCount"));

        return "home";
    }

    /**
     * 锁定页面
     * @return
     */
    @RequestMapping(value = "/lock", method = RequestMethod.GET)
    public String lock() {
        return "lock";
    }

    /**
     * 登出
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        Session session = SecurityUtils.getSubject().getSession();
        session.stop();
        return "login";
    }

    /**
     * 登录页面, 每次登陆都对用户进行判断，如果被锁定了，直接跳转到lock
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        Session session = SecurityUtils.getSubject().getSession();
        String isLockString = (String) session.getAttribute("user");
        if (isLockString == null) {
            return "login";
        } else {
            return "lock";
        }
    }

    /**
     * 登录
     * @param username      账号
     * @param password      密码
     * @param rememberMe    记住我
     * @return
     */
    @MyLog(value = "后台登录")
    @RequestMapping(value = "/ajaxLogin", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> submitLogin(String username, String password, Boolean rememberMe) {
        Map<String, Object> resultMap = new LinkedHashMap<>();

        Session session = SecurityUtils.getSubject().getSession();
        ValueOperations opsForValue = stringRedisTemplate.opsForValue();
        String key = BlogConstants.SHIRO_LOGIN_COUNT + username;

        if (stringRedisTemplate.hasKey(key)) {
            //计数大于5时，设置用户被锁定一小时
            if (Integer.parseInt(opsForValue.get(key).toString()) >= 5) {
                opsForValue.set(BlogConstants.SHIRO_IS_LOCK + username, "LOCK");
                stringRedisTemplate.expire(BlogConstants.SHIRO_IS_LOCK + username, 1, TimeUnit.HOURS);
            }
        }

        opsForValue.increment(key, 1);
        int leftCount = 5 - Integer.parseInt(opsForValue.get(key).toString());
//        log.info("剩余可试错次数: " + leftCount);
        opsForValue.set(BlogConstants.SHIRO_LOGIN_LEFTCOUNT + username, String.valueOf(leftCount));
        stringRedisTemplate.expire(BlogConstants.SHIRO_LOGIN_LEFTCOUNT + username, 1, TimeUnit.HOURS);

        try {
            if ("LOCK".equals(opsForValue.get(BlogConstants.SHIRO_IS_LOCK + username))) {
                session.setAttribute("user", "lock");
                session.setTimeout(60);
                throw new LockedAccountException();
            }

            UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
            SecurityUtils.getSubject().login(token);

            resultMap.put("status", 200);
            resultMap.put("message", "登录成功");
            opsForValue.set(key, "0");
            opsForValue.set(BlogConstants.SHIRO_IS_LOCK + username, "UNLOCK");

            //存储当前登录用户信息
            SecurityUtils.getSubject().getSession().setAttribute("userinfo", sysUserService.findUserByName(username));
            log.info("登录用户信息: " + sysUserService.findUserByName(username));
        } catch (LockedAccountException e) {
            resultMap.put("status", 400);
            resultMap.put("count", 0);
            resultMap.put("message", "您已经被锁定1小时！");
        } catch (UnknownAccountException e) {
            resultMap.put("status", 300);
            resultMap.put("count", opsForValue.get(BlogConstants.SHIRO_LOGIN_LEFTCOUNT + username));
            resultMap.put("message", e.getMessage());
        } catch (IncorrectCredentialsException e) {
            resultMap.put("status", 100);
            resultMap.put("count", opsForValue.get(BlogConstants.SHIRO_LOGIN_LEFTCOUNT + username));
            resultMap.put("message", e.getMessage());
        } catch (Exception e) {
            resultMap.put("status", 600);
            resultMap.put("count", opsForValue.get(BlogConstants.SHIRO_LOGIN_LEFTCOUNT + username));
            resultMap.put("message", e.getMessage());
        }

        return resultMap;
    }

    /**
     * 错误页面
     * @return
     */
    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String toError() {
        return "error";
    }

}