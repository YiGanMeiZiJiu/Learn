package com.learn.thread.Future;

import javafx.concurrent.Task;
////import org.junit.Test;

import java.util.concurrent.*;

/**
 * 烧水泡茶最优解
 */
public class Future {

//    //@Test
    public void makeTea() throws ExecutionException, InterruptedException {
        /**
         * 烧水泡茶
         * 1. 线程T1执行洗茶壶，洗茶杯，拿茶叶
         * 2. 线程T2执行洗水壶，烧开水，泡茶
         */
        FutureTask<String> ft2 = new FutureTask<>(new T2Task());
        FutureTask<String> ft1 = new FutureTask<>(new T1Task(ft2));
        // 线程 T1 执行任务ft1
        Thread T1 = new Thread(ft1);
        T1.start();
        // 线程 T2 执行任务ft2
        Thread T2 = new Thread(ft2);
        T2.start();
        // 等待线程 T1 执行结果
        System.out.println(ft1.get());

        FutureTask<String> s1 = new FutureTask<>(new S1Task());
        FutureTask<String> s2 = new FutureTask<>(new S2Task());
        FutureTask<String> s3 = new FutureTask<>(new S3Task());
        Thread S1 = new Thread(s1);
        S1.start();
        Thread S2 = new Thread(s2);
        S2.start();
        Thread S3 = new Thread(s3);
        S3.start();
    }

    class T1Task implements Callable<String> {

        FutureTask<String> ft2;

        // T1任务需要T2任务的 FutureTask；
        T1Task (FutureTask<String> ft2) {
            this.ft2 = ft2;
        }

        @Override
        public String call() throws Exception {
            System.out.println("T1:洗水壶。。。");
            TimeUnit.SECONDS.sleep(1);

            System.out.println("T1:烧开水。。。");
            TimeUnit.SECONDS.sleep(15);

            // 获取T2线程的茶叶
            String tf = ft2.get();
            System.out.println("T1:拿到茶叶："+tf);

            System.out.println("T1:泡茶。。。");
            return "上茶："+ tf;
        }
    }

    class T2Task implements Callable<String> {

        @Override
        public String call() throws Exception {
            System.out.println("T2:洗茶壶...");
            TimeUnit.SECONDS.sleep(1);

            System.out.println("T2:洗茶杯...");
            TimeUnit.SECONDS.sleep(2);

            System.out.println("T2:拿茶叶...");
            TimeUnit.SECONDS.sleep(1);
            return "龙井";
        }
    }

    class S1Task implements Callable<String> {

        @Override
        public String call() throws Exception {
            System.out.println("S1:向电商 S1 询价");
            TimeUnit.SECONDS.sleep(5);
            return "电商1：25";
        }
    }

    class S2Task implements Callable<String> {

        @Override
        public String call() throws Exception {
            System.out.println("S2:向电商 S2 询价");
            TimeUnit.SECONDS.sleep(5);
            return "电商2：20";
        }
    }

    class S3Task implements Callable<String> {

        @Override
        public String call() throws Exception {
            System.out.println("S3:向电商 S3 询价");
            TimeUnit.SECONDS.sleep(5);
            return "电商3：22";
        }
    }
}