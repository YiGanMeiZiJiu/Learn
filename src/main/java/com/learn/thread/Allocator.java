package com.learn.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IDEA
 * author:WangCheng
 * Date:2019/3/11
 * Time:21:41
 */
public class Allocator {

  public static volatile Allocator allocator;

  private Allocator() {}

  static Allocator getInstance() {
    if (allocator == null) {
      synchronized (Allocator.class) {
        allocator = new Allocator();
      }
    }
    return allocator;
  }

  private List<Object> als = new ArrayList<>();

  public synchronized boolean apply(Object A, Object B) {
    if (als.contains(A) || als.contains(B)) {
      return false;
    } else {
      als.add(A);
      als.add(B);
    }
    return true;
  }

  public synchronized void free(Object A, Object B) {
    als.remove(A);
    als.remove(B);
  }

}
