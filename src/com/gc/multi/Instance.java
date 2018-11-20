package com.gc.multi;

public class Instance {
	public int value = 500;
	static volatile Instance instance;

	public static void startNoVolatile() {
		new Thread(new Runnable() {
			public void run() {
				instance = new Instance();
				while (true) {
					if (instance.value != 500) {
						System.out.print(instance.value);
						break;
					}
					//如果不加下面这句，线程中while循环并不会重新获取对内存的value值，因此
					//value并没有因为主线程更新而改变。还是500
					//如果加了下面这句，线程内存就会重新更新了。
					//因此，需要使用volatile关键字。不管怎样，使用即从主内存拿最新的值
					//System.out.print(Thread.currentThread().getName()+"\n");
				}
			}
		}).start();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		instance.value = 300;// 修改
		System.out.print(Thread.currentThread().getName() + instance.value+"\n");
	}
	
	public static void startWaitNotify() {
		TaskRunnable taskRunnable=new TaskRunnable();
		Thread thread=new Thread(taskRunnable);
		System.out.println("主线程:"+Thread.currentThread());
		//启动线程0，运行run方法，firstMethod先休眠10秒	
		thread.start();
		//主线程运行secondMethod函数。打印信息后，先wait等待被唤醒。这时线程0还在休眠。
		//当线程0休眠够10秒，开始运行。这时候发现主线程的secondMethod函数还在wait状态。
		//线程0运行完同步代码块后，notify主线程。这时候主线程被唤醒。继续运行。根据打印的number的值可以看出
		//主线程再次运行的时候。number已经变成线程0运行完后的值。
		
		//说明在wait的时候。testThread对象锁被释放了，然后访问了其他的同步代码块。
		//在secondMethod中加入Thread sleep 60秒，这时候在线程0的时间到10秒后，也不会执行firstMethod
		//的同步代码块，因为sleep并不会释放对象锁。
		//对象锁是指的TaskRunnable对象，同一个对象的不同同步块也无法同时进入。
		try {
			taskRunnable.secondMethod();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
