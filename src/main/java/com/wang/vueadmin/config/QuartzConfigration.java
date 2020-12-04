package com.wang.vueadmin.config;

import org.apache.poi.ss.formula.functions.T;
import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * Quartz配置类
 * @author 王鹏
 * @date 2020/11/22 15:52
 */
@Configuration
public class QuartzConfigration {
    //配置定时任务
    @Bean(name = "jobDetail")
    public MethodInvokingJobDetailFactoryBean detailFactoryBean(T task, String taskMethodName,String taskName, String taskGroup){
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        //任务是否并发执行  当前任务必须等上一个任务执行完
        jobDetail.setConcurrent(false);
        jobDetail.setName(taskName);
        jobDetail.setGroup(taskGroup);
        /*
         *   需要执行的实体类
         */
        jobDetail.setTargetObject(task);
        /*
         * 需要执行实体类的方法
         */
        jobDetail.setTargetMethod(taskMethodName);
        return jobDetail;
    }

    @Bean(name = "jobTrigger")
    public CronTriggerFactoryBean cronTriggerFactoryBean(MethodInvokingJobDetailFactoryBean jobDetail
    , String cron, String triggerName){
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        //触发器调用的任务
        trigger.setJobDetail(jobDetail.getObject());
        //设置触发器执行时间表达式
        trigger.setCronExpression(cron);
        trigger.setName(triggerName);
        return  trigger;
    }

    @Bean(name = "scheduler")
    public SchedulerFactoryBean schedulerFactoryBean(Trigger trigger){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        //用于 quartz集群  启动时更新已经存在的job
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        //设置应用延时一秒启动
        schedulerFactoryBean.setStartupDelay(1);
        //注册触发器
        schedulerFactoryBean.setTriggers(trigger);
        return  schedulerFactoryBean;
    }
}
