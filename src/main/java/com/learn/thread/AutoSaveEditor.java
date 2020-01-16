package com.learn.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author 王🍊 2020-01-16
 * 定时自动保存
 */
public class AutoSaveEditor {

    // 文件是否被修改过
    boolean changed = false;

    // 定时任务线程池
    ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    /**
     * 定时执行自动保存
     */
    void startAutoSave() {
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            autoSave();
        }, 5, 5, TimeUnit.SECONDS);
    }

    /**
     * 自动存盘操作
     */
    void autoSave() {
        // 判断是否修改过
        synchronized (this) {
            if (!changed) {
                return;
            }
            changed = false;
        }
        // 执行存盘
        this.executeSave();
    }

    /**
     * 执行存盘
     */
    void executeSave() {
        // 省略实现；
        System.out.println("存盘成功");
        return;
    }

    void edit() {
        // 修改文件内容
        System.out.println("修改文件内容");
        // 修改文件状态
        this.change();
    }

    void change() {
        synchronized (this) {
            changed = true;
        }
    }
}
