package com.ivan.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ivan.blog.model.SysLog;
import org.apache.ibatis.annotations.Select;

public interface SysLogMapper extends BaseMapper<SysLog> {

    //重置操作日志数据
    @Select("truncate sys_log")
    int reset();
}