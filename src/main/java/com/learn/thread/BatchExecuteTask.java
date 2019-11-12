package com.learn.thread;

import com.sun.xml.internal.ws.util.CompletedFuture;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.Future;

public class BatchExecuteTask {

    /**
     * 保存询价
     * @param price
     */
    private void save(Integer price) {
        System.out.println(price);
    }

    /*
    向电商S1询价
     */
    private Integer getPriceByS1() throws InterruptedException {
        Thread.sleep(10000);
        return 10;
    }

    /*
    向电商S2询价
     */
    private Integer getPriceByS2() throws InterruptedException {
        Thread.sleep(15000);
        return 15;
    }

    /*
    向电商S3询价
     */
    private Integer getPriceByS3() throws InterruptedException {
        Thread.sleep(20000);
        return 20;
    }

    /*
    异步询价，同步保存
     */
    @Test
    public void test1() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        // 异步开始询价
        Future<Integer> f1 = executorService.submit(() -> getPriceByS1());
        Future<Integer> f2 = executorService.submit(() -> getPriceByS2());
        Future<Integer> f3 = executorService.submit(() -> getPriceByS3());

        // 获取询价结果并保存
        Integer p1 = f1.get();
        executorService.execute(() -> save(p1));
        Integer p2 = f2.get();
        executorService.execute(() -> save(p2));
        Integer p3 = f3.get();
        executorService.execute(() -> save(p3));

    }

    /*
    异步询价，创建阻塞队列同步保存
     */
    @Test
    public void test2() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        BlockingQueue<Integer> queue = new LinkedBlockingDeque<>();
        // 异步开始询价
        Future<Integer> f1 = executorService.submit(() -> getPriceByS1());
        Future<Integer> f2 = executorService.submit(() -> getPriceByS2());
        Future<Integer> f3 = executorService.submit(() -> getPriceByS3());

        // 获取询价结果并保存
        executorService.execute(() -> {
            try {
                queue.put(f1.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        executorService.execute(() -> {
            try {
                queue.put(f2.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        executorService.execute(() -> {
            try {
                queue.put(f3.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });

        for (int i = 1; i < 4; i++) {
            Integer r = queue.take();
            executorService.execute(() -> save(r));
        }

    }

    /*
    异步询价，异步保存
     */
    @Test
    public void test3() throws ExecutionException, InterruptedException {
        // 创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        BlockingQueue<Future<Integer>> queue = new LinkedBlockingDeque<>(3);
        CompletionService<Integer> cs = new ExecutorCompletionService<Integer>(executorService,queue);
        // 异步询价
        cs.submit(() -> getPriceByS1());
        cs.submit(() -> getPriceByS2());
        cs.submit(() -> getPriceByS3());

        // 将询价结果保存至数据库
        for (int i = 0; i < 3; i++) {
            Integer r = cs.take().get();
            executorService.execute(() -> save(r));
        }
    }

    /*
    利用CompletionService快速实现一个简单的Forking Cluster
     */
    @Test
    public void test4() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CompletionService<Integer> completionService = new ExecutorCompletionService<>(executorService);
        List<Future<Integer>> futures = new ArrayList<>(3);
        // 利用future提交异步任务，并且保存结果
        futures.add(completionService.submit(() -> getPriceByS1()));
        futures.add(completionService.submit(() -> getPriceByS2()));
        futures.add(completionService.submit(() -> getPriceByS3()));

        // 获取最快返回
        Integer r = 0;
        try {
            // 只要获取到一个返回，立即结束
            for (int i = 0; i < 3; i++) {
                r = completionService.take().get();
                if (r != null) {
                    break;
                }
            }
        } finally {
            for (Future f : futures) {
                f.cancel(true);
            }
        }
        System.out.println(r);
//        return r;
    }
}
