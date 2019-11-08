package com.learn.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorTest {

    public static void main(String[] args){
        for (int i = 0; i < 10; i++) {
            test();
        }
    }

    static class MyTask implements Runnable {
        String type;

        MyTask (String type) {
            this.type = type;
        }

        @Override
        public void run() {
            System.out.println(new Date() +","+type+","+ Thread.currentThread().getName());
            for (int i = 0; i < Integer.valueOf(type)*10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public static void test () {
        int activitySize = 50;
        List<String> activityBaseList = new ArrayList<String>();
        for (int i=0; i<activitySize; i++){
            activityBaseList.add(i+"");
        }
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(activitySize);
        // 返回Java虚拟机的可用处理器数量
        int processors = Runtime.getRuntime().availableProcessors();
        System.out.println("1"+processors);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(processors, processors, 0, TimeUnit.MICROSECONDS, queue);
        try {
            activityBaseList.stream().forEach(active -> {
                executor.execute(new MyTask(active));
            });
        } finally {
            // 关闭线程池
            executor.shutdown();
        }
    }

//    public static void test2 () {
//        int activitySize = 50;
//        List<String> activityBaseList = new ArrayList<String>();
//        for (int i=0; i<activitySize; i++){
//            activityBaseList.add(i+"");
//        }
//        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(activitySize);
//        // 返回Java虚拟机的可用处理器数量
//        int processors = Runtime.getRuntime().availableProcessors();
//        System.out.println("2"+processors);
//        ThreadPoolExecutor executor = new ThreadPoolExecutor(processors, processors, 0, TimeUnit.MICROSECONDS, queue);
//        try {
//            activityBaseList.stream().forEach(active -> {
//                executor.execute(new MyTask(active));
//            });
//        } finally {
//            // 关闭线程池
//            executor.shutdown();
//        }
//    }

}
