package com.learn.designMode.proxy;

/**
 * 代理类
 */
public class Proxy implements Subject{

    private Subject subject;

    public Proxy(Subject subject) {
        this.subject = subject;
    }

    @Override
    public void request() {
        System.out.println("代理begin");
        subject.request();
        System.out.println("代理end");
    }
}
