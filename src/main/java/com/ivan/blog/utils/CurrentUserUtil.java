package com.ivan.blog.utils;

import com.ivan.blog.model.SysUser;
import org.apache.shiro.SecurityUtils;

/*
 *  @Author: Ivan
 *  @Description:
 *  @Date: 2019/11/6 11:31
 */
public class CurrentUserUtil {
    /**
     * 获取当前登录用户信息
     * @return
     */
    public static SysUser getCurrentUserinfo(){
        //从shiro权限存储到session中的用户取当前用户
        SysUser user = (SysUser) SecurityUtils.getSubject().getSession().getAttribute("userinfo");

        return user;
    }

}
