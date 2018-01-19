package com.lw.thread;

import java.sql.Connection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

//http://jcip.net/listings.html
public class FirstTest {

//	CopyOnWriteArrayList<E>
//	BlockingQueue<E>
//	Deque<E>  双端队列
	/**
	 * ThreadLocal维持线程封闭
	 */
	private static ThreadLocal<Connection> connectionHolder = 
			new ThreadLocal<Connection>(){
		public Connection initialValue(){
			try {
				return DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	};
	
	public static Connection getConnection(){
		return connectionHolder.get();
	}
	
//	public static void main(String[] args) {
//		Connection conn = FirstTest.getConnection();
//		System.out.println(conn);
//	}
	
	//=============end================================
	/**
	 * 线程计数
	 * countDown()每调用一次，计数器减一，然后await()阻塞线程直到计数器为0
	 */
	public long timeTasks(int nThreads,final Runnable task)throws InterruptedException{
		final CountDownLatch startGate = new CountDownLatch(1);//线程计数器
		final CountDownLatch endGate = new CountDownLatch(nThreads);
		
		for(int i=0;i<nThreads;i++){
			Thread t = new Thread(){
				public void run(){
					try{
						startGate.await();//当前线程阻塞,等待计数器为0 
						try{
							task.run();
						}finally{
							endGate.countDown();//会将开始设置的nThreads数量减一，nThreads本身值不会改变
						}
					}catch(InterruptedException ignored){}
				}
			};
			t.start();
		}
		long start = System.nanoTime();
		startGate.countDown();//1减一为0，所以startGate.await处的阻塞释放
		endGate.await();//此处阻塞主线程，等待endGate处的计数为0才释放阻塞
		long end = System.nanoTime();
		return end-start;
	}
	//================end===============================
	/**
	 * 使用FutureTask来提前加载稍后需要的数据
	 * 当调用ft.get()时会调用future.get()此时早在调用前就调用了call()方法，如果call()方法没有返回结果
	 * future.get()会一直阻塞线程，直到返回了结果。
	 * 
	 * 测试结果：
	 * start future get!
		in call
	 */
	private final FutureTask<String> future = new FutureTask<String>(
			new Callable<String>(){
				public String call(){
					System.out.println("in call");
					return "a";
				}
			});
	private final Thread thread = new Thread(future);
	public void start(){thread.start();}
	public String get() throws Throwable{
		try{
			System.out.println("start future get!");
			return future.get();
		}catch(ExecutionException e){
			e.printStackTrace();
			Throwable cause = e.getCause();
			throw cause;
		}
	}
//	public static void main(String[] args) {
//		FirstTest ft = new FirstTest();
//		try {
//			ft.start();//不启动线程会导致直接调用get阻塞线程
//			ft.get();
//		} catch (Throwable e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	//===============end=========================
	
	/**
	 * 信号量
	 * Semaphore 信号量变量，
	 * new Semaphore(bound);  此处的bound表示许可数量，bound=5
	 * acquire()方法将获取一个许可，此时许可还有4个，如果没有许可了那么就会阻塞线程，等待有许可
	 * release()方法给释放一个许可，此时许可就又变成了5
	 */
	private final Set<Object> set;
	private final Semaphore sem;
	public FirstTest(int bound){
		this.set = Collections.synchronizedSet(new HashSet<Object>());
		sem = new Semaphore(bound);
	}
	public boolean add(Object o)throws InterruptedException{
		sem.acquire();//获取许可,没有许可就阻塞
//		sem.acquire(); //初始化许可为1，如果此处不注释，那么就没有许可了，阻塞线程
		boolean wasAdded = false;
		try{
			System.out.println("ddd");
			wasAdded = set.add(o);
			return wasAdded;
		}finally{
			if(!wasAdded) sem.release();//释放许可
			System.out.println("finally "+wasAdded);
		}
	}
	public boolean remove(Object o){
		boolean wasRemoved = set.remove(o);
		if(wasRemoved) sem.release();
		return wasRemoved;
	}
	
//	public static void main(String[] args) {
//		FirstTest ft = new FirstTest(1);
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				try {
//					ft.add(2);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//		}).start();
//	}
	//==================end===========================
	
	/**
	 * Executor框架
	 * Executors.newFixedThreadPool(NTHREADS); 创建一个固定NTHREADS个线程的线程池，用于存放线程
	 * 即使我设置了count为41才退出，但依旧只会创建NTHREADS个线程，其他30个会在这NTHREADS个中利用空闲的使用
	 */
	private static final int NTHREADS = 10;//不能为0，否则ExceptionInInitializerError
	private static final Executor exec = Executors.newFixedThreadPool(NTHREADS);
//	public static void main(String args[]) throws IOException{
//		ServerSocket socket = new ServerSocket(80);
//		int count = 0;
//		while(count++<40){
////			final Socket connection = socket.accept();
//			Runnable task = new Runnable(){
//				public void run(){
//					//handleRequest(connection);
//					System.out.println(Thread.currentThread().getName());
//				}
//			};
//			exec.execute(task);
//		}
//	}
	//==================end================================
	
	{
		//创建一个固定大小的线程池，并采用有界队列以及调用者运行饱和策略
		/*
		 * LinkedBlockingQueue  有界队列
		 * setRejectedExecutionHandler 设置运行策略
		 * CallerRunsPolicy ：调用者运行饱和策略
		 * 		如果新提交的任务无法保存到队列中执行时，将任务回退到调用者，从而降低新任务流量
		 * 中止策略（AbortPolicy): 抛出未检查的RejectedExecutionException异常
		 * 抛弃策略（DiscardPolicy):新提交的任务无法保存到队列中执行时，抛弃此新任务
		 * 抛弃最旧策略（DiscardOldestPolicy）： 抛弃下一个将被执行的任务，然后重提此新任务
		 * 		不要和优先级队列(PriorityBlockingQueue)放在一起使用，否则会可能会抛弃优先级最高的任务。
		 * 
		 */
//		ThreadPoolExecutor executor = new ThreadPoolExecutor(N_THREADS,N_THREADS,0L,TimeUnit.MILLISECONDS,
//				new LinkedBlockingQueue<Runnable>(CAPACITY));
//		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
	}
	//======================================end==========================================
	
//	FutureTask<V>
	
	{
		Lock lock = new ReentrantLock();
		lock.lock();
		lock.tryLock();
		lock.unlock();
		ReadWriteLock rwLock = new ReadWriteLock() {
			
			@Override
			public Lock writeLock() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Lock readLock() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		rwLock = new ReentrantReadWriteLock();
		rwLock.readLock();
		rwLock.writeLock();
	}
//	AbstractQueuedSynchronizer //线程闭锁等基础抽象类
	
	
}
