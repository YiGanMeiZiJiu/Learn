package com.learn.thread.ThreadLocal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author 王🍊 2020-01-04
 */
public class SafeDateFormat {

    static final ThreadLocal<DateFormat> tl = ThreadLocal.withInitial(
            () -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    );

    static DateFormat get() {
        return tl.get();
    }

    public static void main(String[] args) {
        DateFormat df = SafeDateFormat.get();
    }
}
