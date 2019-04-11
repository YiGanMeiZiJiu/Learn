package com.learn.thread;

/**
 * 检测可见性对多线程的执行影响
 */
public class Visibility {

    private static long count = 0;
    private void add10K() {
        int idx = 0;
        while(idx++ < 10000) {
            count++;
        }
    }

    public static long calc() throws InterruptedException {
        final Visibility visibility = new Visibility();
        // 创建2个线程，执行add操作
        Thread thread1 = new Thread(() -> {
            visibility.add10K();
        });
        Thread thread2 = new Thread(() -> {
            visibility.add10K();
        });
        // 启动两个线程
        thread1.start();
        thread2.start();
        // 等待两个线程执行结束
        thread1.join();
        thread2.join();
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(calc());
    }

}
