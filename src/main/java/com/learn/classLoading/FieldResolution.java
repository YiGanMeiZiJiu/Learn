package com.learn.classLoading;

/**
 * Created with IDEA
 * author:WangCheng
 * Date:2019/3/21
 * Time:21:39
 */
public class FieldResolution {
  /**
   * 字段编译测试
   */
  interface InterFace0 {
    int A = 0;
  }

  interface Interface1 extends InterFace0 {
    int A = 1;
  }

  interface Interface2 {
    int A = 2;
  }

  static class Parent implements Interface1 {
    public static int A = 3;
  }

  static class Sub extends Parent implements Interface2 {
    /**
     * 如果注释这一句static int A = 4；
     * 程序将无法通过编译，因为字段解析时，父类和实现的接口中同时包含了A字段
     * 编译器将会拒绝编译这段代码;
     * 但是有这一句代码，字段解析时在类中找到相符合的字段，会立即返回，所以答案是4
     */
    public static int A = 4;
  }

  public static void main(String[] args) {
    System.out.println(Sub.A);
  }

}
