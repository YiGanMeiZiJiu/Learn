package com.learn.thread;

import java.util.*;

/**
 * 封装一个安全的ArrayList
 * synchronized
 * @param <T>
 */
public class SafeArrayList<T> {

    // 封装ArrayList
    List<T> arrayList = new ArrayList<>();
    // 控制访问路径
    synchronized T get(int index) {
        return arrayList.get(index);
    }

    synchronized void add(int index, T t) {
        arrayList.add(index, t);
    }

    synchronized boolean addIfNotExist(T t) {
        if (!arrayList.contains(t)) {
            arrayList.add(t);
            return true;
        }
        return false;
    }

    // Java SDK 并发包中提供的线程安全的list
    List list = Collections.synchronizedList(new ArrayList<>());

    Set set = Collections.synchronizedSet(new HashSet<>());

    Map map = Collections.synchronizedMap(new HashMap<>());

}
