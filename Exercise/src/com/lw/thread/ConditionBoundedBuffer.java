package com.lw.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionBoundedBuffer<T> {
	protected final Lock lock = new ReentrantLock();
	private final Condition notFull = lock.newCondition();
	private final Condition notEmpty = lock.newCondition();
	int BUFFER_SIZE = 10;
	private final T[] items = (T[])new Object[BUFFER_SIZE];
	private int tail,head,count;
	
	public void put(T x) throws InterruptedException{
		lock.lock();
		try{
			while(count==items.length)
				notFull.await();
			items[tail] = x;
			if(++tail==items.length) 
				tail = 0;
			notEmpty.signal();
		}finally{
			lock.unlock();
		}
	}
	
	public T take() throws InterruptedException{
		lock.lock();
		try{
			while(count==0)
				notEmpty.await();//线程阻塞
			T x = items[head];
			items[head] = null;
			if(++head==items.length)
				head = 0;
			--count;
			notFull.signal();//唤醒notFull阻塞线程
			return x;
		}finally{
			lock.unlock();
		}
	}
}
