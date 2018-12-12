package com.gc.multi;

import android.util.Log;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//lock
public class LockRunnable implements Runnable {
    public static final String TAG = "LockRunnable_";

    Lock lock = new ReentrantLock();


    public void run() {
        if (lock.tryLock()) {
            try {
                Log.d(TAG, "线程:" + Thread.currentThread());
                Log.d(TAG, "准备sleep:" + Thread.currentThread());
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        } else {
            Log.d(TAG, "线程:" + Thread.currentThread() + " 获取锁失败！");
        }

    }

}