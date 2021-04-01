package com.gc.multi.interrupt;

import android.os.SystemClock;
import android.util.Log;

/**
 * 中断
 */
public class InterruptiblyRunnable implements Runnable {
    public static final String TAG = "InterruptiblyRunnable_";

    public void run() {
        try {
            while (true) {

                /**
                 * 运行4s左右
                 */
                long time = SystemClock.currentThreadTimeMillis();
                int i = 0;
                while (i < Integer.MAX_VALUE) {

                    i++;
                    if (i % 100000000 == 0) {
                        Log.d(TAG, "休眠结束开始打印:" + i + "，当前线程状态:" + Thread.currentThread().isInterrupted());
                    }
                }
                Log.d(TAG, "耗时:" + (SystemClock.currentThreadTimeMillis() - time));

                /**
                 * 休眠10s
                 */
                Thread.sleep(10000);
            }
        } catch (InterruptedException e) {
            Log.d(TAG, "循环结束:" + e.getMessage());
            e.printStackTrace();
        }
    }
}