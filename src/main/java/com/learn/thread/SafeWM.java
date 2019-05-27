package com.learn.thread;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 仅实现了设置库存上限 setUpper() 方法，你觉得 setUpper()方法的实现是否正确?
 */
public class SafeWM {

    class WMRange {
        final int upper;
        final int lower;
        WMRange(int upper, int lower) {
            // 省略构造函数实现
            this.upper = upper;
            this.lower = lower;
        }
    }

    final AtomicReference<WMRange> rf = new AtomicReference<WMRange>(new WMRange(0, 0));

    // 设置库存上限
    void setUpper(int v) {
        WMRange nr;
        WMRange or;
        do {
            or = rf.get();
            // 检查参数合法性
            if (v < or.lower) {
                throw new IllegalArgumentException();
            }
            nr = new WMRange(v, or.lower);
        } while (!rf.compareAndSet(or, nr));
    }

}
