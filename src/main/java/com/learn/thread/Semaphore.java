package com.learn.thread;

import java.util.Queue;

/**
 * 代码化的信号量模型
 */
public class Semaphore {

    // 计算器
    int count;

    // 等待队列
    Queue queue;

    // 初始化方法
    Semaphore(int c) {
        this.count =  c;
    }

    void down() {
        this.count--;
        if (count < 0) {
            // 当前线程进入等待队列
            // 阻塞当前线程
        }
    }

    void up() {
        this.count++;
        if (this.count <= 0) {
            // 将线程A从等待队列中移除
            // 唤醒线程A
        }
    }

}
