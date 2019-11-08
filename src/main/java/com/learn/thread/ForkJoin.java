package com.learn.thread;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoin {

    public static void main(String[] args) {
        String[] fc = {
                "hello world",
                "hello fork",
                "hello join",
                "fork join in world"
        };
        // 创建 ForkJoin 线程池
        ForkJoinPool pool = new ForkJoinPool(3);
        // 创建任务
        Fibonacci fib = new Fibonacci(30);
        // 启动分治任务
        Integer result = pool.invoke(fib);
        System.out.println(result);
    }


    static class Fibonacci extends RecursiveTask<Integer> {

        final int n;

        public Fibonacci(int n) {
            this.n = n;
        }

        @Override
        protected Integer compute() {
            if (n <= 1)
                return n;
            Fibonacci f1 = new Fibonacci(n - 1);
            // 创建子任务
            f1.fork();
            Fibonacci f2 = new Fibonacci(n - 2);
            // 等待子任务结果，并合并结果
            return f2.compute() + f1.join();
        }
    }

}
