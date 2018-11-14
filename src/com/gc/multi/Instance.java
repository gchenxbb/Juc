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

}
