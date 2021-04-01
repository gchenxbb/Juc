package com.gc.multi.synchronize;

import android.util.Log;

//类锁与对象锁
public class TestSynchronized {
    public static final String TAG = "TestSynchronized";

    //1对象锁
    public void syncA() {
        String threadName = Thread.currentThread().getName();
        synchronized (this) {
            Log.d(TAG, threadName + "线程， start syncA()方法。");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
            Log.d(TAG, threadName + "线程， end syncA()方法。");
        }
    }

    //1对象锁
    public synchronized void syncB() {
        String threadName = Thread.currentThread().getName();
        Log.d(TAG, threadName + "线程， start syncB()方法。");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
        Log.d(TAG, threadName + "线程， end syncB()方法。");
    }

    //3静态锁
    public static synchronized void syncStaticA() {
        String threadName = Thread.currentThread().getName();
        Log.d(TAG, threadName + "线程， start syncStaticA()方法。");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
        Log.d(TAG, threadName + "线程， end syncStaticA()方法。");
    }

    //4静态锁
    public static synchronized void syncStaticB() {
        String threadName = Thread.currentThread().getName();
        Log.d(TAG, threadName + "线程， start syncStaticB()方法。");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
        Log.d(TAG, threadName + "线程， end syncStaticB()方法。");
    }
}