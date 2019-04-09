package com.learn.thread;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 队列执行入队和出队操作！使用条件变量!
 * @param <T>
 */
public class BlockQueue<T> {

    private final static Integer length = 20;

    private ArrayList<Object> queue = new ArrayList<>();

    final Lock lock = new ReentrantLock();

    // 队列不满； 条件变量
    final Condition notFull = lock.newCondition();

    // 队列不空； 条件变量
    final Condition notEmpty = lock.newCondition();

    // 入队操作
    void enq(T x) {
        lock.lock();
        // 队列不满
        try {
            while (queue.size() >= length) {
                /**
                 * 此时队列已满，所以队列不满的条件变量不满足，进入等待
                 */
                notFull.await();
            }
            // 入队
            queue.add(new Object());
            // 入队之后通知队列不空的条件变量的等待队列，可以出队了
            notEmpty.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    // 出队操作
    void deq(T x) {
        lock.lock();
        // 队列不空
        try {
            while (queue.size() == 0) {
                /**
                 * 此时队列是空的，不满足队列不空条件变量，不执行出队，线程进入队列不空 等待队列中
                 */
                notEmpty.await();
            }
            // 出队操作
            queue.remove(new Object());
            // 通知队列不满的等待队列，可以入队了
            notFull.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
