package com.ivan.blog.service;

import java.util.Map;

/**
 *  @Author: Ivan
 *  @Description:   访问统计
 *  @Date: 2019/10/31 09:37
 */
public interface VisitService {

    Map<String, Object> getVisit();

    void getVisitAndUpdate();
}
