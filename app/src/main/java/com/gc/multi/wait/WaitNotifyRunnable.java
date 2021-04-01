package com.gc.multi.wait;

import android.util.Log;

/**
 * wait和notify休眠和唤醒
 */
public class WaitNotifyRunnable implements Runnable {
    public static final String TAG = "WaitNotifyRunnable";

    public void secondMethod() throws Exception {
        String threadName = Thread.currentThread().getName();
        Thread.sleep(2000);//
        synchronized (this) {
            Log.d(TAG, threadName + "线程，竞争获得锁，进入休眠" );
            Thread.sleep(10000);//不会释放锁
            Log.d(TAG, threadName + "线程，sleep结束，通知唤醒wait线程" );
            notifyAll();
            Log.d(TAG, threadName + "线程，即将再次sleep，不会释放锁，被唤醒的wait，进入锁等待池" );
            Thread.sleep(5000);//不会释放锁
        }
    }

    public void run() {
        try {
            String threadName = Thread.currentThread().getName();
            synchronized (this) {
                Log.d(TAG, threadName + "线程，竞争获得锁，进入休眠" );
                this.wait();//释放锁。
                Log.d(TAG, threadName+"线程，被唤醒" );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}