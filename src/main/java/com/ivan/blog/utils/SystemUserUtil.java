package com.ivan.blog.utils;

import com.ivan.blog.entity.SysUser;
import org.apache.shiro.SecurityUtils;

/**
 * @Author: Ivan
 * @Description:
 * @Date: 2019/11/6 11:31
 */
public class SystemUserUtil {
    /**
     * 获取当前登录用户信息
     *
     * @return
     */
    public static SysUser getCurrentUserinfo() {
        //从shiro授权时存储session中读取当前用户信息
        return (SysUser) SecurityUtils.getSubject().getSession().getAttribute("userinfo");
    }

    /**
     * 密码盐
     *
     * @return
     */
    public static String getCredentialsSalt() {
        return "8d78869f470951332959580424d4bf4f";
    }

}
