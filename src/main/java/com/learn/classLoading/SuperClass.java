package com.learn.classLoading;

/**
 * 检测父类加载时机
 */
public class SuperClass {

    // 静态代码块检测父类加载时机
    static {
        System.out.println("SuperClass init!");
    }

    public static int vlaue = 1234;

    public static void getValue() {
        System.out.println("value is 1234");
    }

}
