package com.learn.thread;

import javax.xml.ws.Response;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Dubbo 异步转同步 源码
 */
public class DefaultFuture {

//    // 创建锁与条件变量
//    private final Lock lock = new ReentrantLock();
//    private final Condition condition = lock.newCondition();
//
//    // 调用方法通过该方法等待结果
//    Object get(int timeout) throws TimeoutException {
//        long start = System.nanoTime();
//        lock.lock();
//        try {
//            while (!isDone()) {
//                condition.await(timeout);
//                long cur = System.nanoTime();
//                if (isDone() || cur - start > timeout) {
//                    break;
//                }
//            }
//        } finally {
//            lock.unlock();
//        }
//        if (!isDone()) {
//            throw new TimeoutException();
//        }
//        return returnFromResponse();
//    }
//
//    // 检测 RPC 结果是否返回
//    boolean isDone() {
//        return response != null;
//    }
//
//    // RPC 结果返回时调用方法
//    private void doReceived(Response res) {
//        lock.lock();
//        try {
//            response = res;
//            if (condition != null) {
//                condition.signal();
//            }
//        } finally {
//            lock.unlock();
//        }
//    }

}
