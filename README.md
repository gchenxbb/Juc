#### 并发
##### synchronized和lock
- synchronized是关键字，内置语言，lock是接口
- synchronized在发生异常和执行完代码时，自动释放锁，lock需要在finally手动释放锁。
- lock可以让等待锁的线程中断，而我们没办法设置synchronized获取锁的等待时间，会一直等下去。
- lock可以知道有没有获取锁。tryLock方法。
- synchronized互斥锁，其他线程无法获取锁对象访问权限，对于读比较多的线程很不利。



##### join和yield
