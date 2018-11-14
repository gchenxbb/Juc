package com.gc.multi;

public class TestSynchronized {
	
	 public synchronized void syncA(){
		System.out.println("start syncA !");
		System.out.println("Thread Name is:"+Thread.currentThread().getName());
		try{
			Thread.sleep(5000);
		}catch(InterruptedException e){
			
		}
		System.out.println("end syncA !");
	 } 
	 
	 public synchronized void syncB(){
		System.out.println("start syncB !");
		System.out.println("Thread Name is:"+Thread.currentThread().getName());
		try{
			Thread.sleep(5000);
		}catch(InterruptedException e){
			
		}
		System.out.println("end syncB !");
	 } 
	 
	 //静态锁
	 public static synchronized void syncStaticA(){
		System.out.println("start syncStaticA !");
		System.out.println("Thread Name is:"+Thread.currentThread().getName());
		try{
			Thread.sleep(5000);
		}catch(InterruptedException e){
			
		}
		
		System.out.println("end syncStaticA !");
	 } 
	 
	//静态锁
	 public static synchronized void syncStaticB(){
		System.out.println("start syncStaticB !");
		System.out.println("Thread Name is:"+Thread.currentThread().getName());
		try{
			Thread.sleep(5000);
		}catch(InterruptedException e){
			
		}
		
		System.out.println("end syncStaticB !");
		 
	 } 
}
