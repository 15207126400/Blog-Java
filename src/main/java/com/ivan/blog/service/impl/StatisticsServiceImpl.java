package com.ivan.blog.service.impl;

import com.ivan.blog.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

/*
 *  @Author: Ivan
 *  @Description:
 *  @Date: 2019/12/25 23:00
 */
@Service
@AllArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {
    private final SysUserService sysUserService;
    private final SysRoleService sysRoleService;
    private final SysPermissionService sysPermissionService;
    private final SysLogService sysLogService;
    private final BlogArticleService blogArticleService;
    private final BlogCategoryService blogCategoryService;
    private final BlogCommentService blogCommentService;

    @Override
    public Map<String, Integer> blogOv() {
        Map<String,Integer> map = new HashMap<>();
        map.put("articleCount",blogArticleService.count());
        map.put("tagsCount",blogCategoryService.count());
        map.put("commontCount",blogCommentService.count());

        return map;
    }

    @Override
    public Map<String, Object> blogBackOv() {
        Map<String,Object> map  = new HashMap<>();
        map.put("userCount",sysUserService.list().size());
        map.put("roleCount",sysRoleService.list().size());
        map.put("permissionCount",sysPermissionService.list().size());
        map.put("logCount",sysLogService.list().size());

        return map;
    }
}
