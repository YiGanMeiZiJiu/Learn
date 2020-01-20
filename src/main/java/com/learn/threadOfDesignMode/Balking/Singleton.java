package com.learn.threadOfDesignMode.Balking;

/**
 * @author 王🍊 2020-01-17
 */
public class Singleton {

    private static Singleton singleton;

    // 构造方法私有化
    private Singleton() {}

    public synchronized static Singleton getInstance() {
        if (singleton == null) {
            singleton = new Singleton();
        }
        return singleton;
    }
}
