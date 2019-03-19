package com.learn.classLoading;

/**
 * 检测子类加载时机
 */
public class SubClass extends SuperClass{

    // 静态代码块检测子类是否加载
    static {
        System.out.println("SubClass init!");
    }

}
