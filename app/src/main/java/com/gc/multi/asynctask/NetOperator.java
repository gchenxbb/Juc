package com.gc.multi.asynctask;

/**
 * 计时 休眠1秒，耗时
 */
public class NetOperator {
    private int valTime = 1000;

    public NetOperator(int valTime) {
        this.valTime = valTime;
    }

    public NetOperator() {
    }

    public void operator() {
        try {
            Thread.sleep(valTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}  