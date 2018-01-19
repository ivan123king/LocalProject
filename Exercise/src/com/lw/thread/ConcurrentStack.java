package com.lw.thread;

import java.util.concurrent.atomic.*;
import com.lw.thread.*;

/**
 * 非阻塞栈,模拟类型
 * @author lenovo
 *
 * @param <E>
 */
public class ConcurrentStack<E> {
	AtomicReference<Node<E>> top = new AtomicReference<Node<E>>();
	public void push(E item){
		Node<E> newHead = new Node<E>(item);
		Node<E> oldHead = null;
		do{
			oldHead = top.get();
			newHead.next = oldHead;
		}while(!top.compareAndSet(oldHead,newHead));
	}
	public E pop(){
		Node<E> oldHead ;
		Node<E> newHead;
		do{
			oldHead = top.get();
			if(oldHead==null) return null;
			newHead = oldHead.next;
		}while(!top.compareAndSet(oldHead,newHead));
		return oldHead.item;
	}
	private static class Node<E>{
		public final E item;
		public Node<E> next;
		public Node(E item){
			this.item = item;
		}
	}
}
