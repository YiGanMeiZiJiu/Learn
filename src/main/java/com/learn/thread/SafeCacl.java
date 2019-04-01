package com.learn.thread;

public class SafeCacl {

    long value = 0L;

    /**
     * JVM开启逃逸分析的话，以下代码的锁会被优化掉，相当于不存在，因为锁住一个可变对象是没有意义的
     */
    long get() {
        synchronized (new Object()) {
            return value;
        }
    }

    void addOne() {
        synchronized (new Object()) {
            value += 1;
        }
    }

    /**
     * 锁应该设计成这样，最佳实践
     */
    // 普通对象锁
    private final Object lock = new Object();
    // 静态对象锁
    private static final Object staticLock = new Object();

    long safeGet() {
        synchronized (lock) {
            return value;
        }
    }

    void safeAddOne() {
        synchronized (staticLock) {
            this.value += 1;
        }
    }
}
