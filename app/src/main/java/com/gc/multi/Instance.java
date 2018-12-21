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
        final TestSynchronized so1 = new TestSynchronized();
        final TestSynchronized so2 = new TestSynchronized();
        new Thread(new Runnable() {
            public void run() {
                so1.syncA();
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                so2.syncA();
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
        final TestSynchronized so1 = new TestSynchronized();
        final TestSynchronized so2 = new TestSynchronized();
        new Thread(new Runnable() {
            public void run() {
                so1.syncStaticA();
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                so2.syncStaticA();
            }
        }).start();
    }

    //4：静态锁，不同对象，不同的加锁代码块，不能同时访问
    public static void testStaDifObjChunk() {
        final TestSynchronized so1 = new TestSynchronized();
        new Thread(new Runnable() {
            public void run() {
                so1.syncStaticA();
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                TestSynchronized.syncStaticB();
            }
        }).start();
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

    //wait_notify
    public static void startWaitNotify() {
        //线程1
        final TaskRunnable taskRunnable = new TaskRunnable();
        Thread thread = new Thread(taskRunnable);
        thread.start();

        //线程2
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

        //线程2运行secondMethod函数。打印信息后，先wait等待被唤醒。这时线程0还在休眠。
        //线程1休眠，线程2先拿到锁
        //线程2拿到锁，先sleep，再wait，sleep并不会释放对象锁。线程1休眠结束，等待锁。
        //线程2wait时，释放锁。
        //线程1，得到锁，修改内容，通知线程2唤醒
        //线程2，修改内容

    }


    //lock
    public static void startLock() {
        //线程1
        final LockRunnable lockRunnable = new LockRunnable();
        Thread one = new Thread(lockRunnable);
        one.start();

        //线程2
        Thread second = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lockRunnable.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        second.start();
    }


    //lock，中断
    public static void startLockInterruptibly() {
        //线程1
        final LockInterruptiblyRunnable lockInterrRunnable = new LockInterruptiblyRunnable();
        Thread one = new Thread(lockInterrRunnable);
        one.start();
        //线程2
        final Thread second = new Thread(lockInterrRunnable);
        second.start();

        //线程2在等待lock锁时，被中断
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    second.interrupt();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

}
