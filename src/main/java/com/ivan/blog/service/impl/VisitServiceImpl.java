package com.ivan.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ivan.blog.Exception.BizException;
import com.ivan.blog.model.tool.DateModel;
import com.ivan.blog.service.VisitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class VisitServiceImpl implements VisitService {
    //服务器统计数据
    public final static String FILE_PATH = System.getProperty("os.name").toLowerCase().startsWith("mac") ? "/Users/yanfei/Documents/visit-count.txt" : "/root/ftp/files/blog/visit-count.txt";

    //本地统计数据
    //public final static String FILE_PATH = "/Users/yanfei/Documents/visit-count.txt";

    @Override
    public Map<String, Object> getVisit() {
        Map<String, Object> map = new HashMap<>();
        try{
            //读取文件(字符流)
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(FILE_PATH),"UTF-8"));
            //循环读取数据
            String str = null;
            StringBuffer content = new StringBuffer();
            while ((str = in.readLine()) != null) {
                content.append(str);
            }
            JSONObject obj = JSON.parseObject(content.toString());
            JSONArray dateList = obj.getJSONArray("dateList");
            //访问总量
            Object totalCount = obj.get("totalCount");
            List<DateModel> list = new ArrayList<>();
            for (int i = 0; i < dateList.size(); i++) {
                JSONObject jsonObject = dateList.getJSONObject(i);
                DateModel dateModel = new DateModel();
                dateModel.setDate(jsonObject.getString("date"));
                dateModel.setCount(jsonObject.getString("count"));
                list.add(dateModel);
            }
            map.put("dateList",list);
            map.put("totalCount",totalCount);
            //关闭流
            in.close();

            return map;
        } catch (Exception e){
            throw new BizException("获取访问数据异常: " + e);
        }
    }

    /**
     * 获取日期访问量并进行数据更新
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void getVisitAndUpdate() {
        //获取数据模板中的数据
        Map<String, Object> resultMap = getVisit();
        //加锁避免并发场景数据处理数据异常
        synchronized(this){
            //日期数据列表
            Object dList = resultMap.get("dateList");
            List<DateModel> dateList = (List<DateModel>)dList;
            //访问总数
            Object tCount = resultMap.get("totalCount");
            Long totalCount = Long.parseLong(tCount.toString());
            totalCount++;
            DateModel dateModel = dateList.get(dateList.size() - 1);
            //日期校验
            boolean ivan = dateChange(dateModel.getDate());
            //如果当前时间在目标时间之后,说明需要更新数据
            if(ivan){
                dateList.remove(0);
                DateModel dModel = new DateModel();
                dModel.setCount("0");
                dModel.setDate(LocalDate.now().toString());
                dateList.add(dModel);
            }
            //数据处理
            getDataProcessing(resultMap, dateList, totalCount);

            try {
                //写入相应的文件
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILE_PATH),"UTF-8"));
                JSONObject jsonObject = new JSONObject(resultMap);
                out.write(jsonObject.toJSONString());
                //清除缓存
                out.flush();
                //关闭流
                out.close();
            } catch (Exception e){
                throw new BizException("处理访问数据异常: " + e);
            }
        }
    }

    private void getDataProcessing(Map<String, Object> resultMap, List<DateModel> dateList, Long totalCount) {
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

        resultMap.put("dateList", resultList);
        resultMap.put("totalCount", totalCount);
    }


    /**
     * 日期校验,返回true说明需要改动文本中的数据
     * @param date  目标日期
     * @return      校验结果
     */
    public static boolean dateChange(String date){
        LocalDate today = LocalDate.now();
        LocalDate beginDateTime = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        boolean ivan = today.isAfter(beginDateTime);
        System.out.println(ivan);

        return ivan;
    }

    /**
     * 获取过去几天以内的日期数组(暂未用到)
     * @param dateSize  天数
     * @return          date数组
     */
    public static ArrayList<String> getDates(int dateSize) {
        ArrayList<String> pastDaysList = new ArrayList<>();
        int i=0;

        for (; i < dateSize; i++) {
            pastDaysList.add(getPastDate(i));
        }
        return pastDaysList;
    }

    //获取
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        log.info(result);
        return result;
    }
}
