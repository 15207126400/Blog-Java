package com.ivan.blog.controller;

import com.ivan.blog.annotation.MyLog;
import com.ivan.blog.model.SysDict;
import com.ivan.blog.service.SysDictService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

/*
 *  @Author: Ivan
 *  @Description:   数据字典控制类
 *  @Date: 2019/10/31 09:38
 */
@Controller
@RequestMapping("/dict")
@AllArgsConstructor
@Slf4j
public class DictController {

    private final SysDictService sysDictService;

    /**
     * 数据字典列表查询.
     * @return
     */
    @MyLog("数据字典列表查询")
    @RequestMapping("/dictList")
    @RequiresPermissions("dict:list")
    public String dictList(Model model){

        model.addAttribute("dicts",sysDictService.list());
        log.info("【执行数据字典列表查询操作: /dict/dictList】");

        return "dict/dictList";
    }

    /**
     * 新增 页面跳转
     * @return
     */
    @RequestMapping("/dictAdd")
    @RequiresPermissions("dict:add")
    public String dictAdd(Model model){
        model.addAttribute("dicts",sysDictService.findListByType(1000));

        return "dict/dictAdd";
    }

    /**
     * 修改 页面跳转
     * @return
     */
    @RequestMapping("/dictPut/{id}")
    @RequiresPermissions("dict:put")
    public String dictPut(Model model ,@PathVariable("id") Integer id){
        model.addAttribute("dict",sysDictService.getById(id));
        model.addAttribute("dicts",sysDictService.findListByType(1000));

        return "dict/dictPut";
    }

    @RequestMapping("insertOrUpdate")
    @ResponseBody
    public Map<String,Object> insertOrUpdate(SysDict sysDict,String op){
        Map<String,Object> map = new HashMap<>();
        if(op.equals("1")){
            boolean result = sysDictService.save(sysDict);
            if(result){
                map.put("status",200);
            }
        } else {
            boolean result = sysDictService.updateById(sysDict);
            if(result){
                map.put("status",200);
            }
        }

        return map;
    }

    /**
     * 数据字典删除;
     * @return
     */
    @RequestMapping("/dictDel")
    @ResponseBody
    @RequiresPermissions("dict:del")
    public Map<String, Integer> dictDel(Integer id){
        Map<String, Integer> map = new HashMap<>();
        boolean result = sysDictService.removeById(id);
        if(result){
            map.put("status",200);
        }

        return map;
    }

}