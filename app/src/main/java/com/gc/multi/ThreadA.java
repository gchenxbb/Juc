package com.gc.multi;

import android.util.Log;

public class ThreadA extends Thread {
    public static String TAG = "Instance_";
    Thread threadB;

    public ThreadA(Thread threadB) {
        this.threadB = threadB;
    }

    @Override
    public void run() {
        super.run();
        try {
            threadB.join();//等b执行完，再执行a线程
        } catch (Exception e) {

        }
        try {
            Thread.sleep(2000);
        } catch (Exception e) {

        }
        Log.d(TAG, Thread.currentThread().getName() + " Thread start");
        for (int i = 0; i < 10; i++) {
            Log.d(TAG, Thread.currentThread().getName() + " loop in " + i);
        }
        Log.d(TAG, Thread.currentThread().getName() + " Thread end");
    }
}
