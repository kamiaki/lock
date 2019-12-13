package com.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("hello Thread");
        A a = new A();
        B b = new B();
        new Thread(a, "线程1").start();
        new Thread(b, "线程2-").start();
    }
}

class A implements Runnable {
    @Override
    public void run() {
        runMan.zao();
    }
}

class B implements Runnable {
    @Override
    public void run() {
        runMan.chi();
    }
}

class runMan {
    private static Lock lock = new ReentrantLock();
    private static Condition condition1 = lock.newCondition();
    private static Condition condition2 = lock.newCondition();

    public static void zao() {
        lock.lock();
        try {
            while (true) {
                System.out.println(Thread.currentThread().getName() + "正运行------------");
                condition2.signal();

//                condition2.signalAll();//只能唤醒自己的condition
                try {
                    condition1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public static void chi() {
        lock.lock();
        try {
            while (true) {
                System.out.println(Thread.currentThread().getName() + "正运行");
                condition1.signal();
                try {
                    condition2.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            lock.unlock();
        }
    }
}
