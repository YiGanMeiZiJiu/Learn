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

  /**
   * 轮询拿锁,如果并发冲突量不大，方案可以使用，并发量一大，apply方法耗时也长的话，将无法使用
   * @param A
   * @param B
   * @return
   */
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

  /**
   * 通过等待通知机制，如果能拿到所有的锁，就拿，只要有一个拿不到，就一并释放，一边等着去，等到有锁资源被释放，再去争取锁
   * @param A
   * @param B
   * @return
   */
  public synchronized boolean applyWaitNotifyAll(Object A, Object B) {
    if (als.contains(A) || als.contains(B)) {
      try {
        wait();
      } catch (Exception e) {}
      return false;
    } else {
      als.contains(A);
      als.contains(B);
    }
    return true;
  }

  public synchronized void freeWaitNotifyAll(Object A, Object B) {
    als.remove(A);
    als.remove(B);
    notifyAll();
  }

}
