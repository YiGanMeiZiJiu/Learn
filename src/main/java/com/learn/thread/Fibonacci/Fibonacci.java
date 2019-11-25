package com.learn.thread.Fibonacci;

import java.util.concurrent.RecursiveTask;

/**
 * 斐波那契数列计算
 */
public class Fibonacci extends RecursiveTask<Integer> {

    final int n;

    public Fibonacci(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if (n <= 1)
            return n;
        Fibonacci fq = new Fibonacci(n - 1);
        // 创建子任务
        fq.fork();
        Fibonacci fibonacci = new Fibonacci(n - 2);
        // 等待子任务结果，合并结果
        return fibonacci.compute() + fq.join();
    }
}
