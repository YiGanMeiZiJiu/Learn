package com.learn.thread;

public class Account {

    //余额
    private int balance;

    //转账 （对象级别的锁，会有并发问题）
    synchronized void transfer(Account target, int amt) {
        if (this.balance > amt) {
            this.balance -= amt;
            target.balance += amt;
        }
    }

    //安全的转账方法 （Account.class作为唯一的共享锁，不存在并发问题，只是转账操作就成了串行的，影响效率）
    void safeTransfer(Account target, int amt) {
        synchronized (Account.class) {
            if (this.balance > amt) {
                this.balance -= amt;
                target.balance += amt;
            }
        }
    }

}
