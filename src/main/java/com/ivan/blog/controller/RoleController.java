package com.ivan.blog.controller;

import com.ivan.blog.annotation.MyLog;
import com.ivan.blog.entity.SysRole;
import com.ivan.blog.service.SysRoleService;
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
 * @Description: 角色信息控制类
 * @Date: 2019/10/31 09:38
 */
@Controller
@RequestMapping("/role")
@AllArgsConstructor
public class RoleController {

    private final SysRoleService sysRoleService;

    /**
     * 角色列表查询.
     *
     * @return
     */
    @MyLog("角色列表查询")
    @RequestMapping("/roleList")
    @RequiresPermissions("role:list")
    public String roleInfo(Model model) {
        model.addAttribute("roles", sysRoleService.list());

        return "role/roleList";
    }

    /**
     * 新增 页面跳转
     *
     * @return
     */
    @RequestMapping("/roleAdd")
    @RequiresPermissions("role:add")
    public String roleAdd(Model model) {
        model.addAttribute("jsonData", sysRoleService.getTreeByRole());

        return "role/roleAdd";
    }

    /**
     * 修改 页面跳转
     *
     * @return
     */
    @RequestMapping("/rolePut/{id}")
    @RequiresPermissions("role:put")
    public String rolePut(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("role", sysRoleService.getById(id));
        model.addAttribute("jsonData", sysRoleService.getTreeByRole(String.valueOf(id)));

        return "role/rolePut";
    }

    /**
     * 新增/修改
     *
     * @param sysRole
     * @param op
     * @return
     */
    @RequestMapping("insertOrUpdate")
    @ResponseBody
    public Map<String, Object> insertOrUpdate(SysRole sysRole, String op) {
        Map<String, Object> map = new HashMap<>();
        if (op.equals("1")) {
            if (sysRoleService.insertByRoleId(sysRole)) {
                map.put("status", 200);
            }
        } else {
            if (sysRoleService.updateByRoleId(sysRole)) {
                map.put("status", 200);
            }
        }
        return map;
    }

    /**
     * 角色删除;
     *
     * @return
     */
    @RequestMapping("/roleDel")
    @ResponseBody
    @RequiresPermissions("role:del")
    public Map<String, Integer> roleDel(Integer id) {
        Map<String, Integer> map = new HashMap<>();
        if (sysRoleService.deleteByRoleId(id)) {
            map.put("status", 200);
        }

        return map;
    }

}