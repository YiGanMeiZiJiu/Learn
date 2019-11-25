package com.learn.thread.MapReduce;

import java.util.Map;
import java.util.concurrent.ForkJoinPool;

public class MapReduce {

    public static void main(String[] args) {
        String[] fc = {
                "hello world",
                "hello me",
                "hello fork",
                "hello join",
                "fork join in world"
        };
        // 创建ForkJoin线程池
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        // 创建任务
        MR mr = new MR(fc, 0, fc.length);
        // 启动任务
        Map<String, Long> result = forkJoinPool.invoke(mr);
        // 输出结果
        result.forEach((k, v) -> {
            System.out.println(k+":"+v);
        });
    }

}
