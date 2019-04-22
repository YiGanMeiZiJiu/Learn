package com.learn.thread;

import jdk.nashorn.internal.runtime.ECMAException;

import java.util.Vector;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CountDownLatchTest {

    /**
     * 存在尚未查账订单
     */
    boolean notBill = true;


    /**
     * 对帐系统中并发执行的对账方法
     * 1.首先查询订单库
     * 2.查询派送单库
     * 3.之后比较订单和派送单
     * 4.将差异写入差异库
     */


    /**
     * 我们创建了两个线程T1，T2，并行执行查询订单库和派送订单库
     * 在主线程中执行对账操作 check() 和写入 save() 两个操作
     * 需要注意的是，主线程需要等待线程T1和T2执行完毕才执行check和save这两个操作
     * 所以我们通过调用T1和T2的join方法来实现等待，当T1和T2线程退出时。调用T1，T2的主线程就会从阻塞状态中被唤醒，从而执行后面的check和save操作
     */
    public void checBillByThread() throws InterruptedException {
        while (notBill) {
            // 查询未对账订单
            Thread T1 = new Thread(() -> {
                P pos = getPOrders();
            });
            T1.start();

            // 查询派送中订单
            Thread T2 = new Thread(() -> {
                D dos = getDOrders();
            });
            T2.start();

            // 等待T1，T2结束
            T1.join();
            T2.join();

            // 执行对账操作
//            diff = check(pos, cos);
            // 差异写入差异库
//            save(diff);
        }
    }


    /**
     * 创建一个CountDOwnLatch，计数器的初始值为2
     * 之后在两个查询操作中分别对计数器的执行减1操作
     * 在主线程中,我们通过调用latch.await()来实现计数器对于非0时的等待
     */
    public void checkBillByCountDownLatch() throws InterruptedException {
        // 创建两个线程的线程池
        Executor executor = Executors.newFixedThreadPool(2);
        while(notBill) {
            // 计数器初始化为2
            CountDownLatch latch = new CountDownLatch(2);
            // 查询未对账订单
            executor.execute(() -> {
                P pos = getPOrders();
                latch.countDown();
            });
            // 查询派送中订单
            executor.execute(() -> {
                D dos = getDOrders();
                latch.countDown();
            });

            // 等待两个查询操作结束
            latch.await();

            // 执行对账操作
//            diff = check(pos, dos);
            // 差异写入差异库
//            save(diff);
        }
    }

    /**
     * 运用条件变量试一试满足题意
     * 失败
     */
    final Lock lock = new ReentrantLock();
    final Condition bothFinish = lock.newCondition();

    public void checkBillByCondition() {
        Executor executor = Executors.newFixedThreadPool(2);
        while(notBill) {
            int count = 2;

        }
    }


    /**
     * 运用 CyclicBarrier
     */

    // 订单队列
    Vector<P> pos;
    // 派送单队列
    Vector<D> dos;
    // 执行回调的线程池
    Executor executor = Executors.newFixedThreadPool(1);
    final CyclicBarrier barrier = new CyclicBarrier(2, () -> {
        executor.execute(() -> checkOne());
    });

    public void checkOne() {
        P p = pos.remove(0);
        D d = dos.remove(0);
        // 执行对账操作
//        diff = check(p, d);
        // 差异写入数据库
//        save(diff);
    }

    /**
     * 线程T1负责查询订单，当查处第一条时，调用barrier.await()来将计数器减1，同时等待计数器变成0；
     * 线程T2负责查询派送中订单，当查出第一条时，调用barrier.await()来将计数器减1，同时等待计数器变成0；
     * 当T1，T2都调用barrier.await()时，计数器会减到0
     * 此时就可以执行下一条语句了，同时会调用barrier的回调函数来执行对账操作
     */
    public void checkAll() {
        // 循环查询订单库
        Thread T1 = new Thread(() -> {
            while(notBill) {
                // 查询订单库
                pos.add(getPOrders());
                // 等待
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });
        T1.start();
        // 循环查询派送中订单
        Thread T2 = new Thread(() -> {
            while(notBill) {
                // 查询派送中订单
                dos.add(getDOrders());
                // 等待
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });
        T2.start();
    }

    /**
     * 从待查账队列中拿出一个待查账订单
     * @return
     */
    P getPOrders() { return new P(); }

    /**
     * 从派送中队列中拿出一个派送中订单
     * @return
     */
    D getDOrders() { return new D(); }
}


class P {
    /**
     * 订单类
     */
}

class D {
    /**
     * 派送订单类
     */
}
