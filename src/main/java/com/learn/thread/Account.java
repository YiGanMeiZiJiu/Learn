package com.learn.thread;

public class Account {

    //账户id
    private long id;
    //余额
    private int balance;

    // 基础封装转账方法（未加锁）
    void transferBasic(Account target, int amt) {
        if (this.balance > amt) {
            this.balance -= amt;
            target.balance += amt;
        }
    }

    //转账 （对象级别的锁，会有并发问题）
    synchronized void transfer(Account target, int amt) {
        transferBasic(target, amt);
    }

    //安全的转账方法 （Account.class作为唯一的共享锁，不存在并发问题，只是转账操作就成了串行的，影响效率）
    void safeTransfer(Account target, int amt) {
        synchronized (Account.class) {
            transferBasic(target, amt);
        }
    }

    // 优化后安全的转账方法，锁住两个账户来转账（但是极有可能发生死锁）
    void safeTransferOptimizeDeadLock(Account target, int amt) {
        // 先锁住转账账户
        synchronized (this) {
            // 再锁住收钱账户
            synchronized (target) {
                transferBasic(target, amt);
            }
        }
    }

    // 对上面可能导致死锁的转账方法进行优化，每次死循环获取一双锁，否则一个都不拿
    void safeTransferOptimize(Account target, int amt) {
        Allocator allocator = Allocator.getInstance();
        while (!allocator.apply(this, target)) {}
        try {
            synchronized (this) {
                synchronized (target) {
                    transferBasic(target, amt);
                }
            }
        } finally {
            allocator.free(this, target);
        }
    }

    // 另一种解除死锁的办法，顺序拿锁
    void safeTransferInTurn(Account target, int amt) {
        Account first = target;
        Account last = this;
        if (target.id > this.id) {
            first = this;
            last = target;
        }
        // 先锁住序号较小的锁
        synchronized (first) {
            // 在锁住序号较大的锁
            synchronized (last) {
                transferBasic(target, amt);
            }
        }
    }

}
