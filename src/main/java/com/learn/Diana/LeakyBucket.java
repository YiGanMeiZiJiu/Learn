package com.learn.Diana;

/**
 * Created with IntelliJ IDEA.
 * User: 小王
 * Date: 3/24/22
 * Time: 3:02 PM
 * Description:
 */
public class LeakyBucket {

    /**
     * 间隔时间
     */
    private static Long interval = 100L;

    /**
     * 阻塞线程数
     */
    private static Integer blockThreadCount = 0;

    /**
     * 最近请求处理时间戳
     */
    private static Long lastTimeStamp = 0L;



}
