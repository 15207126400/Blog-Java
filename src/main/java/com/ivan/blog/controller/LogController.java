package com.ivan.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ivan.blog.annotation.MyLog;
import com.ivan.blog.model.SysLog;
import com.ivan.blog.service.SysLogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;

/*
 *  @Author: Ivan
 *  @Description:   操作日志信息控制类
 *  @Date: 2019/10/31 09:38
 */
@Controller
@RequestMapping("/log")
@AllArgsConstructor
@Slf4j
public class LogController {

    private final SysLogService sysLogService;

    /**
     * 操作日志列表查询.
     * @return
     */
    @MyLog("操作日志列表查询")
    @RequestMapping("/logList")
    @RequiresPermissions("log:list")
    public String logList(Model model){
        LambdaQueryWrapper<SysLog> logLambdaQueryWrapper = Wrappers.<SysLog>lambdaQuery()
                .orderByDesc(SysLog::getId);
        model.addAttribute("logs",sysLogService.list(logLambdaQueryWrapper));

        return "log/logList";
    }

    /**
     * 删除;
     * @return
     */
    @RequestMapping("/logDel")
    @ResponseBody
    @RequiresPermissions("log:del")
    public Map<String, Integer> logDel(Integer id){
        Map<String, Integer> map = new HashMap<>();
        boolean result = sysLogService.removeById(id);
        if(result){
            map.put("status",200);
        }

        return map;
    }

}