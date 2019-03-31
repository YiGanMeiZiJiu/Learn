package com.learn.classLoading;

/**
 * Created with IDEA
 * author:WangCheng
 * Date:2019/3/31
 * Time:21:17
 */
public class Init {

  /**
   * 初始化非法向前引用变量
   */
  static {
    i = 0;  //给变量赋值可以正常编译通过
//    System.out.println(i);  // 这句编译器会报错，“非法向前引用”
  }

  static int i = 1;

}
