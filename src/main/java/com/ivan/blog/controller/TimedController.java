package com.ivan.blog.controller;

import com.ivan.blog.model.SysTimed;
import com.ivan.blog.service.SysDictService;
import com.ivan.blog.service.SysTimedService;
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
 *  @Description:   定时任务控制类
 *  @Date: 2019/10/31 09:38
 */
@Controller
@RequestMapping("/timed")
@AllArgsConstructor
@Slf4j
public class TimedController {

    private final SysTimedService sysTimedService;
    private final SysDictService sysDictService;

    /**
     * 列表查询.
     * @return
     */
    @RequestMapping("/timedList")
    @RequiresPermissions("timed:list")
    public String logList(Model model){
        model.addAttribute("timeds",sysTimedService.list());

        return "timed/timedList";
    }

    /**
     * 新增 页面跳转
     * @return
     */
    @RequestMapping("/timedAdd")
    @RequiresPermissions("timed:add")
    public String dictAdd(Model model){
        model.addAttribute("dicts",sysDictService.findListByType(1000));

        return "timed/timedAdd";
    }

    /**
     * 修改 页面跳转
     * @return
     */
    @RequestMapping("/timedPut/{id}")
    @RequiresPermissions("timed:put")
    public String dictPut(Model model ,@PathVariable("id") Integer id){
        model.addAttribute("timed",sysTimedService.getById(id));
        model.addAttribute("dicts",sysDictService.findListByType(1000));

        return "timed/timedPut";
    }

    @RequestMapping("insertOrUpdate")
    @ResponseBody
    public Map<String,Object> insertOrUpdate(SysTimed sysTimed, String op){
        Map<String,Object> map = new HashMap<>();
        if(op.equals("1")){
            boolean result = sysTimedService.save(sysTimed);
            if(result){
                map.put("status",200);
            }
        } else {
            boolean result = sysTimedService.updateById(sysTimed);
            if(result){
                map.put("status",200);
            }
        }

        return map;
    }

    /**
     * 删除;
     * @return
     */
    @RequestMapping("/timedDel")
    @ResponseBody
    @RequiresPermissions("timed:del")
    public Map<String, Integer> logDel(Integer id){
        Map<String, Integer> map = new HashMap<>();
        boolean result = sysTimedService.removeById(id);
        if(result){
            map.put("status",200);
        }

        return map;
    }

}