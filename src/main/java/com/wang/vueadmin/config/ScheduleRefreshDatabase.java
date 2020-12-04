package com.wang.vueadmin.config;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 定时查库并更新任务
 * @author 王鹏
 * @date 2020/11/22 16:21
 */
@Configuration
@EnableScheduling
@Component
public class ScheduleRefreshDatabase {
    @Resource(name = "jobDetail")
    private JobDetail jobDetail;
    @Resource(name = "jobTrigger")
    private CronTrigger crontrigger;
    @Resource(name = "scheduler")
    private Scheduler scheduler;

    public void updateScheduletJob() throws  SchedulerException {
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(crontrigger.getKey());
        //获得当前表达式
        String cronExpression = trigger.getCronExpression();
        

    }
}
