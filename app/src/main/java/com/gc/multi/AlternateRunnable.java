package com.gc.multi;

import android.util.Log;

/**
 * 两个线程交替打印0-100的数字
 */
public class AlternateRunnable implements Runnable {

    public static String TAG = "Instance_";
    public Object locked = new Object();
    int i = 0;

    @Override
    public void run() {
        Log.d(TAG, Thread.currentThread().getName() + " thread start");
        for (; i < 100; ) {
            printNum();
        }
        Log.d(TAG, Thread.currentThread().getName() + " thread end");

        //wait和nofity在synchronized一起用，最后会通知另一个wait的唤醒。
        synchronized (locked) {
            locked.notify();
        }
    }


    private void printNum() {
        synchronized (locked) {
            i++;
            Log.d(TAG, Thread.currentThread().getName() + " is running " + i);
            locked.notify();

            try {
                locked.wait();
            } catch (Exception e) {
                Log.d(TAG, Thread.currentThread().getName() + e.getMessage());

            }

        }
    }
}
