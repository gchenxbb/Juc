package com.gc.multi;

/*
 *
 * */
public class MainClient {
	public static void main(String[] args) {
		//test1();
		//test2();
		//test3();
		//test4();
		Instance.startNoVolatile();
	}
	//1：不同的对象，普通锁，可同时访问，
	private static void test1(){
		final TestSynchronized s1=new TestSynchronized();
		TestSynchronized s2=new TestSynchronized();
		new Thread(new Runnable(){
			public void run() {
				s1.syncA();
			}
			
		}).start();
		s2.syncA();
	}
	// 2:相同对象，普通锁,不同的加锁代码块，也不能同时访问
	private static void test2(){
		final TestSynchronized s1=new TestSynchronized();
		TestSynchronized s2=new TestSynchronized();
		new Thread(new Runnable(){

			public void run() {
				s1.syncA();
			}
			
		}).start();
		s1.syncB();
	}
	//3: 不同对象，静态锁，不能同时访问
	private static void test3(){
		final TestSynchronized s1=new TestSynchronized();
		TestSynchronized s2=new TestSynchronized();
		new Thread(new Runnable(){

			public void run() {
				// TODO Auto-generated method stub
				s1.syncStaticA();
			}
			
		}).start();
		s2.syncStaticA();
	}
	//4:不同对象，静态锁，不同的加锁代码块，也不能同时访问
	private static void test4(){
		final TestSynchronized s1=new TestSynchronized();
		TestSynchronized s2=new TestSynchronized();
		new Thread(new Runnable(){

			public void run() {
				s1.syncStaticA();
			}
			
		}).start();
		TestSynchronized.syncStaticB();
	}

}
