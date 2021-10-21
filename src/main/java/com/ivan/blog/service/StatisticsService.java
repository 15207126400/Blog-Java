package com.ivan.blog.service;

import java.util.Map;

/*
 *  @Author: Ivan
 *  @Description:   统计接口
 *  @Date: 2019/12/25 22:59
 */
public interface StatisticsService {

    /**
     * 博客数据统计(博客数,标签数,评论数)
     * @return
     */
    Map<String,Integer> blogOv();

    /**
     * 博客后台数据统计(用户,角色,权限,日志)
     * @return
     */
    Map<String,Object> blogBackOv();
}
