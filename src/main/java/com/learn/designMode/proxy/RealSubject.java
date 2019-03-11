package com.learn.designMode.proxy;

public class RealSubject implements Subject {
    @Override
    public void request() {
        System.out.println("RealSubject!!!");
    }
}
