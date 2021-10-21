package com.ivan.blog.controller;

import com.ivan.blog.annotation.MyLog;
import com.ivan.blog.model.dto.SysUserDTO;
import com.ivan.blog.service.SysRoleService;
import com.ivan.blog.service.SysUserService;
import com.ivan.blog.utils.MD5Util;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/*
 *  @Author: Ivan
 *  @Description:   用户信息控制类
 *  @Date: 2019/10/31 09:38
 */
@Controller
@RequestMapping("/user")
@AllArgsConstructor
@Slf4j
public class UserController {


    private final SysUserService sysUserService;
    private final SysRoleService sysRoleService;

    /**
     * 用户列表查询.
     * @return
     */
    @MyLog("用户列表查询")
    @RequestMapping("/userList")
    @RequiresPermissions("user:list")
    public String userList(Model model){

        model.addAttribute("users",sysUserService.list());
        log.info("【执行用户列表查询操作: /user/userList】");

        return "user/userList";
    }

    /**
     * 新增 页面跳转
     * @return
     */
    @RequestMapping("/userAdd")
    @RequiresPermissions("user:add")
    public String userAdd(Model model){
        model.addAttribute("roles",sysRoleService.list());

        return "user/userAdd";
    }

    /**
     * 修改 页面跳转
     * @return
     */
    @RequestMapping("/userPut/{id}")
    @RequiresPermissions("user:put")
    public String userPut(Model model ,@PathVariable("id") Integer id){
        model.addAttribute("user",sysUserService.selectByPrimaryKey(id));
        model.addAttribute("roles",sysRoleService.list());

        return "user/userPut";
    }

    @RequestMapping("insertOrUpdate")
    @ResponseBody
    public Map<String,Object> insertOrUpdate(SysUserDTO sysUser, String op){
        Map<String,Object> map = new HashMap<>();
        if(op.equals("1")){
            if(sysUserService.findUserByName(sysUser.getUsername()) == null){
                //加密
                String password = MD5Util.encodePassword(sysUser.getPassword(),sysUser.getCredentialsSalt());
                sysUser.setPassword(password);
                int num = sysUserService.insert(sysUser);
                if(num > 0){
                    map.put("status",200);
                }
            } else {
                map.put("status",400);
            }
        } else {
            int num = sysUserService.updateByPrimaryKeySelective(sysUser);
            if(num > 0) map.put("status",200);
        }

        return map;
    }

    /**
     * 用户删除;
     * @return
     */
    @RequestMapping("/userDel")
    @ResponseBody
    @RequiresPermissions("user:del")
    public Map<String, Integer> userDel(Integer id){
        Map<String, Integer> map = new HashMap<>();
        int num = sysUserService.deleteByPrimaryKey(id);
        if(num > 0){
            map.put("status",200);
        }

        return map;
    }

}