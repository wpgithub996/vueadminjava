package com.wang.vueadmin.manager;


import com.wang.vueadmin.utils.Threads;
import com.wang.vueadmin.utils.timetask.AsyncFactory;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public  class AsyncManager {
    //操作延时十秒
    private final int DELAY_TIME = 10;
    //
    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    /**
     * 单例模式
     */
    private AsyncManager(){}
    private static AsyncManager me = new AsyncManager();
    public static AsyncManager me(){return me;}
    /**
     * 执行任务
     *
     */

    public void execute(TimerTask timerTask){
        service.schedule(timerTask,DELAY_TIME, TimeUnit.MILLISECONDS);
    }
    /**
     * 停止任务线程池
     */
    public void shutdown(){
        Threads.shutdownAndAwaitTermination(service);
    }

    public void recordlog(String username,String status,String logmMsg){
        AsyncFactory asyncFactory = new AsyncFactory();
        AsyncManager.me().execute(asyncFactory.recordLoginInfo(username,status,logmMsg));
    }
}
