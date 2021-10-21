package com.ivan.blog.controller;

import com.ivan.blog.annotation.MyLog;
import com.ivan.blog.model.SysPermission;
import com.ivan.blog.service.SysPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/*
 *  @Author: Ivan
 *  @Description:   权限信息
 *  @Date: 2019/11/6 20:07
 */
@Controller
@RequestMapping("/permission")
@Slf4j
public class PermissionController {
    @Autowired
    private SysPermissionService sysPermissionService;

    /**
     * 权限列表查询.
     * @return
     */
    @MyLog("权限列表查询")
    @RequestMapping("/permissionList")
    @RequiresPermissions("permission:list")
    public String permissionList(Model model){

        model.addAttribute("permissions",sysPermissionService.list());

        return "permission/permissionList";
    }

    /**
     * 新增 页面跳转
     * @return
     */
    @RequestMapping("/permissionAdd")
    @RequiresPermissions("permission:add")
    public String permissionAdd(Model model){

        return "permission/permissionAdd";
    }

    /**
     * 修改 页面跳转
     * @return
     */
    @RequestMapping("/permissionPut/{id}")
    @RequiresPermissions("permission:put")
    public String permissionPut(Model model ,@PathVariable("id") Integer id){
        model.addAttribute("permission",sysPermissionService.selectByPrimaryKey(id));

        return "permission/permissionPut";
    }

    @RequestMapping("insertOrUpdate")
    @ResponseBody
    public Map<String,Object> insertOrUpdate(SysPermission sysPermission, String op){
        Map<String,Object> map = new HashMap<>();
        if(op.equals("1")){
            if(sysPermissionService.insert(sysPermission) > 0){
                map.put("status",200);
            }
        } else {
            if(sysPermissionService.updateByPrimaryKeySelective(sysPermission) > 0){
                map.put("status",200);
            }
        }

        return map;
    }

    /**
     * 用户删除;
     * @return
     */
    @RequestMapping("/permissionDel")
    @ResponseBody
    @RequiresPermissions("permission:del")
    public Map<String, Integer> permissionDel(Integer id){
        Map<String, Integer> map = new HashMap<>();
        if(sysPermissionService.deleteByPrimaryKey(id) > 0){
            map.put("status",200);
        }

        return map;
    }
}
