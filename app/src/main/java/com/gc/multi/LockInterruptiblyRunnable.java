package com.gc.multi;

import android.util.Log;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//lock，中断
public class LockInterruptiblyRunnable implements Runnable {
    public static final String TAG = "LockInterruptiblyRunna_";

    Lock lock = new ReentrantLock();

    public void run() {
        try {
            lock.lockInterruptibly();
            Log.d(TAG, "线程:" + Thread.currentThread() + " 获取lock锁！");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            //被中断时，进入该代码段，最后释放锁。
            Log.d(TAG, "线程:" + Thread.currentThread() + "被中断！");
        } finally {
            Log.d(TAG, "线程:" + Thread.currentThread() + " 释放lock锁！");
            lock.unlock();
        }
    }

}