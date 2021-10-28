package com.ivan.blog.controller;

import com.ivan.blog.annotation.MyLog;
import com.ivan.blog.entity.SysPermission;
import com.ivan.blog.service.SysPermissionService;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Ivan
 * @Description: 权限信息
 * @Date: 2019/11/6 20:07
 */
@Controller
@RequestMapping("/permission")
@AllArgsConstructor
public class PermissionController {

    private final SysPermissionService sysPermissionService;

    /**
     * 权限列表查询.
     *
     * @return
     */
    @MyLog("权限列表查询")
    @RequestMapping("/permissionList")
    @RequiresPermissions("permission:list")
    public String permissionList(Model model) {

        model.addAttribute("permissions", sysPermissionService.list());

        return "permission/permissionList";
    }

    /**
     * 新增 页面跳转
     *
     * @return
     */
    @RequestMapping("/permissionAdd")
    @RequiresPermissions("permission:add")
    public String permissionAdd(Model model) {

        return "permission/permissionAdd";
    }

    /**
     * 修改 页面跳转
     *
     * @return
     */
    @RequestMapping("/permissionPut/{id}")
    @RequiresPermissions("permission:put")
    public String permissionPut(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("permission", sysPermissionService.getById(id));

        return "permission/permissionPut";
    }

    /**
     * 新增/修改
     *
     * @param sysPermission
     * @param op
     * @return
     */
    @RequestMapping("insertOrUpdate")
    @ResponseBody
    public Map<String, Object> insertOrUpdate(SysPermission sysPermission, String op) {
        Map<String, Object> map = new HashMap<>();
        if (op.equals("1")) {
            if (sysPermissionService.save(sysPermission)) {
                map.put("status", 200);
            }
        } else {
            if (sysPermissionService.updateById(sysPermission)) {
                map.put("status", 200);
            }
        }

        return map;
    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping("/permissionDel")
    @ResponseBody
    @RequiresPermissions("permission:del")
    public Map<String, Integer> permissionDel(Integer id) {
        Map<String, Integer> map = new HashMap<>();
        if (sysPermissionService.removeById(id)) {
            map.put("status", 200);
        }

        return map;
    }
}
