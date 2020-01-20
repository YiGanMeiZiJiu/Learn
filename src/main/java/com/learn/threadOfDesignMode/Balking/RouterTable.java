package com.learn.threadOfDesignMode.Balking;

import java.util.concurrent.*;

/**
 * @author 王🍊 2020-01-17
 * 路由表信息
 */
public class RouterTable {

    // key:接口名
    // value:路由集合
    ConcurrentHashMap<String, CopyOnWriteArraySet> rt = new ConcurrentHashMap<>();

    // 路由表是否发生变化
    volatile boolean changed;

    // 将路由表写入本地文件的线程池
    ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();

    // 启动定时任务
    // 将变更后的路由表写进本地文件
    public void startLocalSaver() {
        ses.scheduleWithFixedDelay(() -> {
            autoSave();
        }, 1, 1, TimeUnit.MINUTES);
    }

    // 保存路由表至本地目录
    void autoSave() {
        if (!changed) {
            return;
        }
        changed = false;
        this.saveLocal();
    }

    void saveLocal() {
        // 保存本地路由至本地
        System.out.println("保存本地路由至本地");
    }
}
