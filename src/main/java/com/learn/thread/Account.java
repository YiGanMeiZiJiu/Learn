package com.learn.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * 锁应该是私有的，不可变的，不可重用的
 */
public class Account {

    //账户id
    private long id;
    //余额
    private int balance;
    //密码
    private String password;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Lock getLock() {
        return lock;
    }

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

    /**
     * 错误的上锁方式
     * 锁住的如果是Integer和String类型的对象是不行的，如果锁住的Integer或者String发生变化，就不能互斥
     * 而且Integer String Boolean是可重用的，意味着别人可能拿着你的锁，你可能永远拿不到锁
     */
    // 取款
//    void withdraw(Integer amt) {
//        synchronized (balance) {
//            if (this.balance > amt) {
//                this.balance -= amt;
//            }
//        }
//    }

    //更改密码
    void updatePassword(String pwd) {
        synchronized (pwd) {
            this.password = pwd;
        }
    }


    /**
     * 尝试使用SDK并发包中的可重入锁
     * tryLock支持非阻塞方式拿锁
     * 下面的方法不会出现死锁，但有可能活锁
     */
    private final Lock lock = new ReentrantLock(false);

    void transferByLock(Account tar, int amt) {
        while (true) {
            if (this.lock.tryLock()) {
                try {
                    if (tar.lock.tryLock()) {
                        try {
                            this.balance -= amt;
                            tar.balance += amt;
                        } finally {
                            tar.lock.unlock();
                        }
                    }
                } finally {
                    this.lock.unlock();
                }
            }
        }
    }

    /**
     * 对上面转账方法的优化，加入转账成功后退出循环和线程随机等待时间，避免活锁
     * @param tar
     * @param amt
     * @throws InterruptedException
     */
    void transferByLockOptimize(Account tar, int amt) throws InterruptedException {
        boolean check = true;
        while (check) {
            if (this.lock.tryLock((long)(1+Math.random()*(30-1+1)),NANOSECONDS)) {
                try {
                    if (tar.lock.tryLock((long)(1+Math.random()*(30-1+1)),NANOSECONDS)) {
                        try {
                            this.balance -= amt;
                            tar.balance += amt;
                            check = false;
                        } finally {
                            tar.lock.unlock();
                        }
                    }
                } finally {
                    this.lock.unlock();
                }
            }
        }
    }

}
