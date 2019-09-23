package com.learn.thread;

import java.util.concurrent.*;

public class CompletionServiceV2 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService executor = Executors.newFixedThreadPool(3);
        // 创建CompletionService
        CompletionService<Integer> cs = new ExecutorCompletionService<Integer>(executor);
        // 异步向 S1 询价
        cs.submit(() -> getPriceByS1());
        // 向 S2 询价
        cs.submit(() -> getPriceByS2());
        // 向 S3 询价
        cs.submit(() -> getPriceByS3());

        // 将查询结果异步保存至数据库
        for (int i=0; i<3; i++) {
            Integer result = cs.take().get();
            executor.execute(() -> save(result));
        }
    }

    private static int getPriceByS1() throws InterruptedException {
        TimeUnit.SECONDS.sleep(10);
        return 20;
    }

    private static int getPriceByS2() throws InterruptedException {
        TimeUnit.SECONDS.sleep(15);
        return 22;
    }

    private static int getPriceByS3() throws InterruptedException {
        TimeUnit.SECONDS.sleep(22);
        return 19;
    }

    private static void save(Integer esult) {
        System.out.println(esult);
    }
}

