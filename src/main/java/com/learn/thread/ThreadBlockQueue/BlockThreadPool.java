package com.learn.thread.ThreadBlockQueue;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 王🍊 2020-01-06
 */
public class BlockThreadPool {

    private ExecutorService threadPool = null;

    public BlockThreadPool(int poolSize) {
        threadPool = new ThreadPoolExecutor(
                poolSize,
                poolSize,
                0,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(5), // 有界队列
                new CustThreadFactory(),
                new CustRejectedExecutionHandler() // 拒绝策略
        );
    }

    private class CustThreadFactory implements ThreadFactory {
        private AtomicInteger count = new AtomicInteger(0);
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            String threadName = BlockThreadPool.class.getSimpleName() + count.addAndGet(1);
            thread.setName(threadName);
            return thread;
        }
    }

    private class CustRejectedExecutionHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                executor.getQueue().put(r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void execute(Runnable runnable) {
        this.threadPool.execute(runnable);
    }

    public static void main(String[] args) {
        BlockThreadPool blockThreadPool = new BlockThreadPool(5);
        for (int i = 0; i < 100; i++) {
            blockThreadPool.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getId());
            });
            System.out.println("放入第"+i+"个任务成功");
        }
        blockThreadPool.threadPool.shutdown();
    }

}
