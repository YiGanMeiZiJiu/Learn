package com.learn.thread.TokenBucket;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 令牌限流算法
 */
public class TokenBucket {
    // 当前令牌桶中的令牌数量
    long storePermits = 0;
    // 令牌桶的容量
    long maxPermits = 5;
    // 下一令牌产生时间
    long next = System.nanoTime();
    // 发放令牌时间间隔（纳秒）
    long interval = 1000_000_000;

    /**
     * 只考虑一个一秒一个令牌且最大令牌1的情况
     * @param now
     * @return
     */
    // 预占令牌，返回能够获取令牌的时间
    synchronized long reserve(long now) {
        if (now > next) {
            // 将下一令牌产生时间重置为当前时间
            next = now;
        }
        // 能够获取令牌的时间
        long at = next;
        next += interval;
        // 返回线程需要等待的时间
        return Math.max(at, 0L);
    }

    // 申请令牌
    void acquire() throws Exception {
        // 申请令牌的时间
        long now = System.nanoTime();
        // 预占令牌
        long at = reserve(now);
        long waitTime = Math.max(at - now , 0);
        // 按照条件进入等待
        if(waitTime > 0) {
            throw new Exception("当前已达到最大开启数");
        }
    }

    /**
     * 令牌桶最大令牌大于1的情况
     * @param now
     * @return
     */
    // 请求时间在下一令牌产生时间之后，则
    // 1.重新计算令牌桶中令牌的数量
    // 2.将下一个令牌产生的时间重置为当前时间
    void resync(long now) {
        if(now > next) {
            // 新产生的令牌数
            long newPermits = (now - next) / interval;
            // 新令牌增加到令牌桶
            storePermits = Math.min(maxPermits, storePermits + newPermits);
            // 将下一个令牌的产生时间重置为当前时间
            next = now;
        }
    }

    // 预占令牌，获取能获得令牌的时间
    synchronized long reserveV2(long now) {
        resync(now);
        // 能够获取令牌的时间
        long at = next;
        // 令牌桶中能提供的令牌
        long fb = Math.min(1, storePermits);
        // 令牌净需求，首先减掉令牌桶中的令牌
        long nr = 1 - fb;
        // 重新计算下一令牌产生时间
        next += (nr * interval);
        this.storePermits -= fb;
        return at;
    }

    void acquireV2() throws InterruptedException {
        long now = System.nanoTime();
        // 预占令牌
        long at = reserveV2(now);
        long waitTime = Math.max(at - now, 0);
        if (waitTime > 0) {
            // 按照条件等待
            TimeUnit.NANOSECONDS.sleep(waitTime);
        }
    }

    public static Date addDays(Date date, Integer days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DATE) + days);
        return calendar.getTime();
    }

    public static void main(String[] args) {
        Date a = new Date();
        Date b = addDays(a, -30);
        System.out.println(b);
    }

}
