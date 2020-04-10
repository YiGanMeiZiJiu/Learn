package com.learn.thread.Future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Author 王🍊
 * @Date 2020-03-27 16:05
 **/
public class FutureTaskTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 思路，在FuturTask里执行更新逻辑，返回码返回String，success/fail
        FutureTask<String> futureTask = new FutureTask<String>( () -> {
            int i = 0;
            while (true) {
                i++;
            }
        });
        // 创建线程
        Thread thread = new Thread(futureTask);
        thread.start();
        // 获取计算结果
        System.out.println("尝试获取计算结果,如果获取不到代表还没处理完，阻塞");
        String result = futureTask.get();
        System.out.println("获取到结果："+result);
        // 获取到结果之后进行redis设置，可以将允许的准入数值减1，放一个新的更新任务进来
        // 同时设置一个缓存，表明该任务已经更新完毕了，方便前端请求isOk接口返回信息
    }

}
