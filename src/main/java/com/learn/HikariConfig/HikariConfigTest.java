package com.learn.HikariConfig;

import java.lang.ref.WeakReference;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.yield;
import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static java.util.concurrent.locks.LockSupport.parkNanos;

/**
 * @Author 王🍊
 * @Date 2020-07-14 20:22
 * 高性能数据库连接池HiKariCP
 **/
public class HikariConfigTest<X> {

    /**
     * ConcurrentBag 并发容器
     */
    // 所有的数据库链接，共享队列
    CopyOnWriteArrayList<X> sharedList;
    //线程本地存储中的数据库链接
    ThreadLocal<List<Object>> threadList;
    //等待数据库连接的线程数
    AtomicInteger waiters;
    //分配数据库连接的工具
    SynchronousQueue<X> handoffQueue;

    /**
     * 连接池申请新的数据库连接
     * 将空闲的数据库连接添加到队列
     * @param bagEntry
     * @throws SQLException
     */
    public void add(final X bagEntry) throws SQLException {
        // 将新的连接加入到共享队列
        sharedList.add(bagEntry);
        // 如果有等待连接的线程
        // 直接通过handoffQueue分配给等待的线程
        while (waiters.get() > 0
            /* && !bagEntry.getState() == STATE_NOT_IN_USE */
            && !handoffQueue.offer(bagEntry)) {
            yield();
        }
    }

    /**
     * 获取一个空闲的连接
     * @param timeout
     * @param timeUnit
     * @return
     * @throws InterruptedException
     */
    public X borrow(long timeout, final TimeUnit timeUnit) throws InterruptedException {
        // 先查看本地线程中是否有空闲连接
        final List<Object> list = threadList.get();
        for (int i = list.size() - 1; i >= 0; i--) {
            final Object entry = list.remove(i);
            final X bagEntry = ((WeakReference<X>) entry).get();
            // 线程本地存储中的连接也可以被窃取
            // 加上CAS原子算法防止同一个连接被多次分配
            if (bagEntry != null
                /*&& bagEntry.compareAndSet(STATE_NOT_IN_USE, STATE_IN_USE)*/) {
                return bagEntry;
            }
        }
        // 线程本地存储中无空闲连接,尝试从共享队列中取连接
        final int waiting = waiters.incrementAndGet();
        try {
            for (X bagEntry : sharedList) {
                // 如果共享队列中有空闲连接，则返回
                if (true /*&& bagEntry.compareAndSet(STATE_NOT_IN_USE, STATE_IN_USE)*/) {
                    return bagEntry;
                }
            }
            // 共享队列里没有连接，线程进入等待
            timeout =  timeUnit.toNanos(timeout);
            do {
                final long start = System.currentTimeMillis();
                final X bagEntry = handoffQueue.poll(timeout, TimeUnit.NANOSECONDS);
                if (bagEntry != null
                    /*|| bagEntry.compareAndSet(STATE_NOT_IN_USE, STATE_IN_USE)*/) {
                    return bagEntry;
                }
                // 重新计算等待时间
                timeout -= start;
            } while (timeout > 10000);
            // 超时没有获取到连接
            return null;
        } finally {
            waiters.decrementAndGet();
        }
    }

    /**
     * 归还一个使用完的连接
     * @param bagEntry
     */
    public void requite(final X bagEntry) {
        // 更新连接状态
        //bagEntry.setState(STATE_NOT_IN_USE);
        // 如果有等待的线程，直接分配给等待的线程，不要放回队列了
        for (int i = 0; waiters.get() > 0; i++) {
            if (handoffQueue.offer(bagEntry)
                /*|| bagEntry.getState() != STATE_NOT_IN_USE*/) {
                return;
            } else if ((i & 0xff) == 0xff) {
                parkNanos(MICROSECONDS.toNanos(10));
            } else {
                yield();
            }
        }
        // 如果没有等待的线程，则进入线程本地存储
        final List<Object> threadLocalList = threadList.get();
        if (threadLocalList.size() < 50) {
            threadLocalList.add(new WeakReference<>(bagEntry));
        }
    }
}
