package com.learn.thread.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author 王🍊 2020-01-06
 */
public class TestThreadPool {

    private volatile static int i = 1;

    public static void main(String[] args) {
        // 阻塞队列
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(4000);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 4, 0, TimeUnit.MICROSECONDS, queue);
        long start = System.currentTimeMillis();
        try {
            for (int j = 0; j < 4; j++) {
                executor.execute(() -> {
                    while (i < 100000) {
                        i++;
                        System.out.println("当前线程:"+Thread.currentThread().getName()+"执行---------"+i);
                    }
                });
            }
            System.out.println(i);
            long programEnd = System.currentTimeMillis();
            System.out.println("程序执行完毕------------------------------------------------------------------------："+(programEnd-start));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            long end = System.currentTimeMillis();
            executor.shutdown();
            System.out.println("关闭线程池------------------------------------------------------------------------："+(end-start));
        }
    }

}
