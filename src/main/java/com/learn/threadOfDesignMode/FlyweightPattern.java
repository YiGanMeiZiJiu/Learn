package com.learn.threadOfDesignMode;

public class FlyweightPattern {
}

class Foo {
    int age = 0;
    String name = "abc";
}

final class Bar {
    Foo foo;

    void setAge(int a) {
        foo.age = a;
    }
}
