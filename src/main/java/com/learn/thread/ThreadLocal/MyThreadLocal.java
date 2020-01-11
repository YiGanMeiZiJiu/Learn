package com.learn.thread.ThreadLocal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 王🍊 2020-01-04
 */
public class MyThreadLocal<T> {

    Map<Thread, T> locals = new ConcurrentHashMap<>();

    // 获取线程变量
    T get() {
        return locals.get(Thread.currentThread());
    }

    // 设置线程变量
    void set(T t) {
        locals.put(Thread.currentThread(), t);
    }

}
