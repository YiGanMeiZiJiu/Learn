package com.learn.Diana;

/**
 * Created with IntelliJ IDEA.
 * User: 小王
 * Date: 3/24/22
 * Time: 2:35 PM
 * Description: 令牌桶算法
 */
public class TokenBucket {

    /**
     * 上次生成令牌的时间
     */
    private static Long tokenCreateStamp = 0L;

    /**
     * 桶中总令牌数量
     */
    private static Integer tokenCount = 0;

    /**
     * 桶中令牌上限值10个
     */
    private static Integer maxTokenCount = 10;

    /**
     * 令牌生成时间间隔100ms
     */
    private static Long tokenCreateInterval = 100L;

    public static boolean getToken() {
        if (tokenCount >= 1) {
            tokenCount--;
            return true;
        }
        // 令牌桶中没有令牌了，重算桶中令牌数
        Long now = System.currentTimeMillis();
        tokenCount = Integer.min(maxTokenCount, (int) (tokenCount + (now - tokenCreateStamp) / tokenCreateInterval));
        if (tokenCount >= 1) {
            tokenCount--;
            tokenCreateStamp = now;
            return true;
        }
        return false;
    }
}
