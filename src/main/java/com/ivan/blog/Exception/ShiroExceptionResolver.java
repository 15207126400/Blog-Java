package com.ivan.blog.Exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/*
 *  @Author: Ivan
 *  @Description:   shiro权限异常
 *                  统一转发到没有权限提示页面
 *  @Date: 2019/11/1 14:24
 */
@Slf4j
public class ShiroExceptionResolver implements HandlerExceptionResolver{

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView mv = new ModelAndView();
        //如果是shiro无权操作，因为shiro 在操作auno等一部分不进行转发至无权限url
        if(ex instanceof UnauthorizedException){
            mv.setViewName("error");
            mv.addObject("exception", ex.toString().replaceAll("\n", "<br/>"));
        }

        return mv;
    }
}