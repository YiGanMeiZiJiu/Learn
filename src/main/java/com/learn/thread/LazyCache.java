package com.learn.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LazyCache<K, V> {

    final Map<K, V> m = new HashMap<>();

    final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    // 读锁
    final Lock read = readWriteLock.readLock();

    // 写锁
    final Lock write = readWriteLock.writeLock();

    /**
     * 懒加载的缓存方法
     */

    V get(K key) {
        V v = null;
        // 读缓存
        read.lock();
        try {
            v = m.get(key);
        } finally {
            read.unlock();
        }
        // 缓存中存在,返回
        if (v != null) {
            return v;
        }
        // 缓存中不存在，查询数据库
        write.lock();
        try {
            // 再次验证
            // 其他线程可能已经查询过数据库
            v = m.get(key);
            if (v == null) {
                // 查询数据库
                //v = "查询代码"
                m.put(key, v);
            }
        } finally {
            write.unlock();
        }
        return v;
    }

}
