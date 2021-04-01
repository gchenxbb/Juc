package com.gc.multi.join;

import android.util.Log;

public class ThreadB extends Thread {
    public static String TAG = "Instance_";

    @Override
    public void run() {
        super.run();
        try {
            Thread.sleep(3000);
        } catch (Exception e) {

        }
        Log.d(TAG, Thread.currentThread().getName() + " Thread start");
        for (int i = 0; i < 10; i++) {
            Log.d(TAG, Thread.currentThread().getName() + " loop in " + i);
        }
        Log.d(TAG, Thread.currentThread().getName() + " Thread end");
    }

}
