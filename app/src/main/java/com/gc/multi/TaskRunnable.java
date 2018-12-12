package com.gc.multi;

import android.util.Log;

public class TaskRunnable implements Runnable {
    public static final String TAG = "TaskRunnable_";

    int initValue = 10;

    //先休眠
    public void firstMethod() throws Exception {
        Log.d(TAG, Thread.currentThread() + "：firstMethod" + this);
        Thread.sleep(2000);
        synchronized (this) {
            Log.d(TAG, "线程1，方法1修改前：" + initValue);
            initValue += 100;
            Log.d(TAG, "线程1，马上唤醒线程2，方法1修改后：" + initValue);
            notify();
        }
    }

    public void secondMethod() throws Exception {
        Log.d(TAG, Thread.currentThread() + "：secondMethod" + this);
        synchronized (this) {
            Thread.sleep(10000);//不会释放锁
            this.wait();//释放锁。
            Log.d(TAG, "线程2被唤醒，方法2修改前：" + initValue);
            initValue *= 200;
            Log.d(TAG, "线程2，方法2修改后：" + initValue);
        }
    }

    public void run() {
        try {
            firstMethod();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}