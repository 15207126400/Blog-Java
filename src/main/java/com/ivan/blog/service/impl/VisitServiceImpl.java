package com.ivan.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ivan.blog.entity.SysVisit;
import com.ivan.blog.entity.tool.DateModel;
import com.ivan.blog.mapper.SysVisitMapper;
import com.ivan.blog.service.VisitService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class VisitServiceImpl implements VisitService {

    private final SysVisitMapper sysVisitMapper;

    /**
     * 访问量统计
     * @return
     */
    @Override
    public Map<String, Object> getVisit() {
        Map<String, Object> resultMap = new HashMap<>();

        //读取访问统计数据
        String visit = sysVisitMapper.selectById(1).getContent();

        //转JSON
        JSONObject visitJson = JSON.parseObject(visit);
        JSONArray dateList = visitJson.getJSONArray("dateList");
        //访问总量
        Object totalCount = visitJson.get("totalCount");
        List<DateModel> list = new ArrayList<>();
        for (int i = 0; i < dateList.size(); i++) {
            JSONObject jsonObject = dateList.getJSONObject(i);
            DateModel dateModel = new DateModel();
            dateModel.setDate(jsonObject.getString("date"));
            dateModel.setCount(jsonObject.getString("count"));
            list.add(dateModel);
        }
        //组装数据
        resultMap.put("dateList", list);
        resultMap.put("totalCount", totalCount);

        return resultMap;
    }

    /**
     * 获取日期访问量并进行数据更新
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void getVisitAndUpdate() {
        //获取数据模板中的数据
        Map<String, Object> visitMap = this.getVisit();
        //加锁避免并发场景数据处理数据异常
        synchronized (this) {
            //日期数据列表
            List<DateModel> dateList = (List<DateModel>) visitMap.get("dateList");
            //访问总数
            Long totalCount = Long.parseLong(visitMap.get("totalCount").toString());
            totalCount++;
            DateModel dateModel = dateList.get(dateList.size() - 1);
            //日期校验
            boolean bol = dateChange(dateModel.getDate());
            //如果当前时间在目标时间之后,说明需要更新数据
            if (bol) {
                dateList.remove(0);
                DateModel dModel = new DateModel();
                dModel.setCount("0");
                dModel.setDate(LocalDate.now().toString());
                dateList.add(dModel);
            }
            //数据处理
            getDataProcessing(visitMap, dateList, totalCount);

            //写入数据
            SysVisit visit = sysVisitMapper.selectById(1);
            visit.setContent(new JSONObject(visitMap).toJSONString());
            boolean visitBol = sysVisitMapper.updateById(visit) > 0;
            log.info("访问量统计数据更新结果: {}", visitBol);
        }
    }

    //处理
    private void getDataProcessing(Map<String, Object> visitMap, List<DateModel> dateList, Long totalCount) {
        List<JSONObject> resultList = new ArrayList<>();
        for (int i = 0; i < dateList.size(); i++) {
            DateModel model = dateList.get(i);
            if (i == dateList.size() - 1) {
                Long count = Long.parseLong(model.getCount());
                count++;
                model.setCount(String.valueOf(count));
            }
            JSONObject obj = (JSONObject) JSONObject.toJSON(model);
            resultList.add(obj);
        }

        visitMap.put("dateList", resultList);
        visitMap.put("totalCount", totalCount);
    }

    /**
     * 日期校验,返回true说明需要改动文本中的数据
     *
     * @param date 目标日期
     * @return 校验结果
     */
    public static boolean dateChange(String date) {
        LocalDate today = LocalDate.now();
        LocalDate beginDateTime = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        boolean ivan = today.isAfter(beginDateTime);
        System.out.println(ivan);

        return ivan;
    }

}
