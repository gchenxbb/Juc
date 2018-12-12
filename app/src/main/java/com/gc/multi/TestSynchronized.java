package com.gc.multi;

import android.util.Log;

//类锁与对象锁
public class TestSynchronized {

    public static final String TAG = "Synchronized_";
    LogUtil logUtil = LogUtil.getInstance();
    private static long mSleepInterval = 10000;

    //1对象锁
    public synchronized void syncA() {
        Log.d(TAG, this.toString() + " start syncA ! Thread Name is:" + Thread.currentThread().getName());
        try {
            Thread.sleep(mSleepInterval);
        } catch (InterruptedException e) {

        }
        Log.d(TAG, this.toString() + " end syncA !");
    }

    //2对象锁
    public synchronized void syncB() {
        Log.d(TAG, this.toString() + " start syncB ! Thread Name is:" + Thread.currentThread().getName());
        try {
            Thread.sleep(mSleepInterval);
        } catch (InterruptedException e) {

        }
        Log.d(TAG, this.toString() + " end syncB !");
    }

    //3静态锁
    public static synchronized void syncStaticA() {
        Log.d(TAG, "start syncStaticA !");
        Log.d(TAG, "Thread Name is:" + Thread.currentThread().getName());
        try {
            Thread.sleep(mSleepInterval);
        } catch (InterruptedException e) {

        }

        Log.d(TAG, "end syncStaticA !");
    }

    //4静态锁
    public static synchronized void syncStaticB() {
        Log.d(TAG, "start syncStaticB !");
        Log.d(TAG, "Thread Name is:" + Thread.currentThread().getName());
        try {
            Thread.sleep(mSleepInterval);
        } catch (InterruptedException e) {

        }
        Log.d(TAG, "end syncStaticB !");
    }
}
