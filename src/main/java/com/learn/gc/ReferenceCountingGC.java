package com.learn.gc;

//import org.junit.Test;

/**
 * testGC() 执行之后，objA和objB会不会被GC呢？
 */
public class ReferenceCountingGC {

    public Object instance = null;

    private static final int _1MB = 1024 * 1024;

    private byte[] bigSize = new byte[2 * _1MB];

    //@Test
    public void testGC() {
        ReferenceCountingGC objA = new ReferenceCountingGC();
        ReferenceCountingGC objB = new ReferenceCountingGC();
        objA.instance = objB;
        objB.instance = objA;

        objA = null;
        objB = null;

        // 假设在这里发生GC，objA和objB是否能被回收?
        System.gc();
    }
}
