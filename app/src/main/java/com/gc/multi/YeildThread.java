package com.gc.multi;

import android.util.Log;

public class YeildThread extends Thread {
    public static String TAG = "Instance_";
//    让当前运行线程回到可运行状态，让具有相同优先级等想成获得运行机会，增加适当轮转机会。
//    仅仅是让线程出让cpu，下次cpu仍然可能选中它执行。
    @Override
    public void run() {
        super.run();
        Log.d(TAG, Thread.currentThread().getName() + " Thread start");
        for (int i = 0; i < 100; i++) {
            if(i == 50 && getName().equals("threadYeild2")){//让2线程让出。
                yield();
            }
            Log.d(TAG, Thread.currentThread().getName() + " loop in " + i);
        }
        Log.d(TAG, Thread.currentThread().getName() + " Thread end");
    }

}
