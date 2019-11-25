package com.learn.thread.Fibonacci;

public class ForkJoinPool {

    public static void main(String[] args) {
        // 创建分治任务线程池
        java.util.concurrent.ForkJoinPool fjp = new java.util.concurrent.ForkJoinPool(4);
        // 创建分治任务
        Fibonacci fib = new Fibonacci(30);
        // 启动分治任务
        Integer result = fjp.invoke(fib);
        // 输出结果
        System.out.println(result);
    }

}
