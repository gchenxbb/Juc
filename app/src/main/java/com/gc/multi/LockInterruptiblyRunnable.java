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
            invokeM();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d(TAG, "线程:" + Thread.currentThread() + "被中断！");
        }
    }


    public void invokeM() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            Log.d(TAG, "线程:" + Thread.currentThread() + " 获取lock锁！");
            Log.d(TAG, "准备sleep:" + Thread.currentThread());
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            Log.d(TAG, "线程:" + Thread.currentThread() + " 释放lock锁！");
        }
    }

}