package com.learn.classLoading;

/**
 * 类加载测试主类
 */
public class NotInitialization {

    public static void main(String[] args) {
        /**
         * 通过子类引用父类的静态字段
         * 不会造成子类的初始化
         */
//        System.out.println(SubClass.vlaue);
        /**
         * 通过子类引用父类的静态方法
         * 不会造成子类的初始化
         * 只会初始化父类
         */
//        SubClass.getValue();

        /**
         * 通过数组定义来引用类，也不会造成初始化
         */
//        SuperClass[] sca = new SuperClass[10];

        /**
         * 定义为final的静态字段，按道理也是在编译期会存入常亮池中
         * 也不会造成类的初始化
         */
        System.out.println(ConstClass.HELLO_WORLD);
    }

}
