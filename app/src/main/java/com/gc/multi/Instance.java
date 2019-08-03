package com.gc.multi;


import android.util.Log;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Instance {
    private static long mSleepInterval = 1000;
    public int mInitvalue = 500;
    //static volatile Instance instance;//volatile修饰
    static Instance instance;

    public static final String TAG = "Instance_";

    //1：普通锁，不同的对象，可同时访问
    public static void testDifObjNormalLock() {
        new Thread(new Runnable() {
            public void run() {
                new TestSynchronized().syncA();
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                new TestSynchronized().syncA();
            }
        }).start();
    }

    // 2：普通锁，相同对象，不同的加锁代码块，不能同时访问
    public static void testEqualObjDifChunk() {
        final TestSynchronized so1 = new TestSynchronized();
        new Thread(new Runnable() {
            public void run() {
                so1.syncA();
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                so1.syncB();
            }
        }).start();
    }

    //3：静态锁，不同对象，相同方法，不能同时访问
    public static void testStaDifObj() {
        new Thread(new Runnable() {
            public void run() {
                new TestSynchronized().syncStaticA();
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                new TestSynchronized().syncStaticA();
            }
        }).start();
    }

    //4：静态锁，不同对象，不同的加锁代码块，不能同时访问
    public static void testStaDifObjChunk() {
        new Thread(new Runnable() {
            public void run() {
                new TestSynchronized().syncStaticA();
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                TestSynchronized.syncStaticB();
            }
        }).start();
    }

    //wait_notify
    public static void startWaitNotify() {
        //线程1
        final WaitNotifyRunnable taskRunnable = new WaitNotifyRunnable();
        Thread thread = new Thread(taskRunnable);
        thread.start();
        Thread thread2 = new Thread(taskRunnable);
        thread2.start();
        Thread thread3 = new Thread(taskRunnable);
        thread3.start();

        //线程4
        Thread second = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    taskRunnable.secondMethod();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        second.start();
    }

    //lock
    public static void startLock() {
        //线程1
        LockRunnable lockRunnable = new LockRunnable();
        Thread one = new Thread(lockRunnable);
        one.start();
        try {
            //确保one先竞争到锁
            Thread.sleep(500);
        }catch (Exception e){
        }
        //线程2
        Thread two = new Thread(lockRunnable);
        two.start();

        try {
            //让竞争锁失败而休眠的2线程中断。线程2仍会休眠，线程1释放锁后，线程2竞争到锁，执行时sleep会检查到中断状态，异常
            Thread.sleep(1000);
            two.interrupt();
        }catch (Exception e){
        }
    }

    //lock，中断
    public static void startLockInterruptibly() {
        //线程1
        final LockInterruptiblyRunnable lockInterrRunnable = new LockInterruptiblyRunnable();
        Thread one = new Thread(lockInterrRunnable);
        one.start();
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Thread two = new Thread(lockInterrRunnable);
        two.start();

        try {
            Thread.sleep(3000);
            two.interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //volatile关键字
    public static void startNoVolatile() {
        instance = new Instance();
        //线程1
        new Thread(new Runnable() {
            public void run() {
                Log.d(TAG, "线程1开始 " + " 初始值：" + instance.mInitvalue + "\n");
                while (true) {
                    //初始值改变，退出循环。
                    if (instance.mInitvalue != 500) {
                        Log.d(TAG, Thread.currentThread().getName() + " 初始值被更改：" + instance.mInitvalue + "\n");
                        break;
                    }
                    //如果不加下面这句，线程中while循环并不会重新获取对内存的value值
                    //因此，value并没有因为主线程更新而改变。还是500
                    //如果加了下面这句，线程内存就会重新更新了。
                    //因此，需要使用volatile关键字。不管怎样，使用即从主内存拿最新的值
                    //System.out.print(Thread.currentThread().getName()+"\n");
                    //在Android平台上，不加这一句，也没有volatile，有时也可以监控到改变，呵呵。
                }
                Log.d(TAG, "结束循环_break\n");
            }
        }).start();
        //线程2，休眠一段时间后，修改初始值
        new Thread(new Runnable() {
            public void run() {
                Log.d(TAG, "线程2开始\n");
                try {
                    Thread.sleep(mSleepInterval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                instance.mInitvalue = 510;// 修改
                Log.d(TAG, Thread.currentThread().getName() + " 线程2更改初始值为：" + instance.mInitvalue + "\n");
            }
        }).start();
    }

}
