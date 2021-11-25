package com.ivan.blog.controller;

import com.ivan.blog.annotation.MyLog;
import com.ivan.blog.entity.BlogHistory;
import com.ivan.blog.service.BlogHistoryService;
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

/**
 * @author Ivan
 * @date 2021/11/25 09:21
 * @Description 历史记录
 */
@Controller
@RequestMapping("/history")
@AllArgsConstructor
@Slf4j
public class BlogHistoryController {

    private final BlogHistoryService blogHistoryService;

    /**
     * 列表查询.
     *
     * @return
     */
    @MyLog("列表查询")
    @RequestMapping("/historyList")
    @RequiresPermissions("history:list")
    public String historyList(Model model) {

        model.addAttribute("historys", blogHistoryService.list());

        return "history/historyList";
    }

    /**
     * 新增 页面跳转
     *
     * @return
     */
    @RequestMapping("/historyAdd")
    @RequiresPermissions("history:add")
    public String historyAdd(Model model) {

        return "history/historyAdd";
    }

    /**
     * 修改 页面跳转
     *
     * @return
     */
    @RequestMapping("/historyPut/{id}")
    @RequiresPermissions("history:put")
    public String historyPut(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("history", blogHistoryService.getById(id));

        return "history/historyPut";
    }

    @RequestMapping("insertOrUpdate")
    @ResponseBody
    public Map<String, Object> insertOrUpdate(BlogHistory bloghistory, String op) {
        Map<String, Object> map = new HashMap<>();
        if (op.equals("1")) {
            if (blogHistoryService.save(bloghistory)) {
                map.put("status", 200);
            }
        } else {
            if (blogHistoryService.updateById(bloghistory)) {
                map.put("status", 200);
            }
        }

        return map;
    }

    /**
     * 删除;
     *
     * @return
     */
    @RequestMapping("/historyDel")
    @ResponseBody
    @RequiresPermissions("history:del")
    public Map<String, Integer> historyDel(Integer id) {
        Map<String, Integer> map = new HashMap<>();
        if (blogHistoryService.removeById(id)) {
            map.put("status", 200);
        }

        return map;
    }

}
