package com.lw.thread;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import junit.framework.TestCase;

public class PutTakeTest extends TestCase{
	
	private static final ExecutorService pool = Executors.newCachedThreadPool();
	private final AtomicInteger putSum = new AtomicInteger(0);
	private final AtomicInteger takeSum = new AtomicInteger(0);
	private final CyclicBarrier barrier;
	private final BoundedBuffer<Integer> bb;
	private final int nTrials,nPairs;
	
	PutTakeTest(int capacity,int npairs,int ntrials){
		this.bb = new BoundedBuffer<Integer>(capacity);
		this.nTrials = ntrials;
		this.nPairs = npairs;
		this.barrier = new CyclicBarrier(npairs*2+1);
	}
	void test(){
		try{
			for(int i=0;i<nPairs;i++){
				pool.execute(new Producer());
				pool.execute(new Consumer());
			}
			barrier.await();//等待所有线程就绪
			barrier.await();//等待所有线程执行完成
			assertEquals(putSum.get(),takeSum.get());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
//	public static void main(String[] args){
//		new PutTakeTest(2,2,10).test();
//		pool.shutdown();
//	}
	/**
	 * 随机数生成
	 * @param y
	 * @return
	 */
	static int xorShift(int y){
		y ^= (y<<6);
		y ^= (y>>>21);
		y ^= (y<<7);
		return y;
	}
	
	class Producer implements Runnable{
		public void run(){
			try{
				int seed = (this.hashCode()^(int)System.nanoTime());
				int sum = 0;
				barrier.await();
				for(int i=nTrials;i>0;--i){
					bb.put(seed);
					sum += seed;
					seed = xorShift(seed);
					System.out.println(Thread.currentThread().getName()+Producer.class.getName());
				}
				putSum.getAndAdd(sum);
				barrier.await();
			}catch(Exception e){
				throw new RuntimeException(e);
			}
		}
	}
	
	class Consumer implements Runnable{
		@Override
		public void run(){
			try{
				barrier.await();
				int sum = 0;
				for(int i=nTrials;i>0;--i){
					sum += bb.take();
					System.out.println(Thread.currentThread().getName());
				}
				takeSum.getAndAdd(sum);
				barrier.await();
			}catch(Exception e){
				throw new RuntimeException(e);
			}
		}
	}
	
	class BoundedBuffer<E>{
		private final Semaphore availableItems,availableSpaces;
		private final E[] items;
		private int putPosition = 0,takePosition = 0;
		
		public BoundedBuffer(int capacity){
			availableItems = new Semaphore(0);
			availableSpaces = new Semaphore(capacity);
			items = (E[])new Object[capacity];
		}
		public boolean isEmpty(){
			return availableItems.availablePermits()==0;
		}
		public boolean isFull(){
			return availableSpaces.availablePermits()==0;
		}
		public void put(E x) throws InterruptedException{
			availableSpaces.acquire();
			doInsert(x);
			availableItems.release();
		}
		public E take()throws InterruptedException{
			availableItems.acquire();
			E item = doExtract();
			availableSpaces.release();
			return item;
		}
		private synchronized void doInsert(E x){
			int i = putPosition;
			items[i] = x;
			putPosition = (++i==items.length)? 0:i;
		}
		private synchronized E doExtract(){
			int i = takePosition;
			E x = items[i];
			items[i] = null;
			takePosition = (++i==items.length)?0:i;
			return x;
		}
		
	}
	
	
	
	
}
