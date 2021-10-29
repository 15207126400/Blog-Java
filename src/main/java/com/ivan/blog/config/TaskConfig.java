package com.ivan.blog.config;

import com.ivan.blog.entity.SysTimed;
import com.ivan.blog.mapper.SysLogMapper;
import com.ivan.blog.mapper.SysTimedMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.time.LocalDateTime;

/**
 * @Author: Ivan
 * @Description: 定时任务配置类
 * @Date: 2019/10/31 23:04
 */
@Configuration
@EnableScheduling//开启定时任务
@AllArgsConstructor
@Slf4j
public class TaskConfig implements SchedulingConfigurer {

    private final SysTimedMapper sysTimedMapper;
    private final SysLogMapper sysLogMapper;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        //TODO 操作日志定时清理任务, 此业务需要调整
        logTask(scheduledTaskRegistrar);
    }

    /**
     * 操作日志定时清理任务---每天凌晨3点执行数据重置操作
     *
     * @param scheduledTaskRegistrar
     */
    void logTask(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        //任务执行线程
        Runnable runnable = () -> {
            //清除
            int result = sysLogMapper.reset();
            if (result > 0) {
                log.info("执行定时任务--->> " + LocalDateTime.now().toLocalTime());
            }
        };

        //获取定时配置
        SysTimed timed = sysTimedMapper.selectById(1);
        if (timed != null) {
            //任务触发器
            Trigger trigger = triggerContext -> {
                //获取定时触发器，这里可以每次从数据库获取最新记录，更新触发器，实现定时间隔的动态调整
                String cron = timed.getCron();
                return new CronTrigger(cron).nextExecutionTime(triggerContext);
            };
            //注册任务
            scheduledTaskRegistrar.addTriggerTask(runnable, trigger);
        }
    }
}
