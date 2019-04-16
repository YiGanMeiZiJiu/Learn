package com.learn.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 利用读写锁实现一个简单的缓存
 * @param <K>
 * @param <V>
 */
public class Cache<K, V> {

    final Map<K, V> m = new HashMap<>();

    final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    // 读锁
    final Lock read = readWriteLock.readLock();

    // 写锁
    final Lock write = readWriteLock.writeLock();

    // 读缓存
    V get(K key) {
        read.lock();
        try {
            return m.get(key);
        } finally {
            read.unlock();
        }
    }

    // 写缓存
    V put(K key, V v) {
        write.lock();
        try {
            return m.put(key, v);
        } finally {
            write.unlock();
        }
    }

}
