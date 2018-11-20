package com.gc.multi;

public class TaskRunnable implements Runnable{
	
	int number=10;
	
	public void firstMethod() throws Exception{
		Thread.sleep(10000);  
		System.out.println(Thread.currentThread() +"：firstMethod" +  this);
		synchronized (this) {
			number += 100;
			System.out.println("方法1修改后："+number);
			notify();
		}
	}

	public void secondMethod() throws Exception {
		System.out.println(Thread.currentThread() +"：secondMethod" + this);
		synchronized(this){
			Thread.sleep(60000);
			this.wait();
			System.out.println("方法2修改前："+number);
			number*=200;
			System.out.println("方法2修改后："+number);
		}
	}
	
	public void run() {
		try{
			firstMethod();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}