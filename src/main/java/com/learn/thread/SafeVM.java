package com.learn.thread;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 识别共享变量间的约束条件
 * 库存上限一定要大于库存下限
 */
public class SafeVM {

    //库存上限
    private final AtomicLong upper = new AtomicLong(0);

    //库存下限
    private final AtomicLong lower = new AtomicLong(0);

    //设置库存上限
    void setUpper(Long v) {
        upper.set(v);
    }

    //设置库存下限
    void setLower(Long v) {
        lower.set(v);
    }

}
