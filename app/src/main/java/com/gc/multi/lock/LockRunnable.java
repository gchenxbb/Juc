package com.gc.multi.lock;

import android.util.Log;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//lock
public class LockRunnable implements Runnable {
    public static final String TAG = "LockRunnable_";
    Lock lock = new ReentrantLock();

//public void run() {
//    String threadName = Thread.currentThread().getName();
//    if (lock.tryLock()) {
//        try {
//            Log.d(TAG, "线程:" + threadName + " 获得锁。");
//            Thread.sleep(5000);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            lock.unlock();
//        }
//    } else {
//        Log.d(TAG, "线程:" + threadName + " 获取锁失败。");
//    }
//}

    public void run() {
        String threadName = Thread.currentThread().getName();
        lock.lock();
        try {
            Log.d(TAG, "线程:" + threadName + " 获得锁。");
            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "线程:" + Thread.currentThread() + "被中断！");
        } finally {
            lock.unlock();
            Log.d(TAG, "线程:" + threadName + " 释放锁。");
        }
    }
}